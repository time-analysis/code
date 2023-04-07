package plugins;

import de.models.Semester;
import repositories.SemesterRepositoryInterface;

import java.util.List;
import java.util.Optional;

public class SemesterRepositoryMock implements SemesterRepositoryInterface {
    @Override
    public void createSemester(Semester semester) {

    }

    @Override
    public Optional<Semester> getSemesterByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Semester> getSemesters() {
        return null;
    }
}
