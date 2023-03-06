package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Semester;

import java.util.List;
import java.util.stream.Collectors;

public class GetSemesters {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public GetSemesters(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public List<Semester> getSemesters() {
        return this.dataPlugin.getSemesters().stream().map(semesterRessource -> this.dataAdapter.mapSemesterRessourceToSemester(semesterRessource)).collect(Collectors.toList());
    }
}
