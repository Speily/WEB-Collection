package com.kaishengit.util;

import java.io.File;

public class DelFileUtils {
    public static void del(String path){
        File file = new File(path);
        if(file.isFile()){
            file.delete();
        }else{

        }
    }
}
