package exam.hydromap.julienheroguelle.hydromap.Utils.map;

/**
 * Created by madarow on 18/07/17.
 */


import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

import exam.hydromap.julienheroguelle.hydromap.Delegates.MapDelegate;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Forecast;
import exam.hydromap.julienheroguelle.hydromap.R;


/**
 * A demo of the Heatmaps library. Demonstrates how the HeatmapTileProvider can be used to create
 * a colored map overlay that visualises many points of weighted importance/intensity, with
 * different colors representing areas of high and low concentration/combined intensity of points.
 */
public class MapHeatsActivity extends BaseMap {

    // For both versions
    /**
     * Hydro map gradient
     * Generally blue
     */
    private static final int[] HYDRO_MAP_GRADIENT_COLORS = {
            Color.argb(0, 0, 255, 255),// transparent
            Color.argb(255 / 3 * 2, 0, 0, 255),
            Color.rgb(0, 0, 255),
            Color.rgb(70, 70, 200),
            Color.rgb(100, 100, 170)
    };

    public static final float[] HYDRO_MAP_GRADIENT_START_POINTS = {
            0.0f, 0.10f, 0.20f, 0.60f, 1.0f
    };

    /**
     * Tmp heat map gradient
     * Generally Red -> Green
     */
    private static final int[] TEMP_MAP_GRADIENT_COLORS = {
            Color.argb(0, 0, 255, 255),// transparent
            Color.argb(255 / 3 * 2, 0, 255, 255),
            Color.rgb(0, 0, 255),
            Color.rgb(0, 150, 127),
            Color.rgb(255, 0, 0)
    };

    public static final float[] TEMP_MAP_GRADIENT_START_POINTS = {
            0.0f, 0.10f, 0.20f, 0.60f, 1.0f
    };

    public static final Gradient HYDRO_MAP_GRADIENT = new Gradient(HYDRO_MAP_GRADIENT_COLORS,
            HYDRO_MAP_GRADIENT_START_POINTS);
    public static final Gradient TEMP_MAP_GRADIENT = new Gradient(TEMP_MAP_GRADIENT_COLORS,
            TEMP_MAP_GRADIENT_START_POINTS);

    private static HeatmapTileProvider mProvider;
    private static TileOverlay mOverlay;

    private static boolean mHeatGradient = true;

    private List<WeightedLatLng> currentList;

    public MapHeatsActivity() {

    }

    @Override
    protected void startDemo() {
       getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.85, 2.34), 4));

    }

    public void changeGradient() {
        if (mProvider != null && mOverlay != null) {
            if (mHeatGradient) {
                mProvider.setGradient(TEMP_MAP_GRADIENT);
            } else {
                mProvider.setGradient(HYDRO_MAP_GRADIENT);
            }
            mOverlay.clearTileCache();
            mHeatGradient = !mHeatGradient;
        }
    }

    public void initData(ArrayList<WeightedLatLng> list) {
        currentList = list;
        mProvider = new HeatmapTileProvider.Builder().weightedData(list).radius(50).build();
        mOverlay = getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        super.onMapClick(latLng);
    }


}
