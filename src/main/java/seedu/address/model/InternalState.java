package seedu.address.model;

import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
* This class holds the actual state of the application.*/
public final class InternalState {
    private int personPriKeyCnt;
    private int activityPriKeyCnt;

    public InternalState() {}

    public InternalState(InternalState state) {
        this();
        updateInternalState(state);
        applyInternalState();
    }

    /**
    * Applies all the settings in this class to the application.
    */
    public void applyInternalState() {
        Person.setPrimaryKeyCounter(personPriKeyCnt);
        Activity.setPrimaryKeyCounter(activityPriKeyCnt);
    }

    /**
    * Copies values over from some other state. Useful for grabbing values
    * from disk.
    * @param state The InternalState to copy values from.
    */
    public void updateInternalState(InternalState state) {
        personPriKeyCnt = state.personPriKeyCnt;
        activityPriKeyCnt = state.activityPriKeyCnt;
    }

    /**
    * Collects the internal state from the various sources it is supposed to
    * track.
    */
    public void updateInternalState() {
        personPriKeyCnt = Person.getPrimaryKeyCounter();
        activityPriKeyCnt = Activity.getPrimaryKeyCounter();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof InternalState)) { //this handles null as well.
            return false;
        }

        InternalState o = (InternalState) other;
        return personPriKeyCnt == o.personPriKeyCnt
            && activityPriKeyCnt == o.activityPriKeyCnt;
    }
}
