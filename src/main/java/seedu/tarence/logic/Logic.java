package seedu.tarence.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.GuiSettings;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;

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
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the application.
     *
     * @see seedu.tarence.model.Model#getApplication()
     */
    ReadOnlyApplication getApplication();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of students */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered list of modules */
    ObservableList<Module> getFilteredModuleList();

    /** Returns an unmodifiable view of the filtered list of tutorials */
    ObservableList<Tutorial> getFilteredTutorialList();

    /**
     * Returns the user prefs' application file path.
     */
    Path getApplicationFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
