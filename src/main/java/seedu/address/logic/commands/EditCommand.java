package seedu.address.logic.commands;

/**
 * Represents EditActivityCommand and EditContactCommand.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of either a contact or activity,"
            + "depending on the following second command word and arguments.";

}
