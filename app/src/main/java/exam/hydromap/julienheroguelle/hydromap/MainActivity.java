package exam.hydromap.julienheroguelle.hydromap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.function.Consumer;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Coord;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.ForecastPresenter;

public class MainActivity extends AppCompatActivity implements ForecastProtocol {

    ForecastPresenter presenter = new ForecastPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.getFrenchForecasts();

        presenter.getForecastbyCoords(new Coord(48.852517f, 2.236061f));
    }

    @Override
    public void didGotForecasts(List<Forecast> forecasts, OWMError error) {
        if (error != null) {
            System.out.print(error);
        } else {
            for (Forecast forecast : forecasts) {
                Log.d("INFO", forecast.name + " " + forecast.main.temp);
            }
        }
    }

    @Override
    public void didGotForecast(Forecast forecast, OWMError error) {
        if (error != null) {
            System.out.print(error);
        } else {
            Log.d("NEW INFO", forecast.name + " " + forecast.main.temp);
        }
    }
}
