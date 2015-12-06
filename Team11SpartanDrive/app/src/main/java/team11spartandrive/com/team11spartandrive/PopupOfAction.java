package team11spartandrive.com.team11spartandrive;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import team11spartadrive.com.helper.DriveFiles;

/**
 * Created by Jainam on 12/1/2015.
 */
public class PopupOfAction extends Dialog implements View.OnClickListener {

    final static String SHARE = "Share";
    final static String DELETE = "Delete";
    final static String EMAIL = "Email";

    Context context;
    LinearLayout linearLayout;
    Button shareBtn;
    Button deleteBtn;
    Button emailBtn;
    DriveFiles driveFiles;
    private String temp_ID;


    public PopupOfAction(Context context, String temp_ID) {
        super(context);

        this.setTitle("Select Action");

        this.driveFiles = driveFiles;
        this.context = context;
        this.temp_ID = temp_ID;

        // INSTANTIATE LAYOUTS

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        shareBtn = new Button(context);
        deleteBtn = new Button(context);
        emailBtn = new Button(context);


        // SET TEXT AND TAG

        shareBtn.setText(SHARE);
        shareBtn.setTag(SHARE);

        deleteBtn.setText(DELETE);
        deleteBtn.setTag(DELETE);

        emailBtn.setText(EMAIL);
        emailBtn.setTag(EMAIL);

        // SET CLICK LISTENER

        shareBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        emailBtn.setOnClickListener(this);

        // ADD TEXTVIEW TO LINEARLAYOUT

        linearLayout.addView(shareBtn);
        linearLayout.addView(deleteBtn);
        linearLayout.addView(emailBtn);

        // SET CONTENT VIEW
        this.setContentView(linearLayout);

    }


    @Override
    public void onClick(View view) {


        if (view.getTag().toString().equalsIgnoreCase("SHARE")) {
            // UPDATE DATA

            Toast toast = Toast.makeText(context, "update called"+temp_ID, Toast.LENGTH_LONG);
            toast.show();


            //----------------PARSE-----------------------------

            //Send Push notification
            ParseQuery pushQuery = ParseInstallation.getQuery();
            pushQuery.whereEqualTo("username", "smitmehta93@gmail.com");
            // Send push notification to query
            ParsePush push = new ParsePush();
            push.setQuery(pushQuery); // Set our Installation query
            push.setMessage("Hello from the other side!");
            push.sendInBackground();

            //----------------PARSE------------------------------




        } else if(view.getTag().toString().equalsIgnoreCase("DELETE")){
            final String[] success = {""};
            final String[] error = {""};
            new Thread() {
                public void run() {
                    try

                    {
                        DriveFiles.getDriveFileInstance().drive_files.delete(temp_ID).execute();
                        Log.d("Sucessssss:", "file "+temp_ID+" deleted");
                        success[0] = "file "+temp_ID+" deleted";
                    } catch (
                            Exception e
                            )

                    {
                        Log.d("error in deletion", e.getMessage());
                        error[0] = e.getMessage();
                    }
                }
            }.start();

            Toast toast = Toast.makeText(context,"file "+temp_ID+" deleted", Toast.LENGTH_LONG);
            toast.show();

        } else if(view.getTag().toString().equalsIgnoreCase("EMAIL")){


            Toast toast = Toast.makeText(context, "email called"+temp_ID, Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            return;
        }

        // DISMISS
        //this.dismiss();
    }
}
//}