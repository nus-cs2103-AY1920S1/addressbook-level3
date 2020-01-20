package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyProjectList;
import seedu.address.model.performanceoverview.PerformanceOverview;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, IllegalValueException;

    CommandResult executeImageDrop(File imgFile, Person person) throws CommandException, ParseException, IllegalValueException;

    //======== AddressBook =======================================================================
    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    //======== ProjectList =======================================================================
    /**
     * Returns the ProjectList.
     *
     * @see seedu.address.model.Model#getProjectList()
     */
    ReadOnlyProjectList getProjectList();

    /** Returns an unmodifiable view of the filtered list of projects */
    ObservableList<Project> getFilteredProjectList();

    /**
     * Returns the user prefs' project list file path.
     */
    Path getProjectListFilePath();

    /**
     * Returns an Optional<Project> which is the current working project.
     */
    Optional<Project> getWorkingProject();

    /**
     * Remove the working project.
     */
    void removeWorkingProject();

    /**
     * Sets the working project.
     */
    void setWorkingProject(Project project);

    PerformanceOverview getPerformanceOverview();

    //======== GUI =======================================================================

    /**
     * Add a view history.
     */
    public void addUiEvent(UiEvent event);

    /**
     * Get the previous history.
     */
    public UiEvent getPreviousEvent();

    /**
     * Reset the view history because some condition is triggered.
     */
    public void eraseHistory();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
