package useCases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataPluginTest {

    private DataPlugin plugin = new DataPlugin();
    private BufferedWriter writer;
    private BufferedReader reader;
    private File file = new File("Test.csv");

    @BeforeEach
    void setup() {

        try {
            writer = new BufferedWriter(new FileWriter(file, false));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        plugin.setWriter(writer);

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeData() {
        Map<String, String> map = new HashMap();
        LocalDateTime time = LocalDateTime.now();
        map.put("Start", time.toString());
        map.put("End", time.toString());
        map.put("Details", "weitere Informationen");
        map.put("Lecture", "Testvorlesung");

        plugin.writeData(map);
        String assumed = time + "," + time + ",null,weitere Informationen,Testvorlesung";

        String fromFile;
        try {
            fromFile = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(assumed, fromFile);
    }
}