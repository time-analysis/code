package useCases;

import java.time.LocalDateTime;

import de.models.Entry;

public class Timer {
	static private Entry entry;
	static private boolean isRunning = false;

	public static void toggleTimer() {
		if (isRunning) {
			entry.setEnd(LocalDateTime.now());
		} else {
			entry.setStart(LocalDateTime.now());
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