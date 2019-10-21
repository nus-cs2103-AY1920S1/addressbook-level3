package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_ARCHIVE;

import java.util.ArrayList;
import java.util.List;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;

/**
 * Adds an expense to an existing archive.
 * If the archive does not exist, create a new archive to store the expense.
 */
public class AddArchiveCommand extends ArchiveCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = ArchiveCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Creates an archive of the given name and\n"
            + "Adds the expense identified by the index number used in the displayed expense list to the archive\n"
            + "Example: " + ArchiveCommand.COMMAND_WORD + " " + COMMAND_WORD + " 2 " + PREFIX_ARCHIVE + "Groceries";

    public static final String MESSAGE_SUCCESS_EXISTING_ARCHIVE = "[%1$s] added to [%2$s] archive";
    public static final String MESSAGE_SUCCESS_CREATE_ARCHIVE =
            "[%1$s] archive created: [%2$s] added to [%3$s] archive";

    private final String archiveName;
    private final Index targetIndex;

    /**
     * Creates an AddArchiveCommand to add the expense at the specified {@code targetIndex}
     * to the specified {@code archiveName}
     */
    public AddArchiveCommand(String archiveName, Index targetIndex) {
        requireAllNonNull(archiveName, targetIndex);

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

        if (!model.hasArchive(archiveName)) {
            model.addArchive(new Archive(archiveName, new ArrayList<>()));
            model.addArchiveExpense(archiveName, expenseToArchive);

            return new CommandResult(String.format(MESSAGE_SUCCESS_CREATE_ARCHIVE,
                    archiveName, expenseToArchive.getName(), archiveName));
        }

        model.addArchiveExpense(archiveName, expenseToArchive);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS_EXISTING_ARCHIVE, expenseToArchive.getName(), archiveName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddArchiveCommand // instanceof handles nulls
                && targetIndex.equals(((AddArchiveCommand) other).targetIndex)
                && archiveName.equals(((AddArchiveCommand) other).archiveName)); // state check
    }
}
