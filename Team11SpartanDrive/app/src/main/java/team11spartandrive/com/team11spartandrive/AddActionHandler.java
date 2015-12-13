package team11spartandrive.com.team11spartandrive;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
//import java.io.File;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
import com.google.common.io.Files;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;

import team11spartadrive.com.helper.DriveFiles;
import team11spartadrive.com.helper.RefreshAction;

/**
 * Created by Ankit on 12/2/2015.
 */
public class AddActionHandler extends Dialog implements View.OnClickListener {
    final static String newFolder = "Folder";
    final static String newFile = "Upload";
    Context context;
    private final Activity activity;
    LinearLayout linearLayout;
    Button newFolderBtn;
    Button newFileBtn;
    private String newFolderName = "";

    public AddActionHandler(Context context, Activity activity) {
        super(context);
        this.setTitle("New");
        this.context = context;
        this.activity = activity;
        // Layouts
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        newFolderBtn = new Button(context);
        newFileBtn = new Button(context);
        // Assign Tags
        newFolderBtn.setText(newFolder);
        newFolderBtn.setTag(newFolder);
        newFileBtn.setText(newFile);
        newFileBtn.setTag(newFile);
        // Attach Listener
        newFolderBtn.setOnClickListener(this);
        newFileBtn.setOnClickListener(this);
        // Add Buttons to dialog
        linearLayout.addView(newFolderBtn);
        linearLayout.addView(newFileBtn);
        // Set View
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
        String selectedAction = view.getTag().toString();
        switch (selectedAction) {
            case "Folder":
                closeActionHandlerDialog();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("New folder");
                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newFolderName = input.getText().toString();
                        if (!newFolderName.isEmpty()) {
                            new Thread() {
                                public void run() {
                                    File body = new File();
                                    body.setTitle(newFolderName);
                                    body.setMimeType("application/vnd.google-apps.folder");
                                    try {
                                        File file = DriveFiles.getDriveFileInstance().getDrive_files().insert(body).execute();
                                    } catch (Exception e) {
                                        System.out.println("Error while creating new folder");
                                    }

                                    //update page
                                    new RefreshAction().updateAction(context);

                                }
                            }.start();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "folder name can not be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        newFolderName = "";
                        showActionHandlerDialog();
                    }
                });
                builder.show();


                break;
            case "Upload":
                new FileChooser(this.activity).setFileListener(new FileChooser.FileSelectedListener() {
                    public void fileSelected(final java.io.File file) {
                        uploadFileToDrive(file);
                    }}).showDialog();
                break;

        }
    }

    private void uploadFileToDrive(java.io.File file) {
        try {
        // File's metadata.
        final File body = new File();
        body.setTitle(file.getName());
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        body.setMimeType(URLConnection.guessContentTypeFromStream(is));
        //TODO: Upload to specific folder!
        // File's content.
        final FileContent mediaContent = new FileContent(URLConnection.guessContentTypeFromStream(is), file);
            new Thread() {
                public void run() {
                    try {
                        File uploadedFile = DriveFiles.getDriveFileInstance().getDrive_files().insert(body, mediaContent).execute();
                        closeActionHandlerDialog();
                        new RefreshAction().updateAction(context);
                    }
                    catch(Exception ie){
                        closeActionHandlerDialog();
                    }
                }
            }.start();
        } catch (Exception e) {
            closeActionHandlerDialog();
        }
    }
}
