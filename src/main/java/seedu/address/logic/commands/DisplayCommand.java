package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.Budget;
import seedu.address.ui.tab.Tab;

/**
 * Displays a {@code Projection} identified using it's displayed index from the bank account.
 * TODO: extend display to other data types
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";
    public static final String MESSAGE_USAGE = seedu.address.logic.commands.ViewCommand.COMMAND_WORD
            + ": Displays a specified facet of the user's state.\n"
            + "Parameter: SIMI\n"
            + "Example: " + seedu.address.logic.commands.ViewCommand.COMMAND_WORD + " projection";

    public static final String MESSAGE_SUCCESS = "Display %s success! Enter \"close\" to terminate display window\n";

    private final String stateType;
    private final Index targetIndex;

    public DisplayCommand(String stateType, Index targetIndex) {
        requireNonNull(stateType);
        this.stateType = stateType;

        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.stateType.equals("p")) {
            ObservableList<Projection> lastShownList = model.getFilteredProjectionsList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PROJECTION_DISPLAYED_INDEX);
            }
            lastShownList.get(targetIndex.getZeroBased()).displayAsStage();
            return new CommandResult(String.format(MESSAGE_SUCCESS, "transactions"));
        } else if (this.stateType.equals("b")) {
            ObservableList<Budget> lastShownList = model.getFilteredBudgetList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
            }

            Budget budgetToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteBudget(budgetToDelete);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_SUCCESS, budgetToDelete),
                    false, false, Tab.BUDGET);
        } else {
            throw new CommandException("Unknown command error");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayCommand // instanceof handles nulls
                && targetIndex.equals(((DisplayCommand) other).targetIndex)); // state check
    }
}
