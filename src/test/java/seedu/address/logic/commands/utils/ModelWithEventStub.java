package seedu.address.logic.commands.utils;

import static java.util.Objects.requireNonNull;

import seedu.address.model.events.Event;
import seedu.address.model.person.Person;

/**
 * A Model stub that contains a single person.
 */
public class ModelWithEventStub extends ModelStub {
    private final Event event;

    public ModelWithEventStub(Event event) {
        requireNonNull(event);
        this.event = event;
    }

    @Override
    public boolean hasEvent(Event pereventson) {
        requireNonNull(event);
        return this.event.isSameEvent(event);
    }

    @Override
    public boolean hasExactEvent(Event pereventson) {
        requireNonNull(event);
        return this.event.isSameEvent(event);
    }
}
