package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.game.Game;
import seedu.address.model.gamedifficulty.DifficultyEnum;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.globalstatistics.JsonGlobalStatisticsStorage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private WordBank wordBank = new WordBank("Empty wordbank");
    private final WordBankList wordBankList;

    private WordBankStatistics wordBankStatistics;
    private final WordBankStatisticsList wordBankStatisticsList;

    private final GlobalStatistics globalStatistics;

    private final UserPrefs userPrefs;
    private FilteredList<Card> filteredCards;
    private final FilteredList<WordBank> filteredWordBanks;

    //Placeholder game model
    private Game game = null;
    private DifficultyEnum difficulty;

    /**
     * Initializes a ModelManager with the given wordBank and userPrefs.
     */
    public ModelManager(WordBankList wordBankList, WordBankStatisticsList wbStatsList,
                        GlobalStatistics globalStatistics, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(wordBankList, userPrefs);

        logger.fine("Initializing with word bank list: " + wordBankList + " and user prefs " + userPrefs);

        this.wordBankList = wordBankList;
        this.wordBankStatisticsList = wbStatsList;
        this.globalStatistics = globalStatistics;

        this.userPrefs = new UserPrefs(userPrefs);
        filteredWordBanks = new FilteredList<>(this.wordBankList.getWordBankList());

        // Default Difficulty is always EASY.
        this.difficulty = DifficultyEnum.EASY;
    }

    public ModelManager() {
        this(new WordBankList(Collections.emptyList()),
                new WordBankStatisticsList(Collections.emptyList()),
                new GlobalStatistics(),
                new UserPrefs());
    }

    // Placeholder setGame method
    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    @Override
    public void setDifficulty(DifficultyEnum difficultyEnum) {
        this.difficulty = difficultyEnum;
    }

    @Override
    public DifficultyEnum getDifficulty() {
        return difficulty;
    }

    @Override
    public long getTimeAllowedPerQuestion() {
        return getDifficulty().getTimeAllowedPerQuestion();
    }

    @Override
    public FormattedHint getHintFormatFromCurrentGame() throws UnsupportedOperationException {
        if (game == null || game.isOver()) {
            throw new UnsupportedOperationException("No active game session to send hints from");
        }
        return game.getHintFormatForCurrCard();
    }

    @Override
    public int getHintFormatSizeFromCurrentGame() {
        return game.getHintFormatSizeOfCurrCard();
    }

    @Override
    public boolean hintsAreEnabled() {
        return difficulty.hintsAreEnabled();
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

    @Override
    public Path getWordBankFilePath() {
        return userPrefs.getDataFilePath();
    }

    @Override
    public void setWordBankFilePath(Path filePath) {
        requireNonNull(filePath);
        userPrefs.setDataFilePath(filePath);
    }

    //=========== WordBank ================================================================================

    @Override
    public void setWordBank(ReadOnlyWordBank wordBank) {
        this.wordBank = (WordBank) wordBank;
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
        //        this.wordBank.resetData(wordBank);
    }

    /**
     * Clears the WordBank.
     */
    public void clearWordBank() {
        wordBank.resetData(new WordBank(wordBank.getName()));
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
        //        this.wordBank.resetData(wordBank);
    }

    @Override
    public ReadOnlyWordBank getWordBank() {
        return wordBank;
    }

    public void removeWordBank() {
        this.wordBank = new WordBank("Empty wordbank");
    }


    @Override
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return wordBank.hasCard(card);
    }

    @Override
    public void deleteCard(Card target) {
        wordBank.removeCard(target);
        wordBankStatistics.removeCardStatistics(target.getId());
    }

    @Override
    public void addCard(Card card) {
        wordBank.addCard(card);
        updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
    }

    @Override
    public void setCard(Card target, Card editedCard) {
        requireAllNonNull(target, editedCard);
        wordBank.setCard(target, editedCard);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedAddressBook} // todo what is this versionedAddressBook?
     */
    @Override
    public ObservableList<Card> getFilteredCardList() {
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
        return filteredCards;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedAddressBook} // todo what is this versionedAddressBook?
     */
    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return filteredWordBanks;
    }

    @Override
    public WordBankList getWordBankList() {
        return wordBankList;
    }

    @Override
    public void updateFilteredCardList(Predicate<Card> predicate) {
        requireNonNull(predicate);

        filteredCards.setPredicate(predicate);
        filteredCards = new FilteredList<>(this.wordBank.getCardList());
    }

    //=========== WordBankStatistics methods =============================================================

    @Override
    public WordBankStatisticsList getWordBankStatisticsList() {
        return wordBankStatisticsList;
    }

    @Override
    public WordBankStatistics getWordBankStatistics() {
        return this.wordBankStatistics;
    }

    @Override
    public void setWordBankStatistics(WordBankStatistics wordBankStats) {
        this.wordBankStatistics = wordBankStats;
    }

    @Override
    public void clearWordBankStatistics() {
        this.wordBankStatistics = null;
    }

    @Override
    public GlobalStatistics getGlobalStatistics() {
        return globalStatistics;
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
        return wordBank.equals(other.wordBank)
                && userPrefs.equals(other.userPrefs)
                && filteredCards.equals(other.filteredCards);
    }
}
