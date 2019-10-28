package seedu.weme.logic;

import java.nio.file.Path;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.ModelContext;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.Stats;
import seedu.weme.model.template.MemeCreation;
import seedu.weme.model.template.Template;

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
     * Prompt the command and return the command prompt.
     * @param commandText The command as entered by the user
     * @return the command prompt
     * @throws PromptException If and error occurs during prompting.
     */
    CommandPrompt prompt(String commandText) throws PromptException;

    /**
     * Returns weme.
     *
     * @see seedu.weme.model.Model#getWeme()
     */
    ReadOnlyWeme getWeme();

    /** Returns an unmodifiable view of the filtered list of memes */
    ObservableList<Meme> getFilteredMemeList();

    /** Returns an unmodifiable view of the filtered list of imported memes */
    ObservableList<Meme> getFilteredImportList();

    /** Returns an unmodifiable view of the filtered staged list of memes */
    ObservableList<Meme> getFilteredStagedMemeList();

    /** Returns an unmodifiable view of the filtered list of templates */
    ObservableList<Template> getFilteredTemplateList();

    /** Returns an unmodifiable view of the user preferences of Weme */
    ObservableMap<String, String> getObservableUserPreferences();

    /**
     * Returns the current context.
     */
    ObservableValue<ModelContext> getContext();

    /**
     * Sets the context.
     *
     * @param context the context to switch to
     */
    void setContext(ModelContext context);

    /**
     * Returns the current meme creation session.
     *
     * @return the current meme creation session.
     */
    MemeCreation getMemeCreation();

    /**
     * Returns the user prefs' Weme file path.
     */
    Path getWemeFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the stats data.
     */
    Stats getStats();

    /**
     * Returns an unmodifiable view of like data.
     */
    ObservableMap<String, Integer> getObservableLikeData();

    /**
     * Handles any logic that needs to be done before exiting Weme.
     */
    void cleanUp();
}
