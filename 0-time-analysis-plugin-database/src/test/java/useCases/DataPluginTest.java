package useCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataPluginTest {

    private final String fileName = "Test.csv";
    private DataPlugin plugin;
    private BufferedReader reader;

    @BeforeEach
    void setup() {
        File file = new File(fileName);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.print("");
        writer.close();
        plugin = new DataPlugin(fileName);
        try {
            reader = new BufferedReader(new FileReader(fileName));
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