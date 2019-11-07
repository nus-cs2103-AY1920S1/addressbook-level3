package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.comparator.DateComparator;
import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.projection.Projection;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.util.Date;
import seedu.address.ui.tab.Tab;


/**
 * Projects user's future balance based on income/outflow history
 */
public class ProjectCommand extends Command {

    public static final String COMMAND_WORD = "project";
    public static final String MESSAGE_INVALID_DATE = "Date must be set in the future";
    public static final String MESSAGE_VOID_TRANSACTION_HISTORY =
            "There are no transactions in %s. It is impossible to cast a projection.";
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

    public static final int RECOMMENDED_MINIMUM_TRANSACTIONS = 15;
    public static final int REQUIRED_MINIMUM_TRANSACTIONS = 5;
    private static final String MESSAGE_DUPLICATE = "A projection to %s already exists.";

    public final Date date;
    private Category category;
    private ObservableList<Budget> budgets;
    private Projection projection;

    public ProjectCommand(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    public ProjectCommand(Date date, Category category) {
        this(date);
        this.category = category;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<BankAccountOperation> transactionHistory =
                model.getBankAccount().getSortedTransactionHistory(new DateComparator());

        if (this.category != null) {
            transactionHistory = transactionHistory.filtered(x -> x.getCategories().contains(this.category));
            this.budgets = model.getFilteredBudgetList().filtered(x -> x.getCategories().contains(this.category));
            ensureMinimumTransactions(transactionHistory);
            this.projection = new Projection(transactionHistory, date, this.budgets, this.category);
        } else {
            ensureMinimumTransactions(transactionHistory);
            this.projection = new Projection(transactionHistory, date);
        }

        if (model.has(this.projection)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, this.projection.getDate().toString()),
                    false, false, Tab.PROJECTION);
        }
        model.add(this.projection);
        model.commitUserState();

        return transactionHistory.size() < RECOMMENDED_MINIMUM_TRANSACTIONS
                ? new CommandResult(String.format(MESSAGE_SUCCESS, projection.toString(),
                projection.getAllBudgetForecastText()),
                String.format(MESSAGE_WARNING, SMALL_SAMPLE_SIZE))
                : new CommandResult(String.format(MESSAGE_SUCCESS, projection.toString(),
                projection.getAllBudgetForecastText()));
    }

    Optional<Category> getCategory() {
        return this.category == null ? Optional.empty() : Optional.of(this.category);
    }

    /**
     * Ensures there are enough transactions to meaningfully execute the {@code ProjectCommand}
     */
    private void ensureMinimumTransactions(List<BankAccountOperation> transactionHistory) throws CommandException {
        if (transactionHistory.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_VOID_TRANSACTION_HISTORY,
                    this.category == null ? "your transaction history" : this.category.toString()));
        }

        if (transactionHistory.size() < REQUIRED_MINIMUM_TRANSACTIONS) {
            throw new CommandException(MESSAGE_INSUFFICIENT_TRANSACTION_HISTORY);
        }
    }
}
