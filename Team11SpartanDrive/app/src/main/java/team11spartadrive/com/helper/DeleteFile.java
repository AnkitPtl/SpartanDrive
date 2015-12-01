package team11spartadrive.com.helper;

import com.google.api.services.drive.Drive;

import java.io.IOException;

/**
 * Created by student on 11/30/15.
 */
public class DeleteFile {

    public boolean deleteFile(String id){
        if(new SearchFile().isFileExists(id)) {
            try{
            DriveFiles.getDriveFileInstance().getDrive_files().delete(id);
            return true;}
            catch (IOException e)
            {
                return false;
            }
        }
        else{
            return false;
        }
    }

}
