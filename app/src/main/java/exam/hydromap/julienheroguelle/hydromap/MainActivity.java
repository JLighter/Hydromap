package exam.hydromap.julienheroguelle.hydromap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.AWhereProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.Norm;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMRect;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.AWherePresenter;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.ForecastPresenter;

public class MainActivity extends AppCompatActivity implements ForecastProtocol, AWhereProtocol {

    ForecastPresenter presenter = new ForecastPresenter(this);
    AWherePresenter aWherePresenter = new AWherePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // FrenchRect = -5,42,8,51
        OWMRect rect = new OWMRect(-5f, 42f, 8f, 51f);
        Coords coords = new Coords(48.852517f, 2.236061f);
        presenter.getForecastByCoords(coords);

        presenter.getForecastsByZip("91100", "fr");

        List<Integer> ids = new ArrayList<Integer>();
        ids.add(524901);
        ids.add(2643743);
        ids.add(703448);
        presenter.getForecastsById(ids);

        presenter.getForecastsByName("Paris", "fr");

        aWherePresenter.getNorms(coords, "06", "01", 2014, 2017);

    }

    @Override
    public void didGotForecasts(List<Forecast> forecasts, OWMError error) {
        if (error != null) {
            System.out.print(error);
        } else {
            for (Forecast forecast : forecasts) {
                Log.d("MULTIPLE INFO", forecast.name + " " + forecast.main.temp);
            }
        }
    }

    @Override
    public void didGotForecast(Forecast forecast, OWMError error) {
        if (error != null) {
            System.out.print(error);
        } else {
            Log.d("SINGLE INFO", forecast.name + " " + forecast.main.temp + " ");
        }
    }

    @Override
    public void didGotNorm(Norm norm, VolleyError error) {
        if (error != null) {
            System.out.print(error);
        } else {
            Log.d("NORM INFO", norm.links.toString());
        }
    }
}
