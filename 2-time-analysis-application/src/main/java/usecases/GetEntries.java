package usecases;

import de.models.Entry;
import de.models.EntryStatus;
import filterCriteria.EntryFilterCriteria;
import filterCriteria.EntryFilterCriteriaBuilder;
import repositories.EntryRepositoryInterface;

import java.util.List;

public class GetEntries {
    private EntryRepositoryInterface entryRepository;

    public GetEntries(EntryRepositoryInterface entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<Entry> getUnfinishedEntries() {
        EntryFilterCriteriaBuilder entryFilterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria entryFilterCriteria = entryFilterCriteriaBuilder.withStatus(EntryStatus.RUNNING).build();
        return entryRepository.getEntrys(entryFilterCriteria);
    }
}
