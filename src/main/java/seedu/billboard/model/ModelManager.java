package seedu.billboard.model;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;

/**
 * Represents the in-memory model of the Billboard and Archive data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Billboard billboard;
    private final ArchiveWrapper archives;
    private final UserPrefs userPrefs;
    private final FilteredList<Expense> filteredExpense;
    private final HashMap<String, FilteredList<Expense>> filteredArchives;

    /**
     * Initializes a ModelManager with the given billboard and userPrefs.
     */
    public ModelManager(ReadOnlyBillboard initialBillboard, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(initialBillboard, userPrefs);

        List<Expense> archiveExpenses = initialBillboard.filterArchiveExpenses();
        ReadOnlyBillboard noArchiveExpensesBillboard = initialBillboard.removeArchiveExpenses();
        this.archives = new ArchiveWrapper(archiveExpenses);
        this.billboard = new Billboard(noArchiveExpensesBillboard);
        this.userPrefs = new UserPrefs(userPrefs);

        logger.fine("Initializing with billboard: " + billboard
                + " and Archives: " + archives
                + "and user prefs " + userPrefs);

        filteredExpense = new FilteredList<>(this.billboard.getExpenses());
        filteredArchives = new HashMap<>();
        Set<String> archiveNames = this.archives.getArchiveNames();
        for (String archiveName : archiveNames) {
            filteredArchives.put(archiveName,
                    new FilteredList<>(this.archives.getArchiveExpenses(archiveName)));
        }

    }

    public ModelManager() {
        this(new Billboard(), new UserPrefs());
    }

    @Override
    public Billboard getCombinedBillboard() {
        List<Expense> combinedExpenses = new ArrayList<>();
        List<Expense> nonArchiveExpenses = billboard.getExpenses();
        List<Expense> archiveExpense = archives.getExpenseList();

        combinedExpenses.addAll(nonArchiveExpenses);
        combinedExpenses.addAll(archiveExpense);

        Billboard billboard = new Billboard();
        billboard.setExpenses(combinedExpenses);

        return billboard;
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

    //=========== Archive ================================================================================

    @Override
    public List<String> getArchiveNames() {
        return new ArrayList<>(archives.getArchiveNames());
    }

    @Override
    public void setArchives(ReadOnlyArchiveWrapper archives) {
        this.archives.resetData(archives);
    }

    @Override
    public ReadOnlyArchiveWrapper getArchives() {
        return archives;
    }

    @Override
    public boolean hasArchiveExpense(String archiveName, Expense expense) {
        requireAllNonNull(archiveName, expense);
        return archives.hasArchiveExpense(archiveName, expense);
    }

    @Override
    public boolean hasArchive(String archiveName) {
        return archives.hasArchive(archiveName);
    }

    @Override
    public void deleteArchive(String archiveName) {
        archives.removeArchive(archiveName);
    }

    @Override
    public void deleteArchiveExpense(String archiveName, Expense target) {
        archives.removeArchiveExpense(archiveName, target);
    }

    @Override
    public void addArchiveExpense(String archiveName, Expense expense) {
        archives.addArchiveExpense(archiveName, expense);
        updateFilteredArchiveExpenses(archiveName, PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void addArchive(Archive archive) {
        archives.addArchive(archive);
        String archiveName = archive.getArchiveName();
        filteredArchives.put(archiveName,
                new FilteredList<>(this.archives.getArchiveExpenses(archiveName)));
    }

    //==========Tag methods===================================================================================
    @Override
    public Set<Tag> retrieveTags(List<String> toRetrieve) {
        requireNonNull(toRetrieve);
        return billboard.retrieveTags(toRetrieve);
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
    public ObservableList<Expense> getFilteredArchiveExpenses(String archiveName) {
        return filteredArchives.get(archiveName);
    }

    @Override
    public void updateFilteredArchiveExpenses(String archiveName, Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredArchives.get(archiveName).setPredicate(predicate);
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
                && archives.equals(other.archives)
                && userPrefs.equals(other.userPrefs)
                && filteredExpense.equals(other.filteredExpense)
                && filteredArchives.equals(other.filteredArchives);
    }
}
