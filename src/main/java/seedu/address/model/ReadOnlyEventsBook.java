package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.events.Event;

public interface ReadOnlyEventsBook {

    ObservableList<Event> getEventList();

}
