package usecases;

import de.models.Lecture;
import repositories.LectureRepositoryInterface;

public class AdditionalLecture {
    private LectureRepositoryInterface lectureRepository;

    public AdditionalLecture(LectureRepositoryInterface lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public void addLecture(Lecture lecture) {
        lectureRepository.createLecture(lecture);
    }
}
