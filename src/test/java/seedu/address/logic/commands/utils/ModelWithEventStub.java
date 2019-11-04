package seedu.address.logic.commands.utils;

import static java.util.Objects.requireNonNull;

import seedu.address.model.events.Event;

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
    public boolean hasAppointment(Event pereventson) {
        requireNonNull(event);
        return this.event.isSameAs(event);
    }

    @Override
    public boolean hasExactAppointment(Event pereventson) {
        requireNonNull(event);
        return this.event.isSameAs(event);
    }
}
