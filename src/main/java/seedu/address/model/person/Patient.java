package seedu.address.model.person;

import java.util.*;

import seedu.address.model.common.PatientReferenceId;
import seedu.address.model.events.Event;
import seedu.address.model.tag.Tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


public class Patient extends Person {
    private Optional<Event> event;
    private List<Event> eventHistory;

    public Patient(PatientReferenceId referenceId, Name name, Phone phone,
                   Email email, Address address, Set<Tag> tags) {
        super(referenceId, name, phone, email, address, tags);
        event = Optional.empty();
        eventHistory = new ArrayList<>();
    }

    /**
     * Returns patient's appointment. The patient may not have one.
     * @return an Optional {@link Event}
     */
    public Optional<Event> getEvent() {
        return event;
    }

    /**
     * Sets an appointment for the patient.
     * @param event
     */
    public void setEvent(Event event) {
        requireAllNonNull(event);
        eventHistory.add(event);
        this.event = Optional.ofNullable(event);
    }

    /**
     * @return A list of all of the patient's event.
     */
    public List<Event> getAllEvent() {
        return eventHistory;
    }
    /**
     * Checks if patient has an event.
     */
    public boolean hasEvent() {
        return event.isPresent();
    }

    @Override
    public int hashCode() {
        Optional<Event> event = getEvent();
        Name name = getName();
        Phone phone = getPhone();
        Email email = getEmail();
        Address address = getAddress();
        Set<Tag> tags = getTags();
        return Objects.hash(name, phone, email, address, tags, event);
    }

}
