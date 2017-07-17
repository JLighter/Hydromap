package exam.hydromap.julienheroguelle.hydromap.Networking.Repository;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Coord;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.ForecastList;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.ForecastPresenter;
import exam.hydromap.julienheroguelle.hydromap.Utils.Networking;

/**
 * Created by julienheroguelle on 16/07/2017.
 */

public class ForecastRepository {

    ForecastPresenter listener;

    public ForecastRepository(ForecastPresenter listener) {
        this.listener = listener;
    }

    public void getFrenchForecasts() {
            String url = "http://api.openweathermap.org/data/2.5/box/city?";
            url += "bbox=-5,42,8,51,10";
            url += "&cluster=false";
            url += Networking.getAPIKeyArgument();

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    ForecastList forecastList = gson.fromJson(response, ForecastList.class);

                    if (forecastList.cod.equals(200)) {
                        listener.didGotForecasts(forecastList.list, null);
                    } else {
                        OWMError err = gson.fromJson(response, OWMError.class);
                        listener.didGotForecasts(null, err);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    OWMError err = new OWMError("0", "No server");
                    listener.didGotForecasts(null, err);
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(5000, 5000, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            Networking.getInstance().addToRequestQueue(request);
    }

    public void getForecastByCoords(Coord coords) {
        String url = "http://api.openweathermap.org/data/2.5/weather?";
        url += "lat=" + coords.lat;
        url += "&lon=" + coords.lon;
        url += Networking.getAPIKeyArgument();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Forecast forecast = gson.fromJson(response, Forecast.class);

                if (forecast.cod.equals(200)) {
                    listener.didGotForecast(forecast, null);
                } else {
                    OWMError err = gson.fromJson(response, OWMError.class);
                    listener.didGotForecast(null, err);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                OWMError err = new OWMError("0", "No server");
                listener.didGotForecast(null, err);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5000, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Networking.getInstance().addToRequestQueue(request);
    }
}
