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

    private EditText editTextSearch;

    ForecastPresenter presenter = new ForecastPresenter(this);

    private Fragment heatMap;

    public SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        editTextSearch = (EditText) findViewById(R.id.editTextSearch);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setArrowOnly(true);
        mSearchView.setVoice(false);
        mSearchView.setHint(R.string.search_view_hint);
        heatMap = getSupportFragmentManager().findFragmentById(R.id.heatMap);

        ((MapHeatsActivity) heatMap).delegate = this;


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getForecastsByName(query,"");

                mSearchView.open(true); // enable or disable animation

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
            Log.e("UNEXPECTED FORECASTS", forecasts.toString());
        }
    }

    @Override
    public void didGotForecast(Forecast forecast, OWMError error) {
        if (error != null) {
            Log.e("FORECAST", error.toString());
        } else if (forecast != null) {
            didTapOnMapAt(new Coords(forecast.coord.getLat(), forecast.coord.getLon()));
        }
    }

    @Override
    public void didTapOnMapAt(Coords coords) {
        Intent detailIntent = new Intent(App.getAppContext(), DetailActivity.class);
        detailIntent.putExtra("latitude", coords.lat);
        detailIntent.putExtra("longitude", coords.lon);
        startActivity(detailIntent);
    }
}
