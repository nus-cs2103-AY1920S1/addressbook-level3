package seedu.address.logic.commands;

/**
 * Represents ListActivityCommand, ListContactCommand and ListDayCommand
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " activity/day/person: lists all related items.";
}
