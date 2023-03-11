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

    public AdditionalSemester(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public void addSemester(Semester semester) {
            dataPlugin.persistSemester(dataAdapter.mapSemesterToSemesterRessource(semester));
    }
}
