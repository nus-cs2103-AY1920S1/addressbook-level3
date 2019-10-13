package seedu.address.logic.commands;

/**
 * Represents AddActivityCommand, AddContactCommand and AddDayCommand.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": either a contact, activity or day can be added, depending on the word that comes after";

    public static final String MESSAGE_SUCCESS = "Added!";
}
