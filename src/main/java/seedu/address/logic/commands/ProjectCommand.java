package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comparator.DateComparator;
import seedu.address.model.Model;
import seedu.address.model.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.util.Date;



/**
 * Projects user's future balance based on income/outflow history
 */
public class ProjectCommand extends Command {

    public static final String COMMAND_WORD = "project";
    public static final String MESSAGE_INVALID_DATE = "Date must be set in the future";
    public static final String MESSAGE_VOID_TRANSACTION_HISTORY =
            "Transaction history is currently empty. It is impossible to cast a projection.";
    public static final String MESSAGE_INSUFFICIENT_TRANSACTION_HISTORY =
            "There are too few transactions. It is impossible to cast a projection.";
    public static final String SMALL_SAMPLE_SIZE =
            "Projection is based on a small sample size, and may be limited in its accuracy";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Project future balance based on past income/outflow.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "12122103 09:00";
    public static final String MESSAGE_WARNING = "[WARNING] %1$s";
    public static final String MESSAGE_SUCCESS = "Projected balance: %s\n%s";
    public static final String MESSAGE_BUDGET_SUCCESS =
            "You are on track to meeting your budget of %s, with a surplus of %s!\n";
    public static final String MESSAGE_BUDGET_CAUTION =
            "You are likely to exceed your budget of %s, with a deficit of %s!\n";

    private static final int RECOMMENDED_MINIMUM_TRANSACTIONS = 15;
    private static final int REQUIRED_MINIMUM_TRANSACTIONS = 5;

    public final Date date;
    private Index budgetIdx;
    private Projection projection;

    public ProjectCommand(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    public ProjectCommand(Date date, Index budgetIdx) {
        this(date);
        this.budgetIdx = budgetIdx;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<BankAccountOperation> transactionHistory =
                model.getBankAccount().getSortedTransactionHistory(new DateComparator());

        if (transactionHistory.isEmpty()) {
            throw new CommandException(MESSAGE_VOID_TRANSACTION_HISTORY);
        }

        if (transactionHistory.size() < REQUIRED_MINIMUM_TRANSACTIONS) {
            throw new CommandException(MESSAGE_INSUFFICIENT_TRANSACTION_HISTORY);
        }

        if (this.getBudgetIdx().isPresent()) {
            try {
                Budget budget = model.getFilteredBudgetList().get(this.budgetIdx.getZeroBased());
                this.projection = new Projection(transactionHistory, date, budget);
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
            }
        } else {
            this.projection = new Projection(transactionHistory, date);
        }

        return transactionHistory.size() < RECOMMENDED_MINIMUM_TRANSACTIONS
                ? new CommandResult(String.format(MESSAGE_SUCCESS, projection.toString(),
                        projection.getBudgetForecastText()),
                        String.format(MESSAGE_WARNING, SMALL_SAMPLE_SIZE))
                : new CommandResult(String.format(MESSAGE_SUCCESS, projection.toString(),
                        projection.getBudgetForecastText()));
    }

    Optional<Index> getBudgetIdx() {
        return this.budgetIdx == null ? Optional.empty() : Optional.of(this.budgetIdx);
    }
}
