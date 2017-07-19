package exam.hydromap.julienheroguelle.hydromap.Utils.map;

/**
 * Created by madarow on 18/07/17.
 */


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A demo of the Heatmaps library. Demonstrates how the HeatmapTileProvider can be used to create
 * a colored map overlay that visualises many points of weighted importance/intensity, with
 * different colors representing areas of high and low concentration/combined intensity of points.
 */
public class MapHeatsActivity extends BaseMap {

    /**
     * Alternative radius for convolution
     */
    private static final int ALT_HEATMAP_RADIUS = 10;

    /**
     * Alternative opacity of heatmap overlay
     */
    private static final double ALT_HEATMAP_OPACITY = 0.4;

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

    public MapHeatsActivity() {

    }

    /**
     * Maps name of data set to data (list of LatLngs)
     * Also maps to the URL of the data set for attribution
     */
    private HashMap<String, DataSet> mLists = new HashMap<String, DataSet>();

    @Override
    protected void startDemo() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-25, 143), 4));

        // Make the handler deal with the map
        // Input: list of WeightedLatLngs, minimum and maximum zoom levels to calculate custom
        // intensity from, and the map to draw the heatmap on
        // radius, gradient and opacity not specified, so default are used
    }

    public static void changeGradient() {

        // TODO : init mProvier and mOverlay properties
        /*
        if (mHeatGradient) {
            mProvider.setGradient(TEMP_MAP_GRADIENT);
        } else {
            mProvider.setGradient(HYDRO_MAP_GRADIENT);
        }
        mOverlay.clearTileCache();
        mHeatGradient = !mHeatGradient;
        */
    }



    /**
     * Helper class - stores data sets and sources.
     */
    private class DataSet {
        private ArrayList<LatLng> mDataset;
        private String mUrl;

        public DataSet(ArrayList<LatLng> dataSet, String url) {
            this.mDataset = dataSet;
            this.mUrl = url;
        }

        public ArrayList<LatLng> getData() {
            return mDataset;
        }

        public String getUrl() {
            return mUrl;
        }
    }

}
