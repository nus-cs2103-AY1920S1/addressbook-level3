package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;

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
    public static final String MESSAGE_BUDGET_CAUTION =
            "You are likely to exceed your budget of $%s, with a deficit of $%s!\n";
    public static final String MESSAGE_BUDGET_ON_TRACK =
            "You are on track to meeting your budget of $%s, with a surplus of $%s!\n";
    public static final String MESSAGE_SUCCESS = "Projected balance: $%s\n%s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Project future balance based on past income/outflow.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE [" + PREFIX_CATEGORY + "CATEGORY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "12122103 "
            + PREFIX_CATEGORY + "Food";
    public static final int REQUIRED_MINIMUM_TRANSACTIONS = 5;

    private static final String MESSAGE_VOID_TRANSACTION_HISTORY =
            "There are no transactions in %s. It is impossible to cast a projection.";
    private static final String MESSAGE_INSUFFICIENT_TRANSACTION_HISTORY =
            "There are too few transactions. It is impossible to cast a projection.";
    private static final String MESSAGE_PROJECTION_TOO_PROTRACTED =
            "Projections should be a maximum of 2 years (730 days) from now.";
    private static final String SMALL_SAMPLE_SIZE =
            "Projection is based on a small sample size, and may be limited in its accuracy";
    private static final String PROTRACTED_PROJECTION =
            "Projection is cast far into the future, and may be limited in its accuracy";
    private static final String MESSAGE_WARNING = "[WARNING] %1$s";
    private static final int REQUIRED_MAXIMUM_DAYS_TO_PROJECT = 730;
    private static final int RECOMMENDED_MAXIMUM_DAYS_TO_PROJECT = 365;
    private static final int RECOMMENDED_MINIMUM_TRANSACTIONS = 15;
    private static final String MESSAGE_DUPLICATE = "A projection to %s already exists.";

    public final Date date;
    private Category category;

    public ProjectCommand(Date date) {
        requireNonNull(date);
        this.date = date;
        this.category = Category.GENERAL;
    }

    public ProjectCommand(Date date, Category category) {
        this.date = date;
        this.category = category;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ensureDateToProjectIsWithinRange(this.date);
        ObservableList<BankAccountOperation> transactionHistory =
                model.getBankAccount().getSortedTransactionHistory(new DateComparator());
        Projection projection = defineProjection(transactionHistory, model);

        if (model.has(projection)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, projection.getDate().toString()),
                    false, false, Tab.PROJECTION);
        }
        model.add(projection);
        model.commitUserState();

        if (transactionHistory.size() < RECOMMENDED_MINIMUM_TRANSACTIONS
                && Math.abs(Date.daysBetween(Date.now(), this.date)) > RECOMMENDED_MAXIMUM_DAYS_TO_PROJECT) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, projection.toString(),
                    projection.getAllBudgetForecastText()),
                    String.format(MESSAGE_WARNING, SMALL_SAMPLE_SIZE),
                    String.format(MESSAGE_WARNING, PROTRACTED_PROJECTION));
        } else if (transactionHistory.size() < RECOMMENDED_MINIMUM_TRANSACTIONS) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, projection.toString(),
                    projection.getAllBudgetForecastText()),
                    String.format(MESSAGE_WARNING, SMALL_SAMPLE_SIZE));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, projection.toString(),
                projection.getAllBudgetForecastText()));
    }

    /**
     * Defines the {@code Projection} by filtering {@code transactionHistory} according to the
     * projection's category, if present
     * @param transactionHistory to be projected upon by {@code Projection}
     */
    private Projection defineProjection(ObservableList<BankAccountOperation> transactionHistory, Model model)
            throws CommandException {
        if (this.category.equals(Category.GENERAL)) {
            ensureMinimumTransactions(transactionHistory);
            ObservableList<Budget> budgets = model.getFilteredBudgetList()
                    .filtered(x -> x.getCategories().contains(Category.GENERAL));
            return new Projection(transactionHistory, date, budgets);
        } else {
            transactionHistory = transactionHistory.filtered(x -> x.getCategories().contains(this.category));
            ensureMinimumTransactions(transactionHistory);
            ObservableList<Budget> budgets = model.getFilteredBudgetList()
                    .filtered(x -> x.getCategories().contains(this.category));
            return new Projection(transactionHistory, date, budgets, this.category);
        }
    }

    Category getCategory() {
        return this.category;
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

    /**
     * Ensures that the {@code Projection} is not cast beyond the maximum number of days to project
     */
    private void ensureDateToProjectIsWithinRange(Date toProject) throws CommandException {
        int daysFromNow = Math.abs(Date.daysBetween(Date.now(), toProject));
        if (daysFromNow >= REQUIRED_MAXIMUM_DAYS_TO_PROJECT) {
            throw new CommandException(MESSAGE_PROJECTION_TOO_PROTRACTED);
        }
    }
}
