package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Analysis {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public Analysis(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public void getTimeSpentForLecture(Lecture lecture) {
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrysByLecture(this.dataAdapter.mapLectureToLectureRessource(lecture));
        List<Entry> entryList = new ArrayList<Entry>();
        entryRessourceList.forEach(entryRessource -> entryList.add(this.dataAdapter.mapEntryRessourceToEntry(entryRessource)));
        Duration selfStudyTime, lectureTime;
        selfStudyTime = entryList.stream().filter(entry -> entry.getType().equals(EntryType.SELFSTUDY)).map(Entry::calculateDuration).reduce(Duration::plus).get(); //todo wo gehören solche filtermethoden hin? wo zieht man die grenze zum plugin?
        lectureTime = entryList.stream().filter(entry -> entry.getType().equals(EntryType.LECTURE)).map(Entry::calculateDuration).reduce(Duration::plus).get();
    }

    public void getPresenceTime() {
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrys();
        List<Entry> entryList = new ArrayList<>();
        entryRessourceList.forEach(entryRessource -> entryList.add(this.dataAdapter.mapEntryRessourceToEntry(entryRessource)));
        Duration presenceTime = entryList.stream().filter(entry -> entry.getType().equals(EntryType.LECTURE)).map(entry -> entry.calculateDuration()).reduce(Duration::plus).get();

    }

    public void getStudyTime() {
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrys();
        List<Entry> entryList = new ArrayList<>();
        entryRessourceList.forEach(entryRessource -> entryList.add(this.dataAdapter.mapEntryRessourceToEntry(entryRessource)));
        Duration presenceTime = entryList.stream().filter(entry -> entry.getType().equals(EntryType.SELFSTUDY)).map(entry -> entry.calculateDuration()).reduce(Duration::plus).get();
    }

    public void getTimePerSemester(Semester semester) {
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrys();
        List<Entry> entryList = new ArrayList<>();
        entryRessourceList.forEach(entryRessource -> entryList.add(this.dataAdapter.mapEntryRessourceToEntry(entryRessource)));
        Duration presenceTime = entryList.stream().filter(entry -> entry.getLecture().getSemester().getName().equals(semester.getName())).map(entry -> entry.calculateDuration()).reduce(Duration::plus).get();
    }

    public void compareTimeTargetToActual() {

    }
}
