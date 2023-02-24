package useCases;

import java.util.HashMap;
import java.util.Map;

import de.models.Entry;
import de.models.Lecture;

public class DataAdapter implements DataAdapterInterface {
    private DataPluginInterface output;
    private Map<String, String> map = new HashMap<String, String>();

    public DataAdapter(DataPluginInterface dataPlugin) {
        output = dataPlugin;
    }

    public void mapEntryDataToPersist(Entry entry) {
        map.put("Start", entry.getStart().toString());
        map.put("End", entry.getEnd().toString());
        map.put("Details", entry.getDetails());
        map.put("Type", entry.getType().toString());
        map.put("Lecture", entry.getLecture().getName());
        output.persistEntry(map);
    }

    @Override
    public void mapLectureDataToPersist(Lecture lecture) {
        Map<String, String> lectureMap = new HashMap<String, String>();
        lectureMap.put("name", lecture.getName());
        lectureMap.put("lectureTime", Integer.toString(lecture.getLectureTime()));
        lectureMap.put("selfStudyTime", Integer.toString(lecture.getSelfStudyTime()));
        lectureMap.put("semesterName", lecture.getSemester().getName());
        output.persistNewLecture(lectureMap);

    }

}
