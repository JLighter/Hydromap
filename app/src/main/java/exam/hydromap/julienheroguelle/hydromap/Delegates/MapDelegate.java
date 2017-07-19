package exam.hydromap.julienheroguelle.hydromap.Delegates;

import com.google.android.gms.maps.model.LatLng;

import exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMModels.Coords;

/**
 * Created by julienheroguelle on 19/07/2017.
 */

public interface MapDelegate {
    public void didTapOnMapAt(Coords coords);
}
