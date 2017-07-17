package exam.hydromap.julienheroguelle.hydromap.Utils;

import exam.hydromap.julienheroguelle.hydromap.*;

/**
 * Created by julienheroguelle on 13/06/2017.
 */

public class Helper {
    private static final Helper ourInstance = new Helper();

    public static Helper shared() {
        return ourInstance;
    }

    private Helper() {
    }
}
