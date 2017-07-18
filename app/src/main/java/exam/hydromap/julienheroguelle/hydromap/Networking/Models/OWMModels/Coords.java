
package exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coords {

    @SerializedName("lon")
    @Expose
    public Float lon;
    @SerializedName("lat")
    @Expose
    public Float lat;

    public Coords(Float lat, Float lon) {
        this.lat = lat;
        this.lon = lon;
    }

}
