
package exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lon")
    @Expose
    public Float lon;
    @SerializedName("lat")
    @Expose
    public Float lat;

    public Coord(Float lat, Float lon) {
        this.lat = lat;
        this.lon = lon;
    }

}
