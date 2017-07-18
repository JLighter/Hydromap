package exam.hydromap.julienheroguelle.hydromap.Networking.Repository;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.Norm;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.Token;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.AWherePresenter;
import exam.hydromap.julienheroguelle.hydromap.Objects.Callback;
import exam.hydromap.julienheroguelle.hydromap.Utils.Helper;
import exam.hydromap.julienheroguelle.hydromap.Utils.Networking;

/**
 * Created by julienheroguelle on 18/07/2017.
 */

interface NestedFunction {
    void function();
}

public class AWhereRepository {

    AWherePresenter listener;

    public AWhereRepository(AWherePresenter listener) {
        this.listener = listener;
    }

    private void authForToken(final Callback callback) {
        String url = "https://api.awhere.com/oauth/token";

        final AWhereRequestForAuth request = new AWhereRequestForAuth(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Token token = gson.fromJson(response, Token.class);

                Log.d("AWHERE TOKEN: ", token.accessToken);
                long expiresAt = new Date().getTime();
                expiresAt += token.expiresIn;
                token.expiresAt = expiresAt;
                Helper.shared().aWhereToken = token;

                callback.setResult(true);

                callback.call();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AWHERE TOKEN ERROR: ", error.toString());

                callback.setResult(false);

                callback.call();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5000, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Networking.getInstance().addToRequestQueue(request);
    }

    public void getNorms(final Coords coords, final String day, final String month, final Integer startYear, final Integer endYear) {

        // Defining call to execute
        final NestedFunction doCall = new NestedFunction() {
            @Override
            public void function() {
                String url = "https://api.awhere.com/v2/weather/locations/";
                url += coords.lat.toString() + "," + coords.lon.toString();
                url += "/norms/" + month + "-" + day;
                url += "/years/" + startYear.toString() + "," + endYear.toString();

                final AWhereRequest request = new AWhereRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        Norm norm = gson.fromJson(response, Norm.class);

                        listener.didGotNorm(norm, null);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        listener.didGotNorm(null, error);
                    }
                });

                request.setRetryPolicy(new DefaultRetryPolicy(5000, 5000, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                Networking.getInstance().addToRequestQueue(request);
            }
        };

        // Verify if the token is almost expired, if true we retrieve a new one and do call, else, only do call
        if (isTokenAlmostExpired()) {
            authForToken(new Callback() {
                @Override
                public Void call() {

                    if ((Boolean) result == true) {
                        doCall.function();
                    }

                    return null;
                }
            });
        } else {
            doCall.function();
        }
    }

    static boolean isTokenAlmostExpired() {
        long now = new Date().getTime();
        if (Helper.shared().aWhereToken == null) { return true; }

        long expiresAt = Helper.shared().aWhereToken.expiresAt - 10;

        return now > expiresAt;
    }

}

class AWhereRequestForAuth extends StringRequest {

    public AWhereRequestForAuth(int method, String url,
                               Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization","Basic VUdzZTllN0tmVno4TmFPaTVVU0dhQUVmeHRad0dqazg6N1JZeE1rRWk1eGdBQWZjTQ==");
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");

        return body;
    }
}

class AWhereRequest extends StringRequest {

    public AWhereRequest(int method, String url,
                                Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        if (Helper.shared().aWhereToken != null) {
            headers.put("Authorization", "Bearer " + Helper.shared().aWhereToken.accessToken);
        }
        return headers;
    }
}