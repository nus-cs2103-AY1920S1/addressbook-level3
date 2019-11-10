package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.appsettings.ThemeEnum;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.game.Game;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;

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
    void setCurrentWordBank(ReadOnlyWordBank currentWordBank);

    /**
     * Set the word bank to default if the removed.
     */
    void updateWordBank(String name);

    /**
     * Returns the active word bank statistics. Null if no active.
     */
    WordBankStatistics getWordBankStatistics();

    /**
     * Replaces word bank stats data with the data in {@code wordBankStats}.
     */
    void setWordBankStatistics(WordBankStatistics wordBankStats);

    /**
     * Resets the word bank statistics data
     */
    void clearWordBankStatistics();

    /**
     * Resets the word bank data to be empty.
     */
    void clearWordBank();

    /**
     * Returns the WordBank
     */
    ReadOnlyWordBank getCurrentWordBank();

    /**
     * Returns true if a card with the same name as {@code card} exists in the word bank.
     */
    boolean hasCard(Card card);

    /**
     * Deletes the given card.
     * The card must exist in the word bank.
     */
    void deleteCard(Card target);

    /**
     * Adds the given card.
     * {@code card} must not already exist in the word bank.
     */
    void addCard(Card card);

    /**
     * Replaces the given card {@code target} with {@code editedCard}.
     * {@code target} must exist in the word bank.
     * The card name of {@code editedCard} must not be the same as another existing card in the word bank.
     */
    void setCard(Card target, Card editedCard);

    /**
     * Returns an unmodifiable view of the filtered card list
     */
    ObservableList<Card> getFilteredCardList();

    WordBankList getWordBankList();

    WordBankStatisticsList getWordBankStatisticsList();

    void clearActiveWordBankStatistics();

    GlobalStatistics getGlobalStatistics();

    /**
     * Updates the filter of the filtered card list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCardList(Predicate<Card> predicate);

    void setGame(Game game);

    boolean gameIsOver();

    boolean getHasBank();

    Game getGame();

    boolean hasWordBank(String name);

    WordBank getWordBankFromName(String name);

    DifficultyEnum getCurrentGameDifficulty();

    /**
     * Returns the app settings
     */
    AppSettings getAppSettings();

    /**
     * Returns the app settings file path.
     */
    Path getAppSettingsFilePath();

    /**
     * Replaces the default difficulty of the game with {@code difficultyEnum}.
     *
     * @param difficultyEnum The new default difficulty of the game.
     */
    void setDefaultDifficulty(DifficultyEnum difficultyEnum);

    /**
     * Returns the current default difficulty of the game.
     */
    DifficultyEnum getDefaultDifficulty();

    /**
     * Replaces the default theme of the game with {@code difficultyEnum}.
     *
     * @param themeEnum The new default theme of the game.
     */
    void setDefaultTheme(ThemeEnum themeEnum);

    /**
     * Returns the current default theme of the game.
     */
    ThemeEnum getDefaultTheme();

    /**
     * Sets the setting if hints are enabled or not.
     */
    void setHintsEnabled(boolean enabled);

    /**
     * Returns if hints are enabled or not.
     */
    boolean getHintsEnabled();

    void setAvatarId(int avatarId);

    int getAvatarId();

    long getTimeAllowedPerQuestion();

    FormattedHint getFormattedHintFromCurrGame();

    int getHintFormatSizeFromCurrentGame();

}
