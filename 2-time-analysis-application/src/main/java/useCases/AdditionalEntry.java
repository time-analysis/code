package useCases;

import de.models.Entry;

public class AdditionalEntry {
	DataAdapterInterface mapData;

	public AdditionalEntry(DataAdapterInterface mapData) {
		this.mapData = mapData;
	}

	public void addEntry(Entry entry) {
		mapData.mapData(entry);
	}
}
