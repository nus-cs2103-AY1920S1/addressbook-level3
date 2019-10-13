package com.dukeacademy.logic;

import java.nio.file.Path;

import com.dukeacademy.commons.core.GuiSettings;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.exceptions.ParseException;
import com.dukeacademy.model.Model;
import com.dukeacademy.model.ReadOnlyQuestionBank;
import com.dukeacademy.model.question.Question;

import javafx.collections.ObservableList;

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
     * Returns the QuestionBank.
     *
     * @see Model#getQuestionBank()
     */
    ReadOnlyQuestionBank getQuestionBank();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Question> getFilteredPersonList();

    /**
     * Returns the user prefs' question bank file path.
     */
    Path getQuestionBankFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
