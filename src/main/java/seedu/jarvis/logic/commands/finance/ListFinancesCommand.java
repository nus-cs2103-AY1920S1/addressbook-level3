package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.financetracker.FinanceTrackerModel;

/**
 * Lists all expenditures in the finance tracker to the user.
 */
public class ListFinancesCommand extends Command {

    public static final String COMMAND_WORD = "list-finances";

    public static final String MESSAGE_SUCCESS = "Listed all finances";

    public static final String MESSAGE_NO_INVERSE = "The command " + COMMAND_WORD + " cannot be undone";

    public static final boolean HAS_INVERSE = false;

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredInstallmentList(FinanceTrackerModel.PREDICATE_SHOW_ALL_INSTALLMENTS);
        model.updateFilteredPurchaseList(FinanceTrackerModel.PREDICATE_SHOW_ALL_PURCHASES);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * There is no available inverse execution available, always throws a {@code CommandException}.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @throws CommandException Always thrown.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }
}
