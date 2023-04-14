package plugins;

import de.models.Semester;
import repositories.SemesterRepositoryInterface;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SemesterRepositoryMock implements SemesterRepositoryInterface {
    private List<Semester> persistence = new LinkedList<>();

    @Override
    public void createSemester(Semester semester) {
        persistence.add(semester);
    }

    @Override
    public Optional<Semester> getSemesterByName(String name) {
        return persistence.stream().filter(semester -> semester.getName().equals(name)).findFirst();
    }

    @Override
    public List<Semester> getSemesters() {
        return persistence;
    }
}
