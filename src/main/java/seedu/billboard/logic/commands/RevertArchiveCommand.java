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
 * Unarchives an expense in an archive:
 * Removes an expense to an existing archive and adds it back to the current list of expenses.
 */
public class RevertArchiveCommand extends ArchiveCommand {
    public static final String COMMAND_WORD = "revert";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the expense identified by the index number used in the displayed archive expense list\n"
            + "Example: " + ArchiveCommand.COMMAND_WORD + " " + COMMAND_WORD + " 2 " + PREFIX_ARCHIVE + "Groceries";

    public static final String MESSAGE_SUCCESS =
            "Removed [%1$s] from [%2$s] archive and added back to current expense list";

    public static final String MESSAGE_EMPTY_ARCHIVE_AFTER_REVERT_EXPENSE =
            "[%1$s] archive is now empty and will be deleted";

    private final String archiveName;
    private final Index index;

    /**
     * Creates an RevertArchiveCommand to unarchive the expense at the specified {@code targetIndex}
     * in the specified {@code archiveName}
     */
    public RevertArchiveCommand(String archiveName, Index index) {
        requireAllNonNull(archiveName, index);

        this.index = index;
        this.archiveName = archiveName;
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

        Expense expenseToUnarchive = archiveList.get(index.getZeroBased());
        model.deleteArchiveExpense(archiveName, expenseToUnarchive);
        model.addExpense(expenseToUnarchive);

        String feedback = String.format(MESSAGE_SUCCESS, expenseToUnarchive.getName(), archiveName);

        if (archiveList.size() == 0) {
            model.deleteArchive(archiveName);
            feedback += "\n" + String.format(MESSAGE_EMPTY_ARCHIVE_AFTER_REVERT_EXPENSE, archiveName);
            return new CommandResult(feedback);
        } else {
            return new CommandResult(feedback, false, false, archiveName);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RevertArchiveCommand // instanceof handles nulls
                && index.equals(((RevertArchiveCommand) other).index)
                && archiveName.equals(((RevertArchiveCommand) other).archiveName)); // state check
    }
}
