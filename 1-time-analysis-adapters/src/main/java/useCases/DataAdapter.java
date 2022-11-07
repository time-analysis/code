package useCases;

import java.util.HashMap;
import java.util.Map;

import de.models.Entry;

public class DataAdapter implements DataAdapterInterface {
    private DataPluginInterface output;
    private Map<String, String> map = new HashMap<String, String>();

    public DataAdapter(DataPluginInterface dataPlugin) {
        output = dataPlugin;
    }

    public void mapEntryData(Entry entry) {
        map.put("Start", entry.getStart().toString());
        map.put("End", entry.getEnd().toString());
        map.put("Details", entry.getDetails());
        map.put("Type", entry.getType().toString());
        map.put("Lecture", entry.getLecture().getName());
        output.writeData(map);
    }

}
