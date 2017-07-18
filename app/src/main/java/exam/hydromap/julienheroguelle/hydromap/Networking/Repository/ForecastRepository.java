package exam.hydromap.julienheroguelle.hydromap.Networking.Repository;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.ForecastList;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMRect;
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

    public void getForecastsByRect(OWMRect rect, Integer distance) {
            String url = "http://api.openweathermap.org/data/2.5/box/city?";
            url += "bbox="
                    + rect.topLon + ","
                    + rect.leftLat + ","
                    + rect.bottomLon + ","
                    + rect.rightLat + ","
                    + distance;
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

    public void getForecastsByCycle(Coords coords, Integer count) {
        String url = "http://api.openweathermap.org/data/2.5/find?";
        url += "lat=" + coords.lat;
        url += "&lon=" + coords.lon;
        url += "&cnt=" + count;
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

    public void getForecastByCoords(Coords coords) {
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

    public void getForecastsByZip(String code, String country) {
        String url = "http://api.openweathermap.org/data/2.5/weather?";
        url += "zip=" + code;
        url += "," + country;
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

    public void getForecastsByName(String query, String country) {
        String url = "http://api.openweathermap.org/data/2.5/weather?";
        url += "q=" + query;
        url += "," + country;
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

    // WARNING : Each Id is considered as one API Call, if you put 3 id in the array, the call will count for 3 requests
    public void getForecastsById(List<Integer> ids) {
        String url = "http://api.openweathermap.org/data/2.5/group?";
        url += "id=";

        for (Integer i = 0; i<ids.size(); i++) {
            Integer id = ids.get(i);
            url += id + (i != ids.size() - 1 ? "," : "");
        }

        url += Networking.getAPIKeyArgument();

        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ForecastList forecasts = gson.fromJson(response, ForecastList.class);

                listener.didGotForecasts(forecasts.list, null);

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


}
