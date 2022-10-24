package useCases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class DataPlugin implements DataPluginInterface {

	public void writeData(Map<String, String> data) {
		File file = new File("Entries.csv");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
			String csvEntry = String.format("%s,%s,%s,%s,%s", data.get("Start"),data.get("End"),data.get("Type"),data.get("Details"),data.get("Lecture"));
			writer.write(csvEntry);
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
