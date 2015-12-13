package team11spartadrive.com.helper;

import android.content.Context;
import android.content.Intent;

import team11spartandrive.com.team11spartandrive.HomePageActivity;

/**
 * Created by student on 12/12/15.
 */
public class RefreshAction {

    public void updateAction(Context context){

        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);

    }
}
