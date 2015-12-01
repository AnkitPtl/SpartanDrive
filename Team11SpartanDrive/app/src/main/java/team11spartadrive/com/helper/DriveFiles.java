package team11spartadrive.com.helper;

import com.google.api.services.drive.Drive;

import team11spartandrive.com.team11spartandrive.HomePageActivity;

/**
 * Created by student on 11/30/15.
 */
public class DriveFiles {

    private Drive.Files drive_files = null;
    static DriveFiles driveFilesInstance = null;

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

    public void setDrive_files(){
        this.drive_files = HomePageActivity.drive_files;
    }

    public Drive.Files getDrive_files(){
        return drive_files;
    }
}
