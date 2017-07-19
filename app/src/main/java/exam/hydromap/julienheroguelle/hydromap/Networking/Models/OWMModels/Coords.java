
package exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.maps.android.heatmaps.WeightedLatLng;

public class Coords {

    @SerializedName("Lon")
    @Expose
    public Double Lon;
    @SerializedName("Lat")
    @Expose
    public Double Lat;
    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("lat")
    @Expose
    public Double lat;

    public Coords(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public LatLng getLatLng() {
        return new LatLng(getLat(), getLon());
    }

    public Double getLat() {
        if (lat != null) {
            return lat;
        } else if (Lat != null) {
            return Lat;
        }
        return null;
    }

    public Double getLon() {
        if (lon != null) {
            return lon;
        } else if (Lon != null) {
            return Lon;
        }
        return null;
    }
}
