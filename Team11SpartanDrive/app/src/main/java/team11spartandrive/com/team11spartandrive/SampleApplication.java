package team11spartandrive.com.team11spartandrive;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Shubhi on 11/17/2015.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(this, "fCQwyicUY80G8br3pDeKfktKRGR8KdIqyOwS274D", "nLJx2nS5S22jOPtj1eXyyeIsmYZwc4rUVOaiJha3");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
