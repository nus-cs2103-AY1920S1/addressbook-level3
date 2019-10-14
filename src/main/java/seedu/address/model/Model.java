package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.card.Card;
import seedu.address.model.game.Game;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;
    Predicate<WordBank> PREDICATE_SHOW_ALL_WORDBANKS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' word bank file path.
     */
    Path getWordBankFilePath();

    /**
     * Sets the user prefs' word bank file path.
     */
    void setWordBankFilePath(Path addressBookFilePath);

    /**
     * Replaces word bank data with the data in {@code wordBank}.
     */
    void setWordBankList(ReadOnlyWordBankList wordBankList);

    /** Returns the WordBank */
    ReadOnlyWordBankList getWordBankList();

    /**
     * Returns true if a card with the same name as {@code card} exists in the word bank.
     */
    boolean hasWordBank(WordBank wordBank);

    /**
     * Deletes the given card.
     * The card must exist in the word bank.
     */
    void deleteWordBank(WordBank target);

    /**
     * Adds the given card.
     * {@code card} must not already exist in the word bank.
     */
    void addWordBank(WordBank card);

    /**
     * Replaces the given card {@code target} with {@code editedCard}.
     * {@code target} must exist in the word bank.
     * The card name of {@code editedCard} must not be the same as another existing card in the word bank.
     */
    void setWordBankList(WordBank target, WordBank editedwordBank);

    /** Returns an unmodifiable view of the filtered card list */
    ObservableList<WordBank> getFilteredWordBankList();

    /**
     * Updates the filter of the filtered card list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWordBankList(Predicate<WordBank> predicate);

    void setGame(Game game);

    Game getGame();
}
