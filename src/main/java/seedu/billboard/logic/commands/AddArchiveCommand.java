package seedu.billboard.logic.commands;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Adds an expense to an existing archive.
 * If the archive does not exist, create a new archive to store the expense.
 */
public class AddArchiveCommand extends Command {

    public static final String COMMAND_WORD = "add-arc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates an archive of the given name and\n"
            + "Adds the expense identified by the index number used in the displayed expense list to the archive\n"
            + "Example: " + COMMAND_WORD + " 2 arc/Groceries";

    public static final String MESSAGE_SUCCESS = "[%1$s] added to [%2$s] archive";
    public static final String MESSAGE_CREATE_ARCHIVE = "[%s] archive created: ";

    private final String archiveName;
    private final Index targetIndex;

    /**
     * Creates an AddArchiveCommand to add the expense at the specified {@code targetIndex}
     * to the specified {@code archiveName}
     */
    public AddArchiveCommand(String archiveName, Index targetIndex) {
        this.archiveName = archiveName;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenses();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToArchive = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExpense(expenseToArchive);
        expenseToArchive.archiveTo(archiveName);

        String feedback = String.format(MESSAGE_SUCCESS, expenseToArchive.getName(), archiveName);
        if(!model.hasArchive(archiveName)) {
            model.addArchive(new Archive(archiveName, new ArrayList<>()));
            feedback = String.format(MESSAGE_CREATE_ARCHIVE, archiveName) + feedback;
        }

        model.addArchiveExpense(archiveName, expenseToArchive);

        return new CommandResult(feedback);
    }
}
