package exam.hydromap.julienheroguelle.hydromap.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by julienheroguelle on 12/06/2017.
 */

public class Networking {
    private static Networking mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private Networking(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized Networking getInstance() {
        if (mInstance == null) {
            mInstance = new Networking(App.getAppContext());
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    static public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    static public String getAPIKeyArgument() {
        return "&APPID=b8c31462c4d84f66cee3773b95fdddd9";
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}