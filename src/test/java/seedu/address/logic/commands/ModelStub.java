package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.*;
import seedu.address.model.finance.Budget;
import seedu.address.model.performanceoverview.PerformanceOverview;
import seedu.address.model.person.Person;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public boolean hasBudget(Budget budget) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void setWorkingProject(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeWorkingProject() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Project> getWorkingProject() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isCheckedOut() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Person> getMembers() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerformanceOverview(PerformanceOverview overview) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public PerformanceOverview getPerformanceOverview() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void editTaskInAllPersons(Task taskToEdit, Task editedTask, Project currWorkingProject) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTaskInAllPersons(Task task, Project currWorkingProject) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteMeetingInAllPersons(Meeting meeting, Project currWorkingProject) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Person> getMembersOf(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isSignedIn() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void signIn(OwnerAccount ownerAccount) throws Exception {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public OwnerAccount getOwnerAccount() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void logOut() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getProjectListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setProjectListFilePath(Path projectListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setProjectList(ReadOnlyProjectList projectList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyProjectList getProjectList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasProject(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteProject(Project target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addProject(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setProject(Project target, Project editedProject) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteMember(String name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void editInAllProjects(Person personToEdit, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Project> getFilteredProjectList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredProjectList(Predicate<Project> predicate) {
        throw new AssertionError("This method should not be called.");
    }
}
