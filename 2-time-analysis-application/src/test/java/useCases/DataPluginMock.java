package useCases;

import FilterCriteria.EntryFilterCriteria;
import Interfaces.DataPluginInterface;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.util.List;
import java.util.Optional;

public class DataPluginMock implements DataPluginInterface {
    @Override
    public void persistEntry(EntryRessource entryRessource) {

    }

    @Override
    public void persistLecture(LectureResource lectureResource) {

    }

    @Override
    public void persistSemester(SemesterRessource semester) {

    }

    @Override
    public Optional<SemesterRessource> getSemesterByName(String semesterName) {
        return Optional.empty();
    }

    @Override
    public Optional<LectureResource> getLectureByName(String lectureName) {
        return Optional.empty();
    }

    @Override
    public List<LectureResource> getLectures() {
        return null;
    }

    @Override
    public List<SemesterRessource> getSemesters() {
        return null;
    }

    @Override
    public List<EntryRessource> getEntrys() {
        return null;
    }

    @Override
    public List<EntryRessource> getEntrys(EntryFilterCriteria criteria) {
        return null;
    }

    @Override
    public void updateEntry(EntryRessource entryRessource) {

    }
}
