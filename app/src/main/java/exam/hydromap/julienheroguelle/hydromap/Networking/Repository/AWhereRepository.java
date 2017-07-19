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
import exam.hydromap.julienheroguelle.hydromap.Utils.Objects.Callback;
import exam.hydromap.julienheroguelle.hydromap.Utils.Helper;
import exam.hydromap.julienheroguelle.hydromap.Utils.Networking;

/**
 * Created by julienheroguelle on 18/07/2017.
 */

/**
 * Make sure that you retrieve token if needed at the end of each function with a callback
 * else the call can fail with a 401 error, but not handle
 * TODO: handle 401 error in order to retrieve a new token for each methods in class
 */

public class AWhereRepository {

    AWherePresenter listener;

    final String ENDPOINT = "https://api.awhere.com/";

    public AWhereRepository(AWherePresenter listener) {
        this.listener = listener;
    }

    private void authForToken(final Callback callback) {
        String url = ENDPOINT + "oauth/token";

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

    /** StartYear and EndYear must be seperated by 3 years minimum */
    public void getNorms(final Coords coords, final String day, final String month, final Integer startYear, final Integer endYear) {

        // Defining call to execute
        final NestedFunction callback = new NestedFunction() {
            @Override
            public void function() {
                String url = ENDPOINT + "v2/weather/locations/";
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

        this.retrieveTokenIfNeeded(callback);

    }

    /** StartYear and EndYear must be seperated by 3 years minimum
     *  Days are formatted like MM-DD
     *  Years are formatted like YYYY
     */
    public void getNormsPrecisely(final Coords coords, final String startDay, final String endDay, final Integer startYear, final Integer endYear) {

        // Defining call to execute
        final NestedFunction callback = new NestedFunction() {
            @Override
            public void function() {
                String url = ENDPOINT + "v2/weather/locations/";
                url += coords.lat.toString() + "," + coords.lon.toString();
                url += "/norms/" + startDay + "," + endDay;
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

        this.retrieveTokenIfNeeded(callback);

    }

    private void retrieveTokenIfNeeded(final NestedFunction callback) {
        // Verify if the token is almost expired, if true we retrieve a new one and do call, else, only do call
        if (isTokenAlmostExpired()) {
            authForToken(new Callback() {
                @Override
                public Void call() {

                    if ((Boolean) result == true) {
                        callback.function();
                    }

                    return null;
                }
            });
        } else {
            callback.function();
        }
    }

    static boolean isTokenAlmostExpired() {
        long now = new Date().getTime();
        if (Helper.shared().aWhereToken == null) { return true; }

        long expiresAt = Helper.shared().aWhereToken.expiresAt - 10;

        return now > expiresAt;
    }

}

/** This request is only use when we try to perform a OAuth login to aWhere API
 it contains an Authorization with Basic attributs, follows by secret and client id in MD5 */
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

/** This request helps to perform a Http Request of any method with aWhere specific token */
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

interface NestedFunction {
    void function();
}
