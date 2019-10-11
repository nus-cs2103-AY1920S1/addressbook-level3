package seedu.address.model;

import seedu.address.model.person.Person;

/**
* This class holds the actual state of the application.*/
public final class InternalState {
    private int primaryKeyCounter;

    public InternalState() {
    }

    public InternalState(InternalState state) {
        this();
        updateInternalState(state);
        applyInternalState();
    }

    /**
    * Applies all the settings in this class to the application.
    */
    public void applyInternalState() {
        Person.setPrimaryKeyCounter(primaryKeyCounter);
    }

    /**
    * Copies values over from some other state. Useful for grabbing values
    * from disk.
    * @param state The InternalState to copy values from.
    */
    public void updateInternalState(InternalState state) {
        primaryKeyCounter = state.primaryKeyCounter;
    }

    /**
    * Collects the internal state from the various sources it is supposed to
    * track.
    */
    public void updateInternalState() {
        primaryKeyCounter = Person.getPrimaryKeyCounter();
        System.out.println("IS STATE, pk: " + primaryKeyCounter);
    }
}
