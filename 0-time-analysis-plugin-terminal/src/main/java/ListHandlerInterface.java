import ressourceModels.listeable;

import java.util.List;
import java.util.Optional;

public interface ListHandlerInterface {

    <T extends listeable> Optional<T> getObjectFromNumberedList(List<T> list, String listIsEmptyMessage);
}
