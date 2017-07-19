package exam.hydromap.julienheroguelle.hydromap.Utils.Objects;

/**
 * Created by julienheroguelle on 18/07/2017.
 */

import java.util.concurrent.Callable;

public abstract class Callback<T> implements Callable<Void> {
    public T result;

    public void setResult (T result) {
        this.result = result;
    }

    public abstract Void call ();
}