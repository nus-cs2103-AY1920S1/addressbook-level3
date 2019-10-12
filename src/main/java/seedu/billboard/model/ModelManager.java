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
 * Represents the in-memory model of the Billboard and Archive data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Billboard billboardExpenses;
    private final Billboard archiveExpenses;
    private final UserPrefs userPrefs;
    private final FilteredList<Expense> filteredExpense;
    private final FilteredList<Expense> filteredArchiveExpense;

    /**
     * Initializes a ModelManager with the given billboard and userPrefs.
     */
    public ModelManager(ReadOnlyBillboard billboardExpenses, ReadOnlyBillboard archiveExpenses, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(billboardExpenses, archiveExpenses, userPrefs);

        logger.fine("Initializing with billboard: " + billboardExpenses + "and archive: " + archiveExpenses
                 + " and user prefs " + userPrefs);

        this.billboardExpenses = new Billboard(billboardExpenses);
        this.archiveExpenses = new Billboard(archiveExpenses);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpense = new FilteredList<>(this.billboardExpenses.getExpenses());
        filteredArchiveExpense = new FilteredList<>(this.archiveExpenses.getExpenses());
    }

    public ModelManager() {
        this(new Billboard(), new Billboard(), new UserPrefs());
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

    @Override
    public Path getArchiveFilePath() {
        return userPrefs.getArchiveFilePath();
    }

    @Override
    public void setArchiveFilePath(Path archiveFilePath) {
        requireNonNull(archiveFilePath);
        userPrefs.setArchiveFilePath(archiveFilePath);
    }

    //=========== Billboard ================================================================================

    @Override
    public void setBillboardExpenses(ReadOnlyBillboard billboardExpenses) {
        this.billboardExpenses.resetData(billboardExpenses);
    }

    @Override
    public ReadOnlyBillboard getBillboardExpenses() {
        return billboardExpenses;
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return billboardExpenses.hasExpense(expense);
    }

    @Override
    public void deleteExpense(Expense target) {
        billboardExpenses.removeExpense(target);
    }

    @Override
    public void addExpense(Expense expense) {
        billboardExpenses.addExpense(expense);
        updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);
        billboardExpenses.setExpense(target, editedExpense);
    }

    //=========== Archive ================================================================================

    @Override
    public void setArchiveExpenses(ReadOnlyBillboard archiveExpenses) {
        this.archiveExpenses.resetData(archiveExpenses);
    }

    @Override
    public ReadOnlyBillboard getArchiveExpenses() {
        return archiveExpenses;
    }

    @Override
    public boolean hasArchiveExpense(Expense expense) {
        requireNonNull(expense);
        return archiveExpenses.hasExpense(expense);
    }

    @Override
    public void deleteArchiveExpense(Expense target) {
        archiveExpenses.removeExpense(target);
    }

    @Override
    public void addArchiveExpense(Expense expense) {
        archiveExpenses.addExpense(expense);
        updateFilteredArchiveExpenses(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void setArchiveExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);
        archiveExpenses.setExpense(target, editedExpense);
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
    public ObservableList<Expense> getFilteredArchiveExpenses() {
        return filteredArchiveExpense;
    }

    @Override
    public void updateFilteredArchiveExpenses(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredArchiveExpense.setPredicate(predicate);
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
        return billboardExpenses.equals(other.billboardExpenses)
                && userPrefs.equals(other.userPrefs)
                && filteredExpense.equals(other.filteredExpense);
    }

}
