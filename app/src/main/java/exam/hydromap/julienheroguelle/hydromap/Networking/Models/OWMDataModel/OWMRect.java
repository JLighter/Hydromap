package exam.hydromap.julienheroguelle.hydromap.Networking.Models.OWMDataModel;

/**
 * Created by julienheroguelle on 17/07/2017.
 */

public class OWMRect {
    public Float topLon;
    public Float leftLat;
    public Float bottomLon;
    public Float rightLat;

    public OWMRect(Float topLon, Float leftLat, Float bottomLon, Float rightLat) {
        this.topLon = topLon;
        this.leftLat = leftLat;
        this.bottomLon = bottomLon;
        this.rightLat = rightLat;
    }
}
