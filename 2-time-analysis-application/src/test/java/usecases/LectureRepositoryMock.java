package usecases;

import de.models.Lecture;
import repositories.LectureRepositoryInterface;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LectureRepositoryMock implements LectureRepositoryInterface {


    private List<List<Lecture>> inMemoryPersistence = new LinkedList<>();
    private List<Lecture> createdLectures = new LinkedList<>();
    private Iterator<List<Lecture>> iterator;
    private boolean wasCreateLectureMethodCalled = false;

    public void addListOfLectureToReturnList(List<Lecture> lectures) {
        inMemoryPersistence.add(lectures);
        this.iterator = inMemoryPersistence.iterator();
    }

    @Override
    public void createLecture(Lecture lecture) {
        this.wasCreateLectureMethodCalled = true;
        createdLectures.add(lecture);
    }

    public boolean wasCreateLectureMethodCalled() {
        return wasCreateLectureMethodCalled;
    }

    public List<Lecture> getCreatedLectures() {
        return createdLectures;
    }

    @Override
    public Optional<Lecture> getLectureByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Lecture> getLectures() {
        return iterator.next();
    }
}
