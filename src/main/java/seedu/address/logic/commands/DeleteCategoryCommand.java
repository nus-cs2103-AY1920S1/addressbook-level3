package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Budget;

public class DeleteCategoryCommand extends Command {

    public static final String COMMAND_WORD = "deleteCategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the category from guiltTrip"
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY TYPE "
            + PREFIX_DESC + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "Expense "
            + PREFIX_DESC + "Spicy Food ";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Category: %1$s";

    private final Index targetIndex;

    public DeleteCategoryCommand(String categoryName, String categoryType) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Budget> lastShownList = model.getFilteredBudgets();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Budget entryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBudget(entryToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBudgetCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBudgetCommand) other).targetIndex)); // state check
    }
}
