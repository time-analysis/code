package useCases;

import java.util.HashMap;
import java.util.Map;

import de.models.Entry;
import de.models.Lecture;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;

public class DataAdapter implements DataAdapterInterface {
    private DataPluginInterface output;
    private Map<String, String> map = new HashMap<String, String>();

    public DataAdapter(DataPluginInterface dataPlugin) {
        output = dataPlugin;
    }

    public EntryRessource mapEntryDataToPersist(Entry entry) {
        EntryRessource entryRessource = new EntryRessource(entry.getStart(), entry.getEnd(), entry.getType(), entry.getDetails(), entry.getLecture());
        return entryRessource;

    }

    @Override
    public LectureResource mapLectureDataToPersist(Lecture lecture) {
        LectureResource lectureResource = new LectureResource(lecture.getName(), lecture.getSemester(), lecture.getLectureTime(), lecture.getSelfStudyTime());
        return lectureResource;
        
    }

}
