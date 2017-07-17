package exam.hydromap.julienheroguelle.hydromap.Networking.Presenter;

import android.os.Bundle;

import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Coord;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Repository.ForecastRepository;

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

    public void getFrenchForecasts() {
        repository.getFrenchForecasts();
    }

    public void getForecastbyCoords(Coord coords) {
        repository.getForecastByCoords(coords);
    }

    public void didGotForecasts(List<Forecast> list, OWMError error) {
        listener.didGotForecasts(list, error);
    }

    public void didGotForecast(Forecast forecast, OWMError error){
        listener.didGotForecast(forecast, error);
    }
}
