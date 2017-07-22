package exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AWhereError {

    @SerializedName("detailedMessage")
    @Expose
    public String detailedMessage;
    @SerializedName("errorId")
    @Expose
    public String errorId;
    @SerializedName("simpleMessage")
    @Expose
    public String simpleMessage;
    @SerializedName("statusCode")
    @Expose
    public Integer statusCode;
    @SerializedName("statusName")
    @Expose
    public String statusName;

}