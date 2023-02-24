package useCases;

import de.models.Lecture;

public class AdditionalLecture {
    private DataAdapterInterface dataAdapter;

    public AdditionalLecture(DataAdapterInterface dataAdapter) {
        this.dataAdapter = dataAdapter;
    }

    public void addLecture(Lecture lecture) {
        dataAdapter.mapLectureDataToPersist(lecture);
    }
}
