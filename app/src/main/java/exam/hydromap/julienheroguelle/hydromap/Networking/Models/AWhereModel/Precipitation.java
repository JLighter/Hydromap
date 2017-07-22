
package exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Precipitation {

    @SerializedName("average")
    @Expose
    public Float average;
    @SerializedName("stdDev")
    @Expose
    public Float stdDev;
    @SerializedName("units")
    @Expose
    public String units;

}
