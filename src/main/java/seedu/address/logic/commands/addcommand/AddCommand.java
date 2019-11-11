package seedu.address.logic.commands.addcommand;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.TrackableState;
import seedu.address.model.entity.Entity;

/**
 * Adds an {@link Entity} to Alfred.
 */
public abstract class AddCommand extends Command implements TrackableState {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new entity to Alfred.\n"
            + "\tAdd command must specify the entity being added "
            + "and its corresponding fields. \n"
            + "Format: add [entity] [entity fields] \n"
            + "Example: add participant n/NAME e/EMAIL p/PHONE_NUMBER";

}
