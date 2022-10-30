package useCases;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class UIAdapter {
    UIPluginInterface uiPlugin;
    DataPluginInterface dataPlugin;

    public UIAdapter(UIPluginInterface uiPlugin, DataPluginInterface dataPlugin) {
        this.uiPlugin = uiPlugin;
        this.dataPlugin = dataPlugin;
    }

    public void addEntryByTimeStamp(Map<String, String> data) {
        DataAdapterInterface dataAdapter = new DataAdapter(this.dataPlugin);
        AdditionalEntry add = new AdditionalEntry(dataAdapter);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse(data.get("Start"), formatter);
        LocalDateTime end = LocalDateTime.parse(data.get("End"), formatter);

        Entry entry = new Entry(start, end, EntryType.valueOf(data.get("Type")), data.get("Details"));
        Lecture lecture = new Lecture(data.get("Lecture"), new Semester(), 1, 1);
        add.addEntry(entry, lecture);

    }
}
