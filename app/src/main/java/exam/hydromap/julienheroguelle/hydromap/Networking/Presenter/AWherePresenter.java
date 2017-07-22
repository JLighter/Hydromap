package exam.hydromap.julienheroguelle.hydromap.Networking.Presenter;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import exam.hydromap.julienheroguelle.hydromap.Networking.Interfaces.AWhereProtocol;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.Norm;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.AWhereModel.NormList;
import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;
import exam.hydromap.julienheroguelle.hydromap.Networking.Repository.AWhereRepository;

/**
 * Created by julienheroguelle on 18/07/2017.
 */

public class AWherePresenter {

    AWhereProtocol listener;
    AWhereRepository repository;

    public AWherePresenter(AWhereProtocol listener) {
        this.listener = listener;
        repository = new AWhereRepository(this);
    }

    public void getNorms(final Coords coords, final String day, final String month, final Integer startYear, final Integer endYear) {
        repository.getNorms(coords, day, month, startYear, endYear);
    }

    public void getNormsPrecisely(final Coords coords, final String startDay, final String endDay, final String startYear, final String endYear) {
        repository.getNormsPrecisely(coords, startDay, endDay, startYear, endYear);
    }

    public void didGotNorm(Norm norm, VolleyError error) {
        listener.didGotNorm(norm, error);
    }

    public void didGotNorms(NormList norms, VolleyError error) {
        listener.didGotNorms(norms, error);
    }
}
