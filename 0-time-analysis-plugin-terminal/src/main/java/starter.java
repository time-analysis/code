import Interfaces.UIAdapterInterface;
import Interfaces.UIPluginInterface;
import adapters.DataAdapter;
import adapters.UIAdapter;
import repositories.*;

public class starter {
    public static void main(String[] args) {

        DataAdapter dataAdapter = new DataAdapter();
        SemesterRepositoryInterface semesterRepository = new SemesterRepository(dataAdapter, "Semesters.csv");
        LectureRepositoryInterface lectureRepository = new LectureRepository(dataAdapter, "Lectures.csv");

        EntryRepositoryInterface entryRepository = new EntryRepository(dataAdapter, "Entries.csv");
        EntryRepositoryInterface entryRepositoryCache = new EntryRepositoryCache(entryRepository);

        dataAdapter.setRepositories(semesterRepository, lectureRepository);
        UIAdapterInterface uiAdapter = new UIAdapter(semesterRepository, lectureRepository);
        UIPluginInterface terminal = new UITerminalPlugin(semesterRepository, lectureRepository, entryRepository, uiAdapter);
        terminal.start();
    }
}