package exam.hydromap.julienheroguelle.hydromap;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel.OWMRect;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.ForecastPresenter;
import exam.hydromap.julienheroguelle.hydromap.Utils.map.MapHeatsActivity;


public class MainActivity  extends AppCompatActivity implements ForecastProtocol {



    ForecastPresenter presenter = new ForecastPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // FrenchRect = -5,42,8,51
        OWMRect rect = new OWMRect(-5f, 42f, 8f, 51f);
        presenter.getForecastByCoords(new Coords(48.852517f, 2.236061f));

        presenter.getForecastsByZip("91100", "fr");

        List<Integer> ids = new ArrayList<Integer>();
        ids.add(524901);
        ids.add(2643743);
        ids.add(703448);
        presenter.getForecastsById(ids);

        presenter.getForecastsByName("Paris", "fr");

        Intent i = new Intent(this, MapHeatsActivity.class);
       startActivity(i);
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
}
