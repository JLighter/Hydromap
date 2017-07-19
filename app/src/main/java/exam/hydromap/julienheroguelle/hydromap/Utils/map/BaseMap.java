package exam.hydromap.julienheroguelle.hydromap.Utils.map;

/**
 * Created by madarow on 18/07/17.
 */

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

import exam.hydromap.julienheroguelle.hydromap.Delegates.MapDelegate;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Utils.App;

public abstract class BaseMap extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    protected GoogleMap mMap;

    public MapDelegate delegate;

    final public static Integer zoomLevel = 10;
    final public static LatLng defaultLatLng = new LatLng(48.85, 2.34);

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        setUpMap();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;
        mMap.setOnMapClickListener(this);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setTrafficEnabled(false);
        mMap.setMaxZoomPreference(zoomLevel);

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, zoomLevel));
    }

    private void setUpMap() {
        getMapAsync(this);
    }

    protected GoogleMap getMap() {
        return mMap;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        final Coords coords = new Coords(latLng.latitude, latLng.longitude);

        CameraPosition position = new CameraPosition.Builder().zoom(zoomLevel).target(latLng).build();
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(position);
        mMap.animateCamera(camera);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                delegate.didTapOnMapAt(coords);
            }
        }, 2000);

    }
}
