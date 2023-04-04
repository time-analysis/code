import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIAdapterInterface;
import Repositories.LectureRepository;
import Repositories.SemesterRepository;
import repositories.LectureRepositoryInterface;
import repositories.SemesterRepositoryInterface;
import useCases.DataAdapter;
import useCases.DataPlugin;
import useCases.UIAdapter;

public class starter {
    public static void main(String[] args) {

        DataPluginInterface dataPlugin = new DataPlugin("Entries.csv", "Lectures.csv", "Semesters.csv");
        SemesterRepositoryInterface semesterRepository = new SemesterRepository(dataAdapter, "Semesters.csv");
        LectureRepositoryInterface lectureRepository = new LectureRepository(dataAdapter, "Lectures.csv");
        UIAdapterInterface uiAdapter = new UIAdapter(semesterRepository, lectureRepository);
        DataAdapterInterface dataAdapter = new DataAdapter(semesterRepository, lectureRepository);
        UITerminalPlugin terminal = new UITerminalPlugin(dataPlugin, dataAdapter, uiAdapter);
        terminal.start();
    }
}