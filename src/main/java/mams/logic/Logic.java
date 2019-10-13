package mams.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import mams.commons.core.GuiSettings;
import mams.logic.commands.CommandResult;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.parser.exceptions.ParseException;
import mams.model.Model;
import mams.model.ReadOnlyMams;
import mams.model.module.Module;
import mams.model.student.Student;

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
     * Returns the Mams.
     *
     * @see Model#getMams()
     */
    ReadOnlyMams getMams();

    /** Returns an unmodifiable view of the filtered list of students */
    ObservableList<Student> getFilteredStudentList();
    ObservableList<Module> getFilteredModuleList();

    // TODO add appeal lists getters

    /**
     * Returns the user prefs' address book file path.
     */
    Path getMamsFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
