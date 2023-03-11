package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Lecture;

import java.util.List;

public class GetLectures {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public GetLectures(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public List<Lecture> getLectures() {
        return this.dataAdapter.mapLectureRessourceListToLectureList(this.dataPlugin.getLectures());
    }
}
