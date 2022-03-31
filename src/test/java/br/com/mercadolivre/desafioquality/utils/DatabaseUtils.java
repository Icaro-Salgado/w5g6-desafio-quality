package br.com.mercadolivre.desafioquality.utils;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class DatabaseUtils<T> {

    @Value("${path.database.file}")
    private String pathDatabase;

    @Value("${path.database.default.file}")
    private String pathDefaultDatabases;

    private File dbFile = null;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void startDatabase(String filename) {
        if (dbFile == null) {
            dbFile = new File(pathDatabase.concat(filename));
        }
    }

    public void resetDatabase(String filename) throws IOException {
        startDatabase(filename);
        objectMapper.writeValue(dbFile, new ArrayList<>());
    }

    public void writeIntoFile(String filename, Object objectToBeSaved) throws IOException {
        startDatabase(filename);
        objectMapper.writeValue(new File(pathDatabase.concat(filename)), objectToBeSaved);
    }

    public T readFromFile(String filename, Class<T> typeParameterClass) throws IOException {
        startDatabase(filename);
        return objectMapper.readValue(dbFile, typeParameterClass);
    }

    public void deleteDatabase() {
        File directory = new File(pathDatabase);
        for(File file: Objects.requireNonNull(directory.listFiles())){
            if (!file.isDirectory()){
                file.delete();
            }
        }
    }

    public void loadDefaultFiles(String filename){
        File defaultDBFile = new File(pathDefaultDatabases.concat(filename).replace(".json", "").concat("_default.json"));

        File dbFile = new File(pathDatabase.concat(filename));

        try {
            Files.copy(defaultDBFile.toPath(), dbFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
