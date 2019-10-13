package seedu.ichifund.model;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.commons.core.LogsCenter;
import seedu.ichifund.model.person.Person;

/**
 * Represents the in-memory model of the fund book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FundBook fundBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given fundBook and userPrefs.
     */
    public ModelManager(ReadOnlyFundBook fundBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(fundBook, userPrefs);

        logger.fine("Initializing with fund book: " + fundBook + " and user prefs " + userPrefs);

        this.fundBook = new FundBook(fundBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.fundBook.getPersonList());
    }

    public ModelManager() {
        this(new FundBook(), new UserPrefs());
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
    public Path getFundBookFilePath() {
        return userPrefs.getFundBookFilePath();
    }

    @Override
    public void setFundBookFilePath(Path fundBookFilePath) {
        requireNonNull(fundBookFilePath);
        userPrefs.setFundBookFilePath(fundBookFilePath);
    }

    //=========== FundBook ================================================================================

    @Override
    public void setFundBook(ReadOnlyFundBook fundBook) {
        this.fundBook.resetData(fundBook);
    }

    @Override
    public ReadOnlyFundBook getFundBook() {
        return fundBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return fundBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        fundBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        fundBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        fundBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedFundBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
        return fundBook.equals(other.fundBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
