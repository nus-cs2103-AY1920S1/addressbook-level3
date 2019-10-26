package seedu.exercise.commons.core;

import seedu.exercise.MainApp;

/**
 * Represents the current state of the program.
 * Only subclasses of {@code Command} can alter the state of the program.
 * The main state is held in {@link MainApp}.
 */
public enum State {
    IN_CONFLICT,
    NORMAL
}
