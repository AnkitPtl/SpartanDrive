package team11spartadrive.com.helper;

import android.graphics.Picture;
import android.util.Log;

import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.User;

/**
 * Created by harsh on 12/11/2015.
 */
public class UsageDataHandler {

    public static About about = null;
    static UsageDataHandler usageInfo = null;
    public static String userName;
    public static String userEmail;
    public static User.Picture profilePic;
    Long totalSpace;
    Long totalSpaceUsed;

    Long totalFreeSpace;



    public static UsageDataHandler getUsageInstance(){

        if(usageInfo == null){
            usageInfo = new UsageDataHandler();
            return usageInfo;
        }
        else{
            return usageInfo;
        }

    }


    public void setAbout(final About about){

        new Thread(){

            public void run(){

                try {
                    UsageDataHandler.about = about;
                    totalSpace = (about.getQuotaBytesTotal() / 1000000000);
                    totalSpaceUsed = (about.getQuotaBytesUsed() / 1000000000);
                    totalFreeSpace = totalSpace - totalSpaceUsed;
                    userName = about.getUser().getDisplayName();
                    userEmail = about.getUser().getEmailAddress();
                    profilePic = about.getUser().getPicture();
                    Log.d("UsageDate", String.valueOf(about.getQuotaBytesUsed()));
                }
                catch(Exception e){

                }


        }


        }.run();
    }
}
