package db;

import java.io.File;
import java.io.IOException;

public class FileCreate {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean judgeFileExist(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            System.out.println("file exists");
            file.delete();
//            return true;
        } else {
            System.out.println("file not exists, create it ...");
            createFile(filepath);
            return  false;
        }
        return true;
    }

    public File createFile(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File exist");
        }
        return file;
    }

}
