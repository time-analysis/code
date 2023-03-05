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
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrysByLectureName(this.dataAdapter.mapLectureToLectureRessource(lecture).getName());
        List<Entry> entryList = new ArrayList<Entry>();
        entryRessourceList.forEach(entryRessource -> entryList.add(this.dataAdapter.mapEntryRessourceToEntry(entryRessource)));
        Duration selfStudyTime, lectureTime;
        selfStudyTime = entryList.stream().filter(entry -> entry.getType().equals(EntryType.SELFSTUDY)).map(Entry::calculateDuration).reduce(Duration::plus).get(); //todo wo gehören solche filtermethoden hin? wo zieht man die grenze zum plugin?
        lectureTime = entryList.stream().filter(entry -> entry.getType().equals(EntryType.LECTURE)).map(Entry::calculateDuration).reduce(Duration::plus).get();
        //todo wie brauche ich hierfür eigene RückgabeObjekte?
    }

    public Duration getPresenceTime() {
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrys();
        List<Entry> entryList = new ArrayList<>();
        entryRessourceList.forEach(entryRessource -> entryList.add(this.dataAdapter.mapEntryRessourceToEntry(entryRessource)));
        Duration presenceTime = entryList.stream().filter(entry -> entry.getType().equals(EntryType.LECTURE)).map(Entry::calculateDuration).reduce(Duration::plus).get();
        return presenceTime;
    }

    public Duration getStudyTime() {
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrys();
        List<Entry> entryList = new ArrayList<>();
        entryRessourceList.forEach(entryRessource -> entryList.add(this.dataAdapter.mapEntryRessourceToEntry(entryRessource)));
        Duration studyTime = entryList.stream().filter(entry -> entry.getType().equals(EntryType.SELFSTUDY)).map(Entry::calculateDuration).reduce(Duration::plus).get();
        return studyTime;
    }

    public Duration getTimePerSemester(Semester semester) {
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrys();
        List<Entry> entryList = new ArrayList<>();
        entryRessourceList.forEach(entryRessource -> entryList.add(this.dataAdapter.mapEntryRessourceToEntry(entryRessource)));
        Duration timePerSemester = entryList.stream().filter(entry -> entry.getLecture().getSemester().getName().equals(semester.getName())).map(Entry::calculateDuration).reduce(Duration::plus).get();
        return timePerSemester; //todo reicht java.duration oder braucht man ein eigenes objekt?
    }

    public void compareTimeTargetToActual() {

    }
}
