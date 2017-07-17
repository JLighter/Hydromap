package exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by julienheroguelle on 14/06/2017.
 */

public class OWMError {
    @SerializedName("status")
    @Expose
    public String cod;
    @SerializedName("message")
    @Expose
    public String message;

    public OWMError(String status, String message) {
        this.cod = status;
        this.message = message;
    }
}
