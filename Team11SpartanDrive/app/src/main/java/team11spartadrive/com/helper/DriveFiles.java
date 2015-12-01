package team11spartadrive.com.helper;

import android.util.Log;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import team11spartandrive.com.team11spartandrive.HomePageActivity;

/**
 * Created by student on 11/30/15.
 */
public class DriveFiles {

    private Drive.Files drive_files = null;
    static DriveFiles driveFilesInstance = null;
    private List<String> file_name_list = new ArrayList<String>();
    private List<String> file_id_list = new ArrayList<String>();

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

    public void setDrive_files(Drive.Files drive_files){
        this.drive_files = drive_files;
        try {

            FileList result = drive_files.list().execute();
            List<File> files = result.getItems();

            for(File file : files){
                file_name_list.add(file.getTitle());
                file_id_list.add(file.getId());
            }
        }
        catch (Exception e){
            Log.e("Error", e.getMessage());
        }
    }

    public Drive.Files getDrive_files(){
        return drive_files;
    }

    public List<String> getFileNameList() {
        return file_name_list;
    }

    public List<String> getFileIdList(){
        return file_id_list;
    }

}
