package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIPluginInterface;
import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class UIAdapter {
    
    DataPluginInterface dataPlugin;

    public UIAdapter(UIPluginInterface uiPlugin, DataPluginInterface dataPlugin) {
        //this.uiPlugin = uiPlugin;
        this.dataPlugin = dataPlugin;
    }

    public void addEntryByTimeStamp(Map<String, String> data) {
        DataAdapterInterface dataAdapter = new DataAdapter(this.dataPlugin);
        AdditionalEntry add = new AdditionalEntry(dataAdapter);


        entry.finishEntry(end, data.get("Details"));
        add.addEntry(entry, lecture);
    }

    public Lecture mapLectureRessourceToLecture(LectureResource lectureResource) {
        String name = lectureResource.getName(); //todo wo prüfe ich, ob diese VL nicht schon exsitiert? erst beim rausschreiben? oder muss man intern eine liste aller semester im ram halten?
        int lectureTime = lectureResource.getLectureTime();
        int selfStudyTime = lectureResource.getSelfStudyTime();
        String semester = lectureResource.getSemester(); //todo wie kommt man hier vom index des Semester an das SemesterObjekt
        Semester TEMPSEMESTER = new Semester(semester, LocalDate.now(), LocalDate.now()); //todo entfernen, "echtes" Semester hohlen
        return new Lecture(name, TEMPSEMESTER, lectureTime, selfStudyTime);
    }

    public Entry mapEntryRessourceToEntry(EntryRessource entryRessource) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); //example for correct date: 2023-01-31 09:05
        LocalDateTime start = LocalDateTime.parse(entryRessource.getStart(), formatter);
        LocalDateTime end = LocalDateTime.parse(entryRessource.getEnd(), formatter);

        Lecture lecture = new Lecture(entryRessource.getLecture(), new Semester("MOCKSEMESTER", LocalDate.now(), LocalDate.now()), 1, 1);
        Entry entry = new Entry(start, EntryType.valueOf(data.get("Type")), lecture);//todo sollen die objekte schon hier angelegt werden oder erst im usecase???
        entry.finishEntry(end, details);
        return entry;
    }

    public List<LectureResource> getAllLecturesOfCurrentSemester() {
        //return List.of("BWL", "Evo-Alg"); //todo zugriff auf dataadapter oder auf (neuen) usecase??
        return dataPlugin.getLectures();
    }

    public void addLecture(Map<String, String> data) { //todo kommunikation über maps zwischen adapter und plugin sinvoll?
        //adapter->daten an neuen usecase
        DataAdapterInterface dataAdapter = new DataAdapter(this.dataPlugin);
        AdditionalLecture additionalLecture = new AdditionalLecture(dataAdapter); //todo wo werden daten validiert?im plugin?im adapter?hier?
        // additionalLecture.addLecture(lecture);
    }


    public String formatLocalDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter);
    }
}
