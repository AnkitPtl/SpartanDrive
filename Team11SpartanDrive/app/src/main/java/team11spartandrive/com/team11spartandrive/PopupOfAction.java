package team11spartandrive.com.team11spartandrive;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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


    public PopupOfAction(Context context,DriveFiles driveFiles) {
        super(context);


        this.setTitle("Select Action");

        this.driveFiles = driveFiles;
        this.context = context;


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


        /*if(view.getTag().toString().equalsIgnoreCase(UPDATE)){
            // UPDATE DATA
            new PopupOfUpdateOrAdd((MainActivity)context, dog).show();

        }else{
            // DELETE DATA
            // INSTANTIATE DB HELPER
            MainActivity mainActivity = (MainActivity) context;
            MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
            dbHelper.delete(dog.getId());


            // GET ALL RECORD
            Dog[] dogs = dbHelper.getRecords();

            dbHelper.db.close();
            // REMOVE LISTVIEW FROM LINEARLAYOUT
            mainActivity.linearLayout.removeView(mainActivity.listView);

            // REINSTANTIATE LISTVIEW
            mainActivity.listView = new ListView(mainActivity);
            mainActivity.listView.setOnItemClickListener(mainActivity);

            // SET ADAPTER
            mainActivity.listView.setAdapter(new CustomArrayAdapter(mainActivity,
                    dogs));

            // ADD LISTVIEW TO LINEARLAYOUT
            mainActivity.linearLayout.addView(mainActivity.listView);


*/
        }

        // DISMISS
        //this.dismiss();
    }

//}