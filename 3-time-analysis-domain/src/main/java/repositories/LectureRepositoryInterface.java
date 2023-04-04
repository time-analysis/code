package repositories;

import de.models.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureRepositoryInterface {
    public void createLecture(Lecture lecture);

    public Optional<Lecture> getLectureByName(String name);

    public List<Lecture> getLectures();
}
