package exam.hydromap.julienheroguelle.hydromap.Networking.Presenter;

import android.accounts.NetworkErrorException;
import android.net.Network;

import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMRect;
import exam.hydromap.julienheroguelle.hydromap.Networking.Repository.ForecastRepository;
import exam.hydromap.julienheroguelle.hydromap.Utils.Networking;

/**
 * Created by julienheroguelle on 16/07/2017.
 */

public class ForecastPresenter {

    ForecastProtocol listener;
    ForecastRepository repository;

    public ForecastPresenter(ForecastProtocol listener) {
        this.listener = listener;
        repository = new ForecastRepository(this);
    }

    public void getForecastsByZip(String code, String country) {
        if (Networking.isInternetAvailable()) {
            repository.getForecastsByZip(code, country);
        } else {
            listener.didGotForecasts(null, new OWMError("0", "No network"));
        }
    }

    public void getForecastsByName(String query, String country) {
        if (Networking.isInternetAvailable()) {
            repository.getForecastsByName(query, country);
        } else {
            listener.didGotForecasts(null, new OWMError("0", "No network"));
        }
    }

    public void getForecastsByRect(OWMRect rect, Integer distance) {
        if (Networking.isInternetAvailable()) {
            repository.getForecastsByRect(rect, distance);
        } else {
            listener.didGotForecasts(null, new OWMError("0", "No network"));
        }
    }

    public void getForecastsById(List<Integer> ids) {
        if (Networking.isInternetAvailable()) {
            repository.getForecastsById(ids);
        } else {
            listener.didGotForecasts(null, new OWMError("0", "No network"));
        }
    }

    public void getForecastByCoords(Coords coords) {
        if (Networking.isInternetAvailable()) {
            repository.getForecastByCoords(coords);
        } else {
            listener.didGotForecasts(null, new OWMError("0", "No network"));
        }
    }

    public void getForecastsByCycle(Coords coords, Integer count) {
        if (Networking.isInternetAvailable()) {
            repository.getForecastsByCycle(coords, count);
        } else {
            listener.didGotForecasts(null, new OWMError("0", "No network"));
        }
    }

    public void didGotForecasts(List<Forecast> list, OWMError error) {
        listener.didGotForecasts(list, error);
    }

    public void didGotForecast(Forecast forecast, OWMError error){
        listener.didGotForecast(forecast, error);
    }
}
