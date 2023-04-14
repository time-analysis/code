package plugins;

import de.models.Lecture;
import repositories.LectureRepositoryInterface;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LectureRepositoryMock implements LectureRepositoryInterface {

    private List<Lecture> persistence = new LinkedList<>();

    @Override
    public void createLecture(Lecture lecture) {
        persistence.add(lecture);
    }

    @Override
    public Optional<Lecture> getLectureByName(String name) {
        return persistence.stream().filter(lecture -> lecture.getName().equals(name)).findFirst();
    }

    @Override
    public List<Lecture> getLectures() {
        return persistence;
    }
}
