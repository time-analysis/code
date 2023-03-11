package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Lecture;
import de.models.Semester;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalysisTest {
    DataPluginInterface dataPlugin = new DataPluginMock();
    DataAdapterInterface dataAdapter = new DataAdapterMock();

    @Test
    public void lectureTimeIsCalculatedCorrectly() {
        Analysis analysis = new Analysis(dataAdapter, dataPlugin);

        Duration presenceTime = analysis.getStudyTime();

        assertEquals(presenceTime, Duration.ofHours(2));
    }
}
