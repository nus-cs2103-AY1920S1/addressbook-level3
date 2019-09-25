package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.events.EventSource;

public interface ReadOnlyEventsBook {

    ObservableList<EventSource> getEventList();

}
