package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIPluginInterface;
import de.models.Lecture;

import java.util.Objects;

public class AdditionalLecture {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;
    private UIPluginInterface uiPlugin;

    public AdditionalLecture(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin, UIPluginInterface uiPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public void addLecture(Lecture lecture) {

        if (dataPlugin.getLectureByName(lecture.getName()).isPresent()) {
            uiPlugin.displayError("A Lecture with the name " + lecture.getName() + " already exitsts");
        } else {
            dataPlugin.persistLecture(dataAdapter.mapLectureDataToPersist(lecture));
            uiPlugin.displayMessage("Lecture created.");
        }

    }
}
