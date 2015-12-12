package team11spartadrive.com.helper;

import android.util.Log;

import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.User;

import org.w3c.dom.UserDataHandler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team11spartandrive.com.team11spartandrive.HomePageActivity;
import team11spartandrive.com.team11spartandrive.MainActivity1;

/**
 * Created by student on 11/30/15.
 */
public class DriveFiles {

    private String description = "";
    public static Drive.Files drive_files = null;
    static DriveFiles driveFilesInstance = null;
    private List<String> file_name_list = new ArrayList<String>();
    private List<String> shared_file_name_list = new ArrayList<String>();
    private List<String> file_id_list = new ArrayList<String>();
    private List<String> file_desc_list=  new ArrayList<String>();
    private List<String> shared_file_desc_list=  new ArrayList<String>();
    Map<String,String> file_ext_list = new HashMap<String,String>();
    private Map<String,String> file_name_id = new HashMap<String,String>();
    private List<File> files;
    List <String> owners;
    private String owner_name = HomePageActivity.mCredential.getSelectedAccount().name;
    /*
    <ID, file_metadata>
     */
    private Map<String, List<String>> file_metadata_id = new HashMap<String, List<String>>();


    private DriveFiles(){

    }

    public static DriveFiles getDriveFileInstance(){

        if(driveFilesInstance == null){
            driveFilesInstance = new DriveFiles();
            return driveFilesInstance;
        }

        else{
            return driveFilesInstance;
        }

    }


    public void refresh_mService() {

       setDrive_files(MainActivity1.mService.files());

    }

    public void setDrive_files(final Drive.Files drive_files) {

        new Thread() {

            public void run() {
                DriveFiles.drive_files = drive_files;

                try {
                        FileList result = drive_files.list()
                                //.setMaxResults(15)
                                .execute();

                        files = result.getItems();
                        for (File file : files) {
                            if (file.getDescription() == null) {
                                description = "No Description";
                            } else {
                                description = file.getDescription();
                            }

                        String mdate = file.getModifiedDate().toString();
                        String createdDate = file.getCreatedDate().toString();
                        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");

                        mdate = myFormat.format(fromUser.parse(mdate.split("T")[0]));
                        createdDate = myFormat.format(fromUser.parse(createdDate.split("T")[0]));


                        String fileDesc = "\n Description: "+description+"\n Modified date:"+ mdate +"\n Created date:"+ createdDate;
                        file_id_list.add(file.getId());
                        file_ext_list.put(file.getTitle(), file.getFileExtension());
                        file_name_id.put(file.getTitle(), file.getId());


                        for(User temp: file.getOwners()){
                                if(! temp.getEmailAddress().equals(owner_name)){
                                shared_file_name_list.add(file.getTitle());
                                shared_file_desc_list.add(fileDesc);
                            }
                            else{
                                file_name_list.add(file.getTitle());
                                file_desc_list.add(fileDesc);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        }.run();
    }

    public Drive.Files getDrive_files(){
        return drive_files;
    }

    public List<String> getFileNameList() {
        return file_name_list;
    }

    public List<String> getSharedFileNameList() {
        return shared_file_name_list;
    }

    public List<String> getFileIdList(){
        return file_id_list;
    }

    public Map<String,String> getFile_ext_list() {
        return file_ext_list;
    }

    public List<String> getFile_desc_list() {
        return file_desc_list;
    }

    public List<String> getShared_File_desc_list() {
        return shared_file_desc_list;
    }

    public String getIdFromName(String name){
        return file_name_id.get(name);
    }

    public Map<String, List<String>> get_file_metadata_from_id(){
        return file_metadata_id;
    }

    public List<File> getfiles(){
        return files;
    }

}
