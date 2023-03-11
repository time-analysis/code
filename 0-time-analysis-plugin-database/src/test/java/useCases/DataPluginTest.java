package useCases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ressourceModels.EntryRessource;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataPluginTest {

    private final String entryFileName = "EntryTest.csv";
    private final String lectureFileName = "LectureTest.csv";
    private final String semesterFileName = "SemesterTest.csv";
    private DataPlugin plugin;
    private BufferedReader entryReader;

    @BeforeEach
    void setup() {
        List<File> files = List.of(new File(entryFileName), new File(lectureFileName), new File(semesterFileName));
        PrintWriter writer = null;
        for (File file : files) {
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            writer.print("");
            writer.close();
        }

        plugin = new DataPlugin(entryFileName, lectureFileName, semesterFileName);
        try {
            entryReader = new BufferedReader(new FileReader(entryFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    //todo wie testet man dieses plugin am besten? idee: WriterImplementierung im constructor übergeben (für test: Stringwrite, für normalen Betriebe: FileWriter)
    @Test
    void writeData() {
        LocalDateTime time = LocalDateTime.now();

        plugin.persistEntry(new EntryRessource(time.toString(), time.toString(), "LECTURE", "weitere Informationen", "Testvorlesung"));
        String assumed = "Testvorlesung," + time + "," + time + ",LECTURE,weitere Informationen";
        String fromFile;
        try {
            fromFile = entryReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(assumed, fromFile);
    }
}