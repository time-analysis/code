import Interfaces.UIAdapterInterface;
import Repositories.EntryRepository;
import Repositories.EntryRepositoryCache;
import Repositories.LectureRepository;
import Repositories.SemesterRepository;
import repositories.EntryRepositoryInterface;
import repositories.LectureRepositoryInterface;
import repositories.SemesterRepositoryInterface;
import useCases.DataAdapter;
import useCases.UIAdapter;

public class starter {
    public static void main(String[] args) {

        //DataPluginInterface dataPlugin = new DataPlugin("Entries.csv", "Lectures.csv", "Semesters.csv");
        DataAdapter dataAdapter = new DataAdapter();
        SemesterRepositoryInterface semesterRepository = new SemesterRepository(dataAdapter, "Semesters.csv");
        LectureRepositoryInterface lectureRepository = new LectureRepository(dataAdapter, "Lectures.csv");

        EntryRepositoryInterface entryRepository = new EntryRepository(dataAdapter,"Entries.csv");
        EntryRepositoryInterface entryRepositoryCache = new EntryRepositoryCache(entryRepository);

        dataAdapter.setRepositories(semesterRepository,lectureRepository);
        UIAdapterInterface uiAdapter = new UIAdapter(semesterRepository, lectureRepository);
        UITerminalPlugin terminal = new UITerminalPlugin(semesterRepository,lectureRepository,entryRepositoryCache, uiAdapter);
        terminal.start();
    }
}