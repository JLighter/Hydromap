package exam.hydromap.julienheroguelle.hydromap.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.AWhereProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.ForecastProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.Norm;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.NormList;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Forecast;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.OWMError;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.AWherePresenter;
import exam.hydromap.julienheroguelle.hydromap.Networking.Presenter.ForecastPresenter;
import exam.hydromap.julienheroguelle.hydromap.R;
import exam.hydromap.julienheroguelle.hydromap.Utils.App;
import exam.hydromap.julienheroguelle.hydromap.Utils.map.BaseMap;

public class DetailActivity extends AppCompatActivity implements ForecastProtocol, AWhereProtocol {

    ImageView header;

    TextView placeLabel;
    TextView tempLabel;
    TextView humidityLabel;
    TextView pressureLabel;
    TextView speedLabel;
    TextView degreeLabel;
    TextView endDatePicker;
    TextView startDatePicker;
    TextView maxSummerLabel;
    TextView minSummerLabel;
    TextView maxWinterLabel;
    TextView minWinterLabel;

    Forecast forecast;

    Coords forecastCoords;

    ForecastPresenter forecastPresenter = new ForecastPresenter(this);
    AWherePresenter aWherePresenter = new AWherePresenter(this);

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
        endDatePicker = (TextView) findViewById(R.id.endDatePicker);
        startDatePicker = (TextView) findViewById(R.id.startDatePicker);
        maxSummerLabel = (TextView) findViewById(R.id.maxSummerLabel);
        minSummerLabel = (TextView) findViewById(R.id.minSummerLabel);
        maxWinterLabel = (TextView) findViewById(R.id.maxWinterLabel);
        minWinterLabel = (TextView) findViewById(R.id.minWinterLabel);

        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        startDatePicker.setText(String.format("%d", currentYear-4));
        endDatePicker.setText(String.format("%d", currentYear-1));

        Double latitude = getIntent().getExtras().getDouble("latitude");
        Double longitude = getIntent().getExtras().getDouble("longitude");

        forecastCoords = new Coords(latitude, longitude);

        if (latitude != null && longitude != null) {
            forecastPresenter.getForecastByCoords(forecastCoords);

            setHeaderWith(forecastCoords);
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void didGotForecast(Forecast forecast, OWMError error) {
        if (error != null) {
            Log.e("ERROR GETTING FORECAST", error.message);
        } else if (forecast != null) {
            this.forecast = forecast;
            placeLabel.setText(forecast.name);
            tempLabel.setText(String.format("%.2f K", forecast.main.temp));
            humidityLabel.setText(String.format("%.0f %%", forecast.main.humidity));
            pressureLabel.setText(String.format("%.0f Pa", forecast.main.pressure));
            speedLabel.setText(String.format("%.0f km/h", forecast.wind.speed));
            degreeLabel.setText(String.format("%.0f °", forecast.wind.deg));

            if (forecast.coord.lat != null && forecast.coord.lon != null){
                setHeaderWith(forecast.coord);
            }

            didTapRefreshBtn();

        }
    }

    public void didTapRefreshBtn() {
        getSummerTemp();
        getWinterTemp();
    }

    private void getWinterTemp() {
        aWherePresenter.getNormsPrecisely(forecastCoords, "12-21", "01-21", startDatePicker.getText().toString(), endDatePicker.getText().toString());
    }

    private void getSummerTemp() {
        aWherePresenter.getNormsPrecisely(forecastCoords, "06-21", "09-21", startDatePicker.getText().toString(), endDatePicker.getText().toString());
    }

    @Override
    public void didGotNorm(Norm norm, VolleyError error) {
    }

    @Override
    public void didGotNorms(NormList norms, VolleyError error) {
        if (error != null) {
            Log.e("NORM ERROR", error.getLocalizedMessage());
        } else if (norms != null) {
            Norm first = norms.norms.get(0);
            Log.d("DID GOT NORM FOR SUMMER", first.day);
            if (first.day.equals("12-21")) {
                setSummerLabelWith(norms);
            } else if (first.day.equals("06-21")) {
                setWinterLabelWith(norms);
            }

        }
    }

    private void setSummerLabelWith(NormList normList){
        Float min = 0f;
        Float max = 0f;

        for (Norm norm : normList.norms) {
            Log.d("DID GOT NORM FOR SUMMER", String.format("%f     %f", norm.maxHumidity.average, norm.minHumidity.average));
            min += norm.minHumidity.average;
            max += norm.maxHumidity.average;
        }

        min /= normList.norms.size();
        max /= normList.norms.size();

        minSummerLabel.setText(String.format("%.0f %%", min));
        maxSummerLabel.setText(String.format("%.0f %%", max));
    }

    private void setWinterLabelWith(NormList normList){
        Float min = 0f;
        Float max = 0f;

        for (Norm norm : normList.norms) {
            Log.d("DID GOT NORM FOR WINTER", String.format("%f     %f", norm.maxHumidity.average, norm.minHumidity.average));
            min += norm.minHumidity.average;
            max += norm.maxHumidity.average;
        }

        min /= normList.norms.size();
        max /= normList.norms.size();

        minWinterLabel.setText(String.format("%.0f %%", min));
        maxWinterLabel.setText(String.format("%.0f %%", max));
    }

    private void setHeaderWith(final Coords coords) {
        Integer ratio = 16/9;
        Integer pictureHeight = 200;

        String url = "http://maps.google.com/maps/api/staticmap?center=" + coords.lat + "," + coords.lon + "&zoom=" + BaseMap.zoomLevel + "&size=" +  500 + "x" + 200 + "&sensor=false&maptype=terrain&format=jpeg&visual_refresh=true";
        Log.d("STATIC MAP URL", url);
        Picasso.with(App.getAppContext()).load(url).into(header);
    }

    @Override
    public void didGotForecasts(List<Forecast> forecasts, OWMError error) {

    }
}
