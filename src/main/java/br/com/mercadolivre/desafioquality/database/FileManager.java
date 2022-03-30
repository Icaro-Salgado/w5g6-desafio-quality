package br.com.mercadolivre.desafioquality.database;

import br.com.mercadolivre.desafioquality.exceptions.DatabaseManagementException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class FileManager<T> {

    private final ObjectMapper objectMapper;

    @Value("${path.database.file}")
    private String pathDatabase;

    @Value("${path.database.default.file}")
    private String pathDefaultDatabases;

    public FileManager() {
        this.objectMapper = new ObjectMapper();
    }

    public void writeIntoFile(String filename, Object objectToBeSaved) throws DatabaseWriteException {
        try {
            objectMapper.writeValue(new File(pathDatabase.concat(filename)), objectToBeSaved);
        } catch (IOException e) {
            throw new DatabaseWriteException(
                    "Não foi possível escrever a database ".concat(filename)
            );
        }
    }

    public T readFromFile(String filename, Class<T> typeParameterClass) throws DatabaseReadException {
        // TODO: Treat this exception

        try {
            File db = this.connect(filename);
            return objectMapper.readValue(db, typeParameterClass);
        } catch (IOException e) {
            throw new DatabaseReadException(
                    "Não foi possível ler a database ".concat(filename)
            );
        } catch (DatabaseManagementException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File connect(String Dbname) throws DatabaseManagementException {
        File db = new File(pathDatabase.concat(Dbname));

        try {
            return db.exists() ? db : this.loadDefaultDBFrom(Dbname);

        } catch (SecurityException e) {
            throw new DatabaseManagementException("Não foi possível ler da database devido a permissão do arquivo".concat(Dbname));

        }
    }

    private File loadDefaultDBFrom(String Dbname) throws DatabaseManagementException {
        File defaultDBFile = new File(pathDefaultDatabases.concat(Dbname).replace(".json", "").concat("_default.json"));

        if (!defaultDBFile.exists()) {
            throw new DatabaseManagementException("Você precisa criar um modelo padrão para a database");
        }

        File dbFile = new File(pathDatabase.concat(Dbname));

        try {
            Files.copy(defaultDBFile.toPath(), dbFile.toPath());
        } catch (IOException e) {
            throw new DatabaseManagementException("Não foi possível gerar uma nova base de dados");
        }

        return dbFile;
    }
}