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
        Parse.initialize(this, "cuWaQgvizqJbbw3mGbH32yCaBoaDa1mMDFapYmTI", "r0qkNJbs79ayuWDqVUYnfMjI3vFQfDdJ89jzpgQd");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
