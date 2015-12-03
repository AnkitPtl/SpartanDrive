package team11spartandrive.com.team11spartandrive;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import team11spartadrive.com.helper.DriveFiles;

/**
 * Created by Jainam on 12/1/2015.
 */
public class PopupOfAction extends Dialog implements View.OnClickListener {

    final static String SHARE = "Share File";
    final static String DELETE = "Delete";

    Context context;
    LinearLayout linearLayout;
    Button shareBtn;
    Button deleteBtn;
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


        // SET TEXT AND TAG

        shareBtn.setText(SHARE);
        shareBtn.setTag(SHARE);

        deleteBtn.setText(DELETE);
        deleteBtn.setTag(DELETE);

        // SET CLICK LISTENER

        shareBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        // ADD TEXTVIEW TO LINEARLAYOUT

        linearLayout.addView(shareBtn);
        linearLayout.addView(deleteBtn);

        // SET CONTENT VIEW
        this.setContentView(linearLayout);

    }


    @Override
    public void onClick(View view) {


        if (view.getTag().toString().equalsIgnoreCase("SHARE FILE")) {
            // UPDATE DATA

            Toast toast = Toast.makeText(context, "update called"+temp_ID, Toast.LENGTH_LONG);
            toast.show();

        } else if(view.getTag().toString().equalsIgnoreCase("DELETE")){

//            try {
//                drive_files.delete(temp_ID).execute();
//                Log.d("Sucessssss:", "yeeeeeeeeey deleted");
//            }
//            catch (Exception e){
//                Log.d("error in deletion",e.getMessage());
//            }

            Toast toast = Toast.makeText(context, "delete called"+temp_ID, Toast.LENGTH_LONG);
            toast.show();
        }

        // DISMISS
        //this.dismiss();
    }
}
//}