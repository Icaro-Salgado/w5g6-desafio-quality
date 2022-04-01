package br.com.mercadolivre.desafioquality.unity.database;

import br.com.mercadolivre.desafioquality.database.FileManager;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseReadException;
import br.com.mercadolivre.desafioquality.exceptions.DatabaseWriteException;
import br.com.mercadolivre.desafioquality.models.Neighborhood;
import br.com.mercadolivre.desafioquality.test_utils.DatabaseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles(profiles = "test")
@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileManagerTests {

    private ObjectMapper objectMapper;

    private final String filename = "neighborhood.json";

    @Autowired
    private FileManager<Neighborhood[]> fileManager;

    @Autowired
    private DatabaseUtils<Neighborhood[]> databaseUtils;

    @BeforeAll
    public void init(){
        databaseUtils.startDatabase(filename);
        this.objectMapper = Mockito.mock(ObjectMapper.class);
        fileManager.setObjectMapper(this.objectMapper);
    }

    @AfterAll
    public void afterAll(){
        databaseUtils.deleteDatabase();
    }


    @Test
    public void testIfCanReadValueWithoutErrors() throws DatabaseReadException, IOException {
        Neighborhood[] m = {new Neighborhood()};
        Mockito.when(objectMapper.readValue(Mockito.any(File.class), Mockito.eq(Neighborhood[].class))).thenReturn(m);
        Neighborhood[] result = fileManager.readFromFile(filename, Neighborhood[].class);

        assertEquals(m.length, result.length);
    }

    @Test
    public void testIfThrowDbExceptionWhenReceiveIOException() throws IOException {
        Mockito.when(objectMapper.readValue(Mockito.any(File.class), Mockito.eq(Neighborhood[].class))).thenThrow(new IOException("Error"));
        assertThrows(DatabaseReadException.class, () -> fileManager.readFromFile(filename, Neighborhood[].class));
    }

    @Test
    public void testIfReturnNullWhenReceiveInvalidFileWithInvalidDefault() throws DatabaseReadException {
            Neighborhood[] file = fileManager.readFromFile("inexistent.json", Neighborhood[].class);
            System.out.println(file);
            assertNull(file);
    }

    @Test
    public void testIfThrowDatabaseWriteException() throws IOException {
        Mockito.doThrow(IOException.class)
                .when(objectMapper).writeValue(Mockito.any(File.class), Mockito.any());

        assertThrows(DatabaseWriteException.class, () -> fileManager.writeIntoFile(filename, Neighborhood[].class));
    }

}
