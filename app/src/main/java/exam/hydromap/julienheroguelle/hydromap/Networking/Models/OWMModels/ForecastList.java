
package exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForecastList {

    @SerializedName("cod")
    @Expose
    public Integer cod;
    @SerializedName("calctime")
    @Expose
    public Float calctime;
    @SerializedName("cnt")
    @Expose
    public Integer cnt;
    @SerializedName("list")
    @Expose
    public java.util.List<Forecast> list = null;

}