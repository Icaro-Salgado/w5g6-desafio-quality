package br.com.mercadolivre.desafioquality.utils;

import java.io.File;
import java.util.Objects;

public class DatabaseUtils {

    public static void resetDatabase(){
        String pathDatabase = "src/test/java/br/com/mercadolivre/desafioquality/database/";

        File directory = new File(pathDatabase);
        for(File file: Objects.requireNonNull(directory.listFiles())){
            if (!file.isDirectory()){
                file.delete();
            }

        }

    }
}
