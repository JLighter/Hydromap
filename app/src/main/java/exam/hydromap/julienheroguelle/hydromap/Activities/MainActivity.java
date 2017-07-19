package exam.hydromap.julienheroguelle.hydromap.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Delegates.MapDelegate;
import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMRect;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.ForecastPresenter;
import exam.hydromap.julienheroguelle.hydromap.R;
import exam.hydromap.julienheroguelle.hydromap.Utils.App;
import exam.hydromap.julienheroguelle.hydromap.Utils.map.BaseMap;
import exam.hydromap.julienheroguelle.hydromap.Utils.map.MapHeatsActivity;

public class MainActivity extends AppCompatActivity implements ForecastProtocol, MapDelegate{

    ForecastPresenter presenter = new ForecastPresenter(this);

    private Fragment heatMap;

    public SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchView = (SearchView) findViewById(R.id.searchView);

        heatMap = getSupportFragmentManager().findFragmentById(R.id.heatMap);

        OWMRect rect = new OWMRect(-5f, 42f, 8f, 51f);

        presenter.getForecastsByRect(rect, 10);

        ((MapHeatsActivity) heatMap).delegate = this;

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.e("onQueryTextChange", "called");
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {



                presenter.getForecastsByName(query,"FR");
                return false;
            }

        });
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    public void changeGradient(View view) {
        ((MapHeatsActivity) heatMap).changeGradient();
    }

    @Override
    public void didGotForecasts(List<Forecast> forecasts, OWMError error) {
        if (error != null) {
            Log.e("FORECAST", error.toString());
        } else if (forecasts != null){

            ArrayList<WeightedLatLng> list = new ArrayList<>();

            for (Forecast forecast : forecasts) {
                if (forecast.coord != null) {
                    Coords coords = new Coords(forecast.coord.lat, forecast.coord.lon);
                    WeightedLatLng wLatLng =  new WeightedLatLng(coords.getLatLng(), (forecast.main.humidity/100) * 2 + 0.01);
                    list.add(wLatLng);

                    Log.d("FORECAST WEIGHT", forecast.main.temp.toString());
                }
            }

            //((MapHeatsActivity) heatMap).initData(list);

        }
    }

    @Override
    public void didGotForecast(Forecast forecast, OWMError error) {

    }

    @Override
    public void didTapOnMapAt(Coords coords) {
        Intent detailIntent = new Intent(App.getAppContext(), DetailActivity.class);
        detailIntent.putExtra("latitude", coords.lat);
        detailIntent.putExtra("longitude", coords.lon);
        startActivity(detailIntent);
    }

    // Only for SearchView.VERSION_MENU_ITEM
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_search: {
//                mSearchView.open(true); // enable or disable animation
//                return true;
//            }
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
