
package exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Norm {

    @SerializedName("day")
    @Expose
    public String day;
    @SerializedName("location")
    @Expose
    public Location location;
    @SerializedName("meanTemp")
    @Expose
    public MeanTemp meanTemp;
    @SerializedName("maxTemp")
    @Expose
    public MaxTemp maxTemp;
    @SerializedName("minTemp")
    @Expose
    public MinTemp minTemp;
    @SerializedName("precipitation")
    @Expose
    public Precipitation precipitation;
    @SerializedName("solar")
    @Expose
    public Solar solar;
    @SerializedName("minHumidity")
    @Expose
    public MinHumidity minHumidity;
    @SerializedName("maxHumidity")
    @Expose
    public MaxHumidity maxHumidity;
    @SerializedName("dailyMaxWind")
    @Expose
    public DailyMaxWind dailyMaxWind;
    @SerializedName("averageWind")
    @Expose
    public AverageWind averageWind;
    @SerializedName("_links")
    @Expose
    public Links links;

}
