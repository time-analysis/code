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

	public void mapData(Entry entry) {
		map.put("Start", entry.getStart().toString());
		map.put("Ende", entry.getEnd().toString());

		output.writeData(map);
	}

}
