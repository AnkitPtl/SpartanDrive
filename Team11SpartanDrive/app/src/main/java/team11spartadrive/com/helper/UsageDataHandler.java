package team11spartadrive.com.helper;

import android.graphics.Picture;
import android.util.Log;

import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.DecimalFormat;

/**
 * Created by harsh on 12/11/2015.
 */
public class UsageDataHandler {

    public static About about = null;
    public static UsageDataHandler usageInfo = null;
    public static String userName;
    public static String userEmail;
    public static User.Picture profilePic;
    public static  String url;
    double totalSpace;
    double totalSpaceUsed;
    double totalFreeSpace;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        UsageDataHandler.url = url;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UsageDataHandler.userName = userName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        UsageDataHandler.userEmail = userEmail;
    }

    public static User.Picture getProfilePic() {
        return profilePic;
    }

    public static void setProfilePic(User.Picture profilePic) {
        UsageDataHandler.profilePic = profilePic;
    }

    public double getTotalSpaceUsed() {
        return totalSpaceUsed;
    }

    public void setTotalSpaceUsed(double totalSpaceUsed) {
        this.totalSpaceUsed = totalSpaceUsed;
    }

    public Double getTotalFreeSpace() {
        return totalFreeSpace;
    }

    public void setTotalFreeSpace(double totalFreeSpace) {
        this.totalFreeSpace = totalFreeSpace;
    }

    public double getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(double totalSpace) {
        this.totalSpace = totalSpace;
    }

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
                    totalSpace = ((double)about.getQuotaBytesTotal() / 1073741824);
                    totalSpaceUsed = ((double)about.getQuotaBytesUsedAggregate() / 1073741824);
                    totalFreeSpace = totalSpace - totalSpaceUsed;
                    userName = about.getUser().getDisplayName();
                    userEmail = about.getUser().getEmailAddress();
                    profilePic = about.getUser().getPicture();
                    try {
                        JSONObject json=  new JSONObject(String.valueOf(profilePic));
                        //url =  java.net.URLDecoder.decode(String.valueOf(json), "UTF-8");
                        url = (String) json.get("url") ;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("UsageDate",url);
                }
                catch(Exception e){

                }


            }


        }.run();
    }
}
