package exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces;

import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.OWMError;

/**
 * Created by julienheroguelle on 16/07/2017.
 */

interface ForecastProtocolInferred {
    void didGotForecasts(List<Forecast> forecasts, OWMError error);
    void didGotForecast(Forecast forecast, OWMError error);
}

public interface ForecastProtocol extends ForecastProtocolInferred {
    void didGotForecasts(final List<Forecast> forecasts, OWMError error);
    void didGotForecast(final Forecast forecast, OWMError error);
}
