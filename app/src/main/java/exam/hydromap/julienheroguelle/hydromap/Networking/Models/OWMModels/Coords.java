
package exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.heatmaps.WeightedLatLng;

public class Coords {

    @SerializedName("Lon")
    @Expose
    public Double lon;
    @SerializedName("Lat")
    @Expose
    public Double lat;

    public Coords(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public LatLng getLatLng() {
        return new LatLng(this.lat, this.lon);
    }

}
