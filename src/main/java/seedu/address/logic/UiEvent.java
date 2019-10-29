package seedu.address.logic;

import seedu.address.ui.State;

import java.util.Optional;

/**
 * An UiEvent is an event that has happened in the Ui display.
 */
public class UiEvent {
    private State state;
    private Optional<Integer> projectIndex;

    public UiEvent(State state, Optional<Integer> projectIndex) {
        this.state = state;
        this.projectIndex = projectIndex;
    }

    public State getState() {
        return state;
    }

    public Optional<Integer> getProjectIndex() {
        return projectIndex;
    }
}
