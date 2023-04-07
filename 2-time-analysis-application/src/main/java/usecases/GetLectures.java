package usecases;

import de.models.Lecture;
import repositories.LectureRepositoryInterface;

import java.util.List;

public class GetLectures {
    private LectureRepositoryInterface lectureRepository;

    public GetLectures(LectureRepositoryInterface lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public List<Lecture> getLectures() {
        return lectureRepository.getLectures();
    }
}
