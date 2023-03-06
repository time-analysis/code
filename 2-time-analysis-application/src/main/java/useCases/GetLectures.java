package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Lecture;

import java.util.List;
import java.util.stream.Collectors;

public class GetLectures {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public GetLectures(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public List<Lecture> getLectures() {
        return this.dataPlugin.getLectures().stream().map(lectureResource -> this.dataAdapter.mapLectureRessourceToLecture(lectureResource)).collect(Collectors.toList());
    }
}
