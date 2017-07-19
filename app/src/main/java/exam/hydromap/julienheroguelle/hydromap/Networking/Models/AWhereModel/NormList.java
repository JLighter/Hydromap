package exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by julienheroguelle on 20/07/2017.
 */

public class NormList {
    @SerializedName("norms")
    @Expose
    public List<Norm> norms;
    @SerializedName("_links")
    @Expose
    public Links _links;
}
