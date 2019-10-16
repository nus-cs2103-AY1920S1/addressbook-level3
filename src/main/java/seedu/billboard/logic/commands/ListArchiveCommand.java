package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;

/**
 * Displays the list of expenses in an archive.
 */
public class ListArchiveCommand extends Command {

    public static final String COMMAND_WORD = "list-arc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the archive identified by the archive name used in the displayed list of archives.\n"
            + "Parameters: ARCHIVE NAME\n"
            + "Example: " + COMMAND_WORD + " 2016 expenses";

    public final String messageSuccess;

    private final String archiveName;

    public ListArchiveCommand(String archiveName) {
        requireNonNull(archiveName);
        this.archiveName = archiveName;
        this.messageSuccess = "Listed all expenses from the [" + archiveName + "] archive";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (archiveName.equals("")) {
            throw new CommandException(Messages.MESSAGE_INVALID_ARCHIVE_NAME);
        }

        if (!model.hasArchive(archiveName)) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_ARCHIVE_ENTERED);
        }

        model.updateFilteredArchiveExpenses(archiveName, Model.PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(messageSuccess, false, false, archiveName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof ListArchiveCommand) {
            ListArchiveCommand command = (ListArchiveCommand) other;
            return archiveName.equals(command.archiveName);
        } else {
            return false;
        }
    }
}
