package exam.hydromap.julienheroguelle.hydromap.Activities;

import android.location.Location;
import android.os.RemoteException;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;

import java.util.List;
import java.util.Map;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.ForecastPresenter;
import exam.hydromap.julienheroguelle.hydromap.R;
import exam.hydromap.julienheroguelle.hydromap.Utils.App;
import exam.hydromap.julienheroguelle.hydromap.Utils.map.BaseMap;

public class DetailActivity extends AppCompatActivity implements ForecastProtocol, OnMapReadyCallback {

    ImageView header;

    TextView placeLabel;
    TextView tempLabel;
    TextView humidityLabel;
    TextView pressureLabel;
    TextView speedLabel;
    TextView degreeLabel;

    Forecast forecast;

    Coords forecastCoords;

    ForecastPresenter presenter = new ForecastPresenter(this);

    public DetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        header = (ImageView) findViewById(R.id.header);

        placeLabel = (TextView) findViewById(R.id.placeLabel);
        tempLabel = (TextView) findViewById(R.id.tempLabel);
        humidityLabel = (TextView) findViewById(R.id.humidityLabel);
        pressureLabel = (TextView) findViewById(R.id.pressureLabel);
        speedLabel = (TextView) findViewById(R.id.speedLabel);
        degreeLabel = (TextView) findViewById(R.id.degreeLabel);

        Double latitude = getIntent().getExtras().getDouble("latitude");
        Double longitude = getIntent().getExtras().getDouble("longitude");

        forecastCoords = new Coords(latitude, longitude);

        if (latitude != null && longitude != null) {
            presenter.getForecastByCoords(forecastCoords);
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    //    header.setImageBitmap(BaseMap.getSnapshotOf(forecast.coord, googleMap));
    }

    @Override
    public void didGotForecast(Forecast forecast, OWMError error) {
        if (error != null) {
            Log.e("ERROR GETTING FORECAST", error.message);
        } else if (forecast != null) {
            this.forecast = forecast;
            placeLabel.setText(forecast.name);
            tempLabel.setText(String.format("%.2f C", forecast.main.temp));
            humidityLabel.setText(String.format("%.0f %%", forecast.main.humidity));
            pressureLabel.setText(String.format("%.0f Pa", forecast.main.pressure));
            speedLabel.setText(String.format("%.0f km/h", forecast.wind.speed));
            degreeLabel.setText(String.format("%.0f °", forecast.wind.deg));
        }
    }

    @Override
    public void didGotForecasts(List<Forecast> forecasts, OWMError error) {

    }
}
