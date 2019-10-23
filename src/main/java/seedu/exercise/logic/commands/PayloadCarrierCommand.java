package seedu.exercise.logic.commands;

import seedu.exercise.logic.commands.events.EventPayload;

/**
 * Interface for commands which need to store an EventPayload object to store key information
 * that can be accessed by others.
 */
public interface PayloadCarrierCommand {

    /**
     * Returns the payload that stores the regime that has been deleted or edited in this command.
     *
     * @return {@code EventPayload} containing relevant objects can be accessed using specific keys
     */
    EventPayload getPayload();
}
