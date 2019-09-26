package seedu.tarence.model;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Represents the in-memory model of the application data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Application application;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given student and userPrefs.
     */
    public ModelManager(ReadOnlyApplication application, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(application, userPrefs);

        logger.fine("Initializing with application: " + application + " and user prefs " + userPrefs);

        this.application = new Application(application);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.application.getPersonList());
    }

    public ModelManager() {
        this(new Application(), new UserPrefs());
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
    public Path getApplicationFilePath() {
        return userPrefs.getApplicationFilePath();
    }

    @Override
    public void setApplicationFilePath(Path applicationFilePath) {
        requireNonNull(applicationFilePath);
        userPrefs.setApplicationFilePath(applicationFilePath);
    }

    //=========== T.A.rence: Person methods ============================================================================

    @Override
    public void setApplication(ReadOnlyApplication application) {
        this.application.resetData(application);
    }

    @Override
    public ReadOnlyApplication getApplication() {
        return application;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return application.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        application.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        application.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        application.setPerson(target, editedPerson);
    }

    //=========== T.A.rence: Module methods ============================================================================

    @Override
    public void addModule(Module module) {
        requireNonNull(module);
        application.addModule(module);
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return application.hasModule(module);
    }

    //=========== T.A.rence: Tutorial methods ========================================================================

    @Override
    public void addTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        application.addTutorial(tutorial);
    }

    @Override
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return application.hasTutorial(tutorial);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedApplication}
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
        return application.equals(other.application)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
