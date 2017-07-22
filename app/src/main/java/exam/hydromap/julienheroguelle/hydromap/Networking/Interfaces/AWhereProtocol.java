package exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces;

import com.android.volley.VolleyError;

import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.Norm;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.NormList;

/**
 * Created by julienheroguelle on 18/07/2017.
 */

public interface AWhereProtocol {
    void didGotNorm(Norm norm, VolleyError error);
    void didGotNorms(NormList norms, VolleyError error);
}
