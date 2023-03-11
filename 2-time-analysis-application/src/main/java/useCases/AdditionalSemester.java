package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIPluginInterface;
import de.models.Semester;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.util.Objects;

public class AdditionalSemester {

    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;
    private UIPluginInterface uiPlugin;

    public AdditionalSemester(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin, UIPluginInterface uiPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
        this.uiPlugin = uiPlugin;
    }

    public void addSemester(Semester semester) {
            dataPlugin.persistSemester(dataAdapter.mapSemesterToSemesterRessource(semester));
    }
}
