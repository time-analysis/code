package useCases;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class UIAdapter {

    //TODO navigation
    //todo interface fehlt?
    //UIPluginInterface uiPlugin;
    DataPluginInterface dataPlugin;

    public UIAdapter(UIPluginInterface uiPlugin, DataPluginInterface dataPlugin) {
        //this.uiPlugin = uiPlugin;
        this.dataPlugin = dataPlugin;
    }

    public void addEntryByTimeStamp(Map<String, String> data) {
        DataAdapterInterface dataAdapter = new DataAdapter(this.dataPlugin);
        AdditionalEntry add = new AdditionalEntry(dataAdapter);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //example for correct date: 2023-01-31 09:05
        LocalDateTime start = LocalDateTime.parse(data.get("Start"), formatter);
        LocalDateTime end = LocalDateTime.parse(data.get("End"), formatter);

        Lecture lecture = new Lecture(data.get("Lecture"), new Semester("5. Semester", LocalDate.now(), LocalDate.now()), 1, 1);
        Entry entry = new Entry(start, EntryType.valueOf(data.get("Type")), lecture);//todo sollen die objekte schon hier angelegt werden oder erst im usecase???
        entry.finishEntry(end, data.get("Details"));
        add.addEntry(entry, lecture);
    }

    public List<String> getAllLecturesOfCurrentSemester() {
        return List.of("BWL", "Evo-Alg"); //zugriff auf dataadapter oder auf (neuen) usecase??
    }

    public void addLecture(Map<String, String> data) {
        //adapter->daten an neuen usecase
        DataAdapterInterface dataAdapter = new DataAdapter(this.dataPlugin);
        AdditionalLecture additionalLecture = new AdditionalLecture(dataAdapter); //todo wo werden daten validiert?im plugin?im adapter?hier?
        String name = data.get("Name"); //todo wo prüfe ich, ob diese VL nicht schon exsitiert? erst beim rausschreiben? oder muss man intern eine liste aller semester im ram halten?
        int lectureTime = Integer.valueOf(data.get("LectureTime"));
        int selfStudyTime = Integer.valueOf(data.get("SelfStudyTime"));
        int indexOfSemester = Integer.valueOf(data.get("Semester")); //todo wie kommt man hier vom index des Semester an das SemesterObjekt
        Semester TEMPSEMESTER = new Semester("MOCKSEMESTER", LocalDate.now(), LocalDate.now()); //todo entfernen, "echtes" Semester hohlen
        Lecture lecture = new Lecture(name, TEMPSEMESTER, lectureTime, selfStudyTime);
        additionalLecture.addLecture(lecture);
    }


    public String formatLocalDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter);
    }
}
