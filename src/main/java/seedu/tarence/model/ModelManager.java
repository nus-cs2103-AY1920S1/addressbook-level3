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
import seedu.tarence.logic.commands.Command;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.NameContainsKeywordsPredicate;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * Represents the in-memory model of the application data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Application application;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<Module> filteredModules;
    private final FilteredList<Tutorial> filteredTutorials;

    /**
     * Initializes a ModelManager with the given student and userPrefs.
     */
    public ModelManager(ReadOnlyApplication application, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(application, userPrefs);

        logger.fine("Initializing with application: " + application + " and user prefs " + userPrefs);

        this.application = new Application(application);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.application.getStudentList());
        filteredPersons = new FilteredList<>(this.application.getPersonList());
        filteredModules = new FilteredList<>(this.application.getModuleList());
        filteredTutorials = new FilteredList<>(this.application.getTutorialList());
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

    //=========== T.A.rence: Student methods ===========================================================================

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return application.hasStudent(student);
    }

    @Override
    public void addStudent(Student student) {
        application.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        application.setStudent(target, editedStudent);
    }

    @Override
    public void deleteStudent(Student student) {
        application.removeStudent(student);
    }

    @Override
    public void addStudentToTutorial(Student student) {
        requireNonNull(student);
        application.addStudentToTutorial(student);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    //=========== T.A.rence: Module methods ============================================================================

    @Override
    public void addModule(Module module) {
        requireNonNull(module);
        application.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return application.hasModule(module);
    }

    @Override
    public void deleteModule(Module module) {
        requireNonNull(module);
        application.removeModule(module);
    }

    @Override
    public void deleteTutorialsFromModule(Module module) {
        requireNonNull(module);
        application.removeTutorialsFromModule(module);
    }

    @Override
    public boolean hasModuleOfCode(ModCode modCode) {
        requireNonNull(modCode);
        return application.hasModuleOfCode(modCode);
    }

    @Override
    public void addTutorialToModule(Tutorial tutorial) {
        requireNonNull(tutorial);
        application.addTutorialToModule(tutorial);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
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

    @Override
    public boolean hasTutorialInModule(ModCode modCode, TutName tutName) {
        requireAllNonNull(modCode, tutName);
        return application.hasTutorialInModule(modCode, tutName);
    }

    @Override
    public int getNumberOfTutorialsOfName(TutName tutName) {
        requireNonNull(tutName);
        return application.getNumberOfTutorialsOfName(tutName);
    }

    @Override
    public void deleteTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        application.removeTutorial(tutorial);
    }

    @Override
    public void setAttendance(Tutorial tutorial, Week week, Student student) {
        requireAllNonNull(tutorial, week, student);
        application.setAttendance(tutorial, week, student);
    }

    @Override
    public void deleteStudentsFromTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        application.removeStudentsFromTutorial(tutorial);
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

    /**
     * Returns an unmodifiable view of the list of {@code Person} containing students backed by
     * the internal list of {@code versionedApplication}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedApplication}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Tutorial} backed by the internal list of
     * {@code versionedApplication}
     */
    @Override
    public ObservableList<Tutorial> getFilteredTutorialList() {
        return filteredTutorials;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStudentList(NameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTutorialList(Predicate<Tutorial> predicate) {
        requireNonNull(predicate);
        filteredTutorials.setPredicate(predicate);
    }

    @Override
    public void storePendingCommand(Command command) {
        requireNonNull(command);
        application.storePendingCommand(command);
    }

    @Override
    public Command getPendingCommand() {
        return application.retrievePendingCommand();
    }

    @Override
    public boolean hasPendingCommand() {
        return application.hasPendingCommand();
    }

    @Override
    public Command peekPendingCommand() {
        return application.peekPendingCommand();
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
                && filteredPersons.equals(other.filteredPersons)
                && filteredStudents.equals(other.filteredStudents)
                && filteredModules.equals(other.filteredModules)
                && filteredTutorials.equals(other.filteredTutorials);
    }

}
