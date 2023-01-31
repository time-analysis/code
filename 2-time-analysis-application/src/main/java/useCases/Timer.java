package useCases;

import java.time.LocalDateTime;

import de.models.Entry;

public class Timer {

    //Singleton

    static private Entry entry;
    static private boolean isRunning = false;

    public static void toggleTimer() {
        if (isRunning) {
            Entry entryWithNewTime = new Entry(entry.getStart(), entry.getType(), entry.getDetails(), entry.getLecture());
            //TODO set end only for fixing the changed constructor
            entryWithNewTime.setEnd(LocalDateTime.now());
        } else {
            Entry entryWithNewTime = new Entry(LocalDateTime.now(), entry.getType(), entry.getDetails(), entry.getLecture());
            //TODO set end only for fixing the changed constructor
            entryWithNewTime.setEnd(entry.getEnd());
        }
        isRunning = !isRunning;
    }

    public static Entry getEntry() {
        return entry;
    }

    public static void setEntry(Entry entry) {
        Timer.entry = entry;
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public static void setRunning(boolean isRunning) {
        Timer.isRunning = isRunning;
    }

}