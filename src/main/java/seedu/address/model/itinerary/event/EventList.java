package seedu.address.model.itinerary.event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.itinerary.UniqueEntityList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class EventList extends UniqueEntityList<Event> {
}
