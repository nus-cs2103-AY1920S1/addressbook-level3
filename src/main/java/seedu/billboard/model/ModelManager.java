package seedu.billboard.model;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.model.expense.Expense;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Billboard billboard;
    private final UserPrefs userPrefs;
    private final FilteredList<Expense> filteredExpense;

    /**
     * Initializes a ModelManager with the given billboard and userPrefs.
     */
    public ModelManager(ReadOnlyBillboard billboard, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(billboard, userPrefs);

        logger.fine("Initializing with address book: " + billboard + " and user prefs " + userPrefs);

        this.billboard = new Billboard(billboard);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpense = new FilteredList<>(this.billboard.getExpenses());
    }

    public ModelManager() {
        this(new Billboard(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getBillboardFilePath() {
        return userPrefs.getBillboardFilePath();
    }

    @Override
    public void setBillboardFilePath(Path billboardFilePath) {
        requireNonNull(billboardFilePath);
        userPrefs.setBillboardFilePath(billboardFilePath);
    }

    //=========== Billboard ================================================================================

    @Override
    public void setBillboard(ReadOnlyBillboard billboard) {
        this.billboard.resetData(billboard);
    }

    @Override
    public ReadOnlyBillboard getBillboard() {
        return billboard;
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return billboard.hasExpense(expense);
    }

    @Override
    public void deleteExpense(Expense target) {
        billboard.removeExpense(target);
    }

    @Override
    public void addExpense(Expense expense) {
        billboard.addExpense(expense);
        updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        billboard.setExpense(target, editedExpense);
    }

    //=========== Filtered Expense List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versioned Billboard}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenses() {
        return filteredExpense;
    }

    @Override
    public void updateFilteredExpenses(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredExpense.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return billboard.equals(other.billboard)
                && userPrefs.equals(other.userPrefs)
                && filteredExpense.equals(other.filteredExpense);
    }

}
