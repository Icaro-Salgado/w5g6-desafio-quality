package br.com.mercadolivre.desafioquality.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class DatabaseUtils {

    @Value("${path.database.file}")
    private String pathDatabase;

    public String getPathDatabase() {
        return pathDatabase;
    }

    public void resetDatabase(){
        File directory = new File(pathDatabase);
        for(File file: Objects.requireNonNull(directory.listFiles())){
            if (!file.isDirectory()){
                file.delete();
            }

        }

    }
}
