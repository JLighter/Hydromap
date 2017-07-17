package exam.hydromap.julienheroguelle.hydromap.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by julienheroguelle on 13/06/2017.
 */

public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}
