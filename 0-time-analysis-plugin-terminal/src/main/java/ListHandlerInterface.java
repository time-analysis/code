import ressourceModels.listeable;

import java.util.List;

public interface ListHandlerInterface {

    <T extends listeable> T getObjectFromNumberedList(List<T> list, String listIsEmptyMessage);
}
