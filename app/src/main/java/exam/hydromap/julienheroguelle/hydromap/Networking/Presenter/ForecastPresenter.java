package exam.hydromap.julienheroguelle.hydromap.Networking.Presenter;

import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMRect;
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

    public void getForecastsByZip(String code, String country) {
        repository.getForecastsByZip(code, country);
    }

    public void getForecastsByName(String query, String country) {
        repository.getForecastsByName(query, country);
    }

    public void getForecastsByRect(OWMRect rect, Integer distance) {
        repository.getForecastsByRect(rect, distance);
    }

    public void getForecastsById(List<Integer> ids) {
        repository.getForecastsById(ids);
    }

        public void getForecastByCoords(Coords coords) {
        repository.getForecastByCoords(coords);
    }

    public void getForecastsByCycle(Coords coords, Integer count) {
        repository.getForecastsByCycle(coords, count);
    }

        public void didGotForecasts(List<Forecast> list, OWMError error) {
        listener.didGotForecasts(list, error);
    }

    public void didGotForecast(Forecast forecast, OWMError error){
        listener.didGotForecast(forecast, error);
    }
}
