package seedu.weme.logic;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Optional;

import javafx.beans.property.SimpleIntegerProperty;
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
import seedu.weme.model.template.MemeText;
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
    ObservableList<Meme> getImportMemeList();

    /** Returns an unmodifiable view of the filtered staged list of memes */
    ObservableList<Meme> getStagedMemeList();

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
     * Returns the list of {@code MemeText} in the current meme creation session.
     *
     * @return the list of {@code MemeText} in the current meme creation session
     */
    ObservableList<MemeText> getMemeTextList();


    /**
     * Returns the image of the meme creation session, if any.
     *
     * @return the image of the meme creation session, or {@link Optional#empty} if there is none.
     */
    Optional<BufferedImage> getMemeCreationImage();

    /**
     * Returns the user prefs' Weme file path.
     */
    Path getWemeFilePath();

    /**
     * Returns the meme that the user wants to view.
     */
    ObservableValue<Meme> getViewableMeme();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns an unmodifiable view of like data.
     */
    ObservableMap<String, SimpleIntegerProperty> getObservableLikeData();

    /**
     * Returns an unmodifiable view of dislike data.
     */
    ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData();

    /**
     * Handles any logic that needs to be done before exiting Weme.
     */
    void cleanUp();
}
