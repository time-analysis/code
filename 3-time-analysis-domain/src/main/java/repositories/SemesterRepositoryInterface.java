package repositories;

import de.models.Semester;

import java.util.List;
import java.util.Optional;

public interface SemesterRepositoryInterface {
    public void createSemester(Semester semester);

    public Optional<Semester> getSemesterByName(String name);

    public List<Semester> getSemesters();
}
