package useCases;

import java.time.LocalDateTime;

import de.models.Entry;

public class Timer {

    //Singleton

    static private Entry entry;
    static private boolean isRunning = false;

    public static void toggleTimer() {
        Entry entryWithNewTime;
        if (isRunning) {
            entryWithNewTime = new Entry(entry.getStart(), entry.getType(), entry.getLecture());
            entryWithNewTime.finishEntry(LocalDateTime.now(), entry.getDetails());
        } else {
            entryWithNewTime = new Entry(LocalDateTime.now(), entry.getType(), entry.getLecture());
        }
        entry = entryWithNewTime;
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