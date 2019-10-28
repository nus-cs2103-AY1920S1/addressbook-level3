package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final ProjectList projectList;
    private final FilteredList<Project> filteredProjects;

    // this is the current branch
    private Optional<Project> workingProject = Optional.empty();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyProjectList projectList) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.projectList = new ProjectList(projectList);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredProjects = new FilteredList<>(this.projectList.getProjectList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new ProjectList());
    }

    /**
     * Mimic a git checkout action. What it does is simply assign the project as the working
     * project.
     * @param project
     */
    public void setWorkingProject(Project project) {
        this.workingProject = Optional.of(project);
    }

    /**
     * @return An Optional object containing the working project.
     */
    public Optional<Project> getWorkingProject() {
        if (workingProject.isEmpty()) {
            return Optional.empty();
        } else {
            return workingProject;
        }
    }

    /**
     * @return If the user checkout to a project.
     */
    public boolean isCheckedOut() {
        return workingProject.isPresent();
    }

    public String checkoutConstrain() {
        return "Please checkout to a project before proceeding";
    }

    @Override
    public List<Person> getMembers() {
        List<Person> members = new ArrayList<>();
        if (getWorkingProject().isEmpty()) {
            return members;
        }
        Project workingProject = getWorkingProject().get();
        addressBook.getPersonList().forEach(person -> {
            if (workingProject.hasMember(person)) {
                members.add(person);
            }
        });
        return members;
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

    //=========== AddressBook ================================================================================

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
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

    //=========== ProjectList ================================================================================

    @Override
    public Path getProjectListFilePath() {
        return userPrefs.getProjectListFilePath();
    }

    @Override
    public void setProjectListFilePath(Path projectListFilePath) {
        requireNonNull(projectListFilePath);
        userPrefs.setProjectListFilePath(projectListFilePath);
    }

    @Override
    public void setProjectList(ReadOnlyProjectList projectList) {
        this.projectList.resetData(projectList);
    }

    @Override
    public ReadOnlyProjectList getProjectList() {
        return projectList;
    }

    @Override
    public boolean hasProject(Project project) {
        requireNonNull(project);
        return projectList.hasProject(project);
    }

    @Override
    public void deleteProject(Project target) {
        projectList.removeProject(target);
    }

    @Override
    public void addProject(Project project) {
        projectList.addProject(project);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
    }

    @Override
    public void setProject(Project target, Project editedProject) {
        requireAllNonNull(target, editedProject);

        projectList.setProject(target, editedProject);
        setWorkingProject(editedProject);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
    }

    @Override
    public void deleteMember(String name) {
        projectList.deleteMember(name);
        updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
    }

    //=========== Email Account for Owner of application ======================================================

    private OwnerAccount ownerAccount;
    private boolean isSignedIn = false;

    public void signIn(OwnerAccount ownerAccount) throws Exception {
        String account = ownerAccount.getEmail().value;
        String pass = ownerAccount.getPassword();
        Mailer.sendEmail(account, pass, "cs2103t17@gmail.com", "SignInCheck", "Email Exists and can sign in");
        this.ownerAccount = ownerAccount;
        this.isSignedIn = true;
    }

    public boolean isSignedIn() {
        return this.isSignedIn;
    }

    public OwnerAccount getOwnerAccount() {
        return this.ownerAccount;
    }

    public void logOut() {
        this.ownerAccount = null;
        this.isSignedIn = false;
    }

    //=========== Filtered Project List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedProjectList}
     */
    @Override
    public ObservableList<Project> getFilteredProjectList() {
        return filteredProjects;
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        requireNonNull(predicate);
        filteredProjects.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && projectList.equals(other.projectList)
                && filteredProjects.equals(other.filteredProjects);
    }

}
