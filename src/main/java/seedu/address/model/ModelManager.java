package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;
import seedu.address.model.game.Game;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;
import seedu.address.model.wordbanklist.WordBankList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WordBankList wordBankList;
    private final UserPrefs userPrefs;
    private final FilteredList<WordBank> filteredWordBank;

    //Placeholder game model
    private Game game = null;

    /**
     * Initializes a ModelManager with the given wordBank and userPrefs.
     */
    public ModelManager(ReadOnlyWordBankList wordBankList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(wordBankList, userPrefs);

        logger.fine("Initializing with word bank list: " + wordBankList + " and user prefs " + userPrefs);

        this.wordBankList = new WordBankList(wordBankList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredWordBank = new FilteredList<>(this.wordBankList.getWordBankList());
    }

    public ModelManager() {
        this(new WordBankList(), new UserPrefs());
    }

    // Placeholder setGame method
    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
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
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setWordBankFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== WordBankList ================================================================================

    @Override
    public void setWordBankList(ReadOnlyWordBankList wordBankList) {
        this.wordBankList.resetData(wordBankList);
    }

    @Override
    public ReadOnlyWordBankList getWordBankList() {
        return wordBankList;
    }

    @Override
    public boolean hasWordBank(WordBank wordBank) {
        requireNonNull(wordBank);
        return wordBankList.hasWordBank(wordBank);
    }

    @Override
    public void deleteWordBank(WordBank target) {
        wordBankList.removeCard(target);
    }

    @Override
    public void addWordBank(WordBank card) {
        wordBankList.addCard(card);
        updateFilteredWordBankList(PREDICATE_SHOW_ALL_WORDBANKS);
    }

    @Override
    public void setWordBankList(WordBank target, WordBank editedWordBank) {
        requireAllNonNull(target, editedWordBank);

        wordBankList.setWordBankList(target, editedWordBank);
    }

    //=========== Filtered Card List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Card} backed by the internal list of
     * {@code versionedAddressBook} // todo what is this versionedAddressBook?
     */
    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return filteredWordBank;
    }

    @Override
    public void updateFilteredWordBankList(Predicate<WordBank> predicate) {
        requireNonNull(predicate);
        filteredWordBank.setPredicate(predicate);
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
        return wordBankList.equals(other.wordBankList)
                && userPrefs.equals(other.userPrefs)
                && filteredWordBank.equals(other.filteredWordBank);
    }

}
