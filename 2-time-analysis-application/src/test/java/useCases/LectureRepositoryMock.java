package useCases;

import de.models.Lecture;
import repositories.LectureRepositoryInterface;

import java.util.List;
import java.util.Optional;

public class LectureRepositoryMock implements LectureRepositoryInterface {
    @Override
    public void createLecture(Lecture lecture) {

    }

    @Override
    public Optional<Lecture> getLectureByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Lecture> getLectures() {
        return null;
    }
}
