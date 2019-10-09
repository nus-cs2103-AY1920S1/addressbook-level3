package seedu.address.logic.commands.utils;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.events.Event;

/**
 * A Model stub that always accept the person being added.
 */
public class ModelAcceptingEventAddedStub extends ModelStub {
    public final ArrayList<Event> eventsAdded = new ArrayList<>();

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return eventsAdded.stream().anyMatch(event::isSameEvent);
    }

    @Override
    public void addEvent(Event event) {
        requireNonNull(event);
        eventsAdded.add(event);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return new AddressBook();
    }
}
