package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIPluginInterface;
import de.models.Lecture;

public class AdditionalLecture {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public AdditionalLecture(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public void addLecture(Lecture lecture) {
            dataPlugin.persistLecture(dataAdapter.mapLectureToLectureRessource(lecture));

    }
}
