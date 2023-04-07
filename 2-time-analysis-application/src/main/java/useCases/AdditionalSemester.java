package useCases;

import de.models.Semester;
import repositories.SemesterRepositoryInterface;

public class AdditionalSemester {

    private SemesterRepositoryInterface semesterRepository;

    public AdditionalSemester(SemesterRepositoryInterface semesterRepository) {
      this.semesterRepository = semesterRepository;
    }

    public void addSemester(Semester semester) {
        semesterRepository.createSemester(semester);
    }
}
