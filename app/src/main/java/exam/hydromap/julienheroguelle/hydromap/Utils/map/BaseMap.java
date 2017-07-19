package exam.hydromap.julienheroguelle.hydromap.Utils.map;

/**
 * Created by madarow on 18/07/17.
 */

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.R;
import exam.hydromap.julienheroguelle.hydromap.Utils.App;

public abstract class BaseMap extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    protected int getLayoutId() {
        return R.layout.map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUpMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMap();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (mMap != null) {
            return;
        }
        mMap = map;
        startDemo();
    }

    private void setUpMap() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    /**
     * Run the demo-specific code.
     */
    protected abstract void startDemo();

    protected GoogleMap getMap() {
        return mMap;
    }

    /**
     * This function retrieve the mapView snapshot in order to put it in an imageView for preview or anything else
     * @param coord The center coords of the region you want to snapshot
     * @param imageView The imageview you want it to get image on
     */
    protected void getSnapshotOf(final Coords coord, final ImageView imageView) {

        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {

                mMap.addMarker(new MarkerOptions().position(coord.getLatLng()));

                CameraUpdate lastLocation = CameraUpdateFactory.newCameraPosition(mMap.getCameraPosition());
                CameraUpdate newLocation = CameraUpdateFactory.newLatLngZoom(coord.getLatLng(), 4.0f);

                mMap.moveCamera(newLocation);

                imageView.setImageBitmap(bitmap);

                mMap.moveCamera(lastLocation);

            }
        });
    }
}
