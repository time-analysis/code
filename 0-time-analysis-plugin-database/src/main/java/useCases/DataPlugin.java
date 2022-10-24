package useCases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class DataPlugin implements DataPluginInterface {

	public void writeData(Map<String, String> data) {
		File file = new File("Entries.csv");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
			writer.write(data.get("Start") + " | " + data.get("Ende"));
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
