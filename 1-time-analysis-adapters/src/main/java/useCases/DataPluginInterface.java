package useCases;

import java.util.Map;

public interface DataPluginInterface { //TODO geh�rt eigl ins adapter-package
	public boolean writeData(Map<String, String> data);
}
