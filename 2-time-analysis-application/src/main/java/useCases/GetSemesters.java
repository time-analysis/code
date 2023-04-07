package useCases;

import de.models.Semester;
import repositories.SemesterRepositoryInterface;

import java.util.List;

public class GetSemesters {
  private SemesterRepositoryInterface semesterRepository;
    public GetSemesters(SemesterRepositoryInterface semesterRepository) {
     this.semesterRepository = semesterRepository;
    }

    public List<Semester> getSemesters() {
        return this.semesterRepository.getSemesters();
    }
}
