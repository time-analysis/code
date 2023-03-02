package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIAdapterInterface;
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

public class UIAdapter implements UIAdapterInterface {

    private DataPluginInterface dataPlugin;

    public UIAdapter(DataPluginInterface dataPlugin) {
        this.dataPlugin = dataPlugin;
    }
    

    public Lecture mapLectureRessourceToLecture(LectureResource lectureResource) {
        String name = lectureResource.getName();
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
        Entry entry = new Entry(start, EntryType.valueOf(entryRessource.getType()), lecture);
        entry.finishEntry(end, entryRessource.getDetails());
        return entry;
    }

    public List<LectureResource> getAllLecturesOfCurrentSemester() {
        //return List.of("BWL", "Evo-Alg"); //todo zugriff auf dataadapter oder auf (neuen) usecase??
        return dataPlugin.getLectures();
    }

    public String formatLocalDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter);
    }
}
