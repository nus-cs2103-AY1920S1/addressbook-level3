package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_ARCHIVE;

import java.util.List;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.Expense;

/**
 * Deletes an archive expense identified using it's displayed index from
 * the given archive name and archive list of expenses.
 */
public class DeleteArchiveCommand extends ArchiveCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = ArchiveCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the archive expense identified by the index number used in "
            + "the archive displayed expense list.\n"
            + "Parameters: INDEX (must be a positive integer), ARCHIVE NAME (must be an existing archive)\n"
            + "Example: " + ArchiveCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1" + PREFIX_ARCHIVE + "hobbies";

    public static final String MESSAGE_DELETE_EXPENSE_SUCCESS = "Deleted [%1$s] expense in [%2$s] archive";

    public static final String MESSAGE_EMPTY_ARCHIVE_AFTER_DELETE_EXPENSE =
            "[%1$s] archive is now empty and will be deleted";

    private final String archiveName;
    private final Index index;

    public DeleteArchiveCommand(String archiveName, Index index) {
        requireAllNonNull(archiveName, index);

        this.archiveName = archiveName;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasArchive(archiveName)) {
            throw new CommandException(Messages.MESSAGE_NONEXISTENT_ARCHIVE_ENTERED);
        }

        List<Expense> archiveList = model.getFilteredArchiveExpenses(archiveName);

        if (index.getZeroBased() >= archiveList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToDelete = archiveList.get(index.getZeroBased());
        model.deleteArchiveExpense(archiveName, expenseToDelete);

        String feedback = String.format(MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete.getName(), archiveName);

        if (archiveList.size() == 0) {
            model.deleteArchive(archiveName);
            feedback += "\n" + String.format(MESSAGE_EMPTY_ARCHIVE_AFTER_DELETE_EXPENSE, archiveName);
            return new CommandResult(feedback);
        } else {
            return new CommandResult(feedback, false, false, archiveName);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteArchiveCommand // instanceof handles nulls
                && index.equals(((DeleteArchiveCommand) other).index)
                && archiveName.equals(((DeleteArchiveCommand) other).archiveName)); // state check
    }
}
