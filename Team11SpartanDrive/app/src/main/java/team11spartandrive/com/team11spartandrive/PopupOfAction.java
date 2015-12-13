package team11spartandrive.com.team11spartandrive;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;
import com.google.api.services.drive.model.File;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import team11spartadrive.com.helper.DriveFiles;
import team11spartadrive.com.helper.RefreshAction;

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
    private String option_type="";
    private String email="";


    public PopupOfAction(Context context, String temp_ID, String param1) {
        super(context);

        this.setTitle("Select Action");
        this.option_type = param1;
        this.driveFiles = driveFiles;
        this.context = context;
        this.temp_ID = temp_ID;

        // INSTANTIATE LAYOUTS

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        shareBtn = new Button(context);

        emailBtn = new Button(context);


        // SET TEXT AND TAG

        shareBtn.setText(SHARE);
        shareBtn.setTag(SHARE);

        if(!option_type.equals("SharedFile")) {
            deleteBtn = new Button(context);
            deleteBtn.setText(DELETE);
            deleteBtn.setTag(DELETE);
            deleteBtn.setOnClickListener(this);
            linearLayout.addView(deleteBtn);
        }
        emailBtn.setText(EMAIL);
        emailBtn.setTag(EMAIL);

        // SET CLICK LISTENER

        shareBtn.setOnClickListener(this);
        emailBtn.setOnClickListener(this);

        // ADD TEXTVIEW TO LINEARLAYOUT

        linearLayout.addView(shareBtn);
        linearLayout.addView(emailBtn);

        // SET CONTENT VIEW
        this.setContentView(linearLayout);

    }

    private void closeActionHandlerDialog() {
        this.dismiss();
    }
    private void showActionHandlerDialog() {
        this.show();
    }

    @Override
    public void onClick(View view) {


        if (view.getTag().toString().equalsIgnoreCase("SHARE")) {
            // UPDATE DATA

            closeActionHandlerDialog();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Enter Email");
            // Set up the input
            final EditText input = new EditText(context);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    email = input.getText().toString();
                    if (!email.isEmpty()) {
                        new Thread() {
                            public void run() {

                                //Send Push notification
                                ParseQuery pushQuery = ParseInstallation.getQuery();
                                pushQuery.whereEqualTo("username",email);
                                // Send push notification to query
                                ParsePush push = new ParsePush();
                                push.setQuery(pushQuery); // Set our Installation query
                                push.setMessage("One or more file is shared to you! by");
                                push.sendInBackground();

                                //update page
                              // new RefreshAction().updateAction(context);

                            }
                        }.start();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Email can not be sent", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    showActionHandlerDialog();
                }
            });
            builder.show();








            ////---------------------------



        } else if (view.getTag().toString().equalsIgnoreCase("DELETE")) {

                final String[] success = {""};
                final String[] error = {""};
                new Thread() {
                    public void run() {
                        try

                        {
                            DriveFiles.getDriveFileInstance().drive_files.delete(temp_ID).execute();
                            Log.d("File deleted", "file " + DriveFiles.getDriveFileInstance().getFileObjectFromID().get(temp_ID).getTitle() + " deleted");
                            success[0] = "file " + temp_ID + " deleted";
                        } catch (
                                Exception e
                                )
                        {
                            Log.d("error in deletion", e.getMessage());
                            error[0] = e.getMessage();
                        }
                    }
                }.start();

                DriveFiles.getDriveFileInstance().removeFileFromList(temp_ID);

                Toast toast = Toast.makeText(context, "file " + DriveFiles.getDriveFileInstance().getFileObjectFromID().get(temp_ID).getTitle() + " deleted", Toast.LENGTH_LONG);
                toast.show();

                //update page
                new RefreshAction().updateAction(context);


        } else if (view.getTag().toString().equalsIgnoreCase("EMAIL")) {

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("vnd.android.cursor.dir/email");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");

            emailIntent.putExtra(Intent.EXTRA_TEXT, DriveFiles.getDriveFileInstance().getFileObjectFromID().get(temp_ID).getAlternateLink());

            context.startActivity(emailIntent);
            // DISMISS
            this.dismiss();
        }

        else
        {
            return;
        }
    }


}
