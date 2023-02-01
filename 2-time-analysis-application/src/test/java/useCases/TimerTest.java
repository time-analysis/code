package useCases;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import de.models.Lecture;
import de.models.Semester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.models.Entry;
import de.models.EntryType;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimerTest {

    @BeforeEach
    void setup() {
        Semester semester = new Semester("5. Semester", LocalDate.now(), LocalDate.now());
        Lecture lecture = new Lecture("ASE", semester, 10, 20);
        Timer.setEntry(new Entry(LocalDateTime.now(), EntryType.PRESENCE, lecture));
        Timer timer = new Timer();
    }

    @Test
    void timerStart() {
        Timer.setRunning(false);
        Timer.toggleTimer();
        LocalDateTime currentTime = LocalDateTime.now();
        assertTrue(Timer.isRunning());
        assertTrue(Duration.between(Timer.getEntry().getStart(), currentTime).toMillis() < 100); //100 ms can pass between starting timer and measuring time...
    }

    @Test
    void timerEnd() {
        Timer.setRunning(true);
        Timer.toggleTimer();
        LocalDateTime currentTime = LocalDateTime.now();
        assertFalse(Timer.isRunning());
        assertTrue(Duration.between(Timer.getEntry().getEnd(), currentTime).toMillis() < 100); //100 ms can pass between starting timer and measuring time...
    }

}
