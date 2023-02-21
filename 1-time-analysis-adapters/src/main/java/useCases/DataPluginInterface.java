package useCases;

import java.util.Map;

public interface DataPluginInterface {
    public boolean persistEntry(Map<String, String> data);

    void persistNewLecture(Map<String, String> lectureMap);
}
