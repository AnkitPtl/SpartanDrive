package team11spartadrive.com.helper;

import java.io.IOException;

/**
 * Created by student on 11/30/15.
 */
public class SearchFile {

    public boolean isFileExists(String id){
        try {
            DriveFiles.getDriveFileInstance().getDrive_files().get(id);
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }
}
