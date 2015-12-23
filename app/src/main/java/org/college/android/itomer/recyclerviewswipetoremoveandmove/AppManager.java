package org.college.android.itomer.recyclerviewswipetoremoveandmove;

import android.app.Application;
import com.parse.Parse;

public class AppManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // [Optional] Power your app with Local Datastore.
        // For more info, visit:
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
