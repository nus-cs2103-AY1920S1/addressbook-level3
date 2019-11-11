package seedu.address.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CARD;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyUserPrefs;
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
import seedu.address.testutil.CardBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
class DeleteCommandTest {

    private Model model = new ModelManager();

    @Test
    void execute_invalidIndex_throwsCommandException() {
        Card abra = new CardBuilder().withWord("Abra").build();
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_THIRD_CARD);
        ModelStubAcceptingCardDeleted modelStub = new ModelStubAcceptingCardDeleted(abra);

        assertCommandFailure(deleteCommand, modelStub, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX, () -> deleteCommand.execute(modelStub));
    }

    @Test
    void execute_validIndexUnfilteredList_success() {
        Card abra = new CardBuilder().withWord("Abra").build();
        ModelStubAcceptingCardDeleted modelStub = new ModelStubAcceptingCardDeleted(abra);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CARD);
        Card cardToDelete = modelStub.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        ModelStubAcceptingCardDeleted expectedModel = new ModelStubAcceptingCardDeleted();

        assertCommandSuccess(deleteCommand, modelStub, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_CARD);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_CARD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    void constructor_nullCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteCommand(null));
    }

    @Test
    void execute_cardAcceptedByModel_deleteSuccessful() throws Exception {
        Card abra = new CardBuilder().withWord("Abra").build();
        ModelStubAcceptingCardDeleted modelStub = new ModelStubAcceptingCardDeleted(abra);

        CommandResult commandResult = new DeleteCommand(INDEX_FIRST_CARD).execute(modelStub);

        assertEquals(String.format(DeleteCommand.MESSAGE_DELETE_CARD_SUCCESS, abra), commandResult.getFeedbackToUser());

        showNoPerson(modelStub);

        ObservableList<Card> cardList = FXCollections.observableArrayList();
        assertEquals(cardList, modelStub.cardList);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        assertTrue(model.getFilteredCardList().isEmpty());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void setGame(Game game) {
        }

        public Game getGame() {
            return null;
        }

        @Override
        public boolean hasWordBank(String name) {
            return false;
        }

        @Override
        public WordBank getWordBankFromName(String name) {
            return null;
        }

        @Override
        public boolean gameIsOver() {
            return false;
        }

        @Override
        public boolean getHasBank() {
            return false;
        }

        @Override
        public AppSettings getAppSettings() {
            return null;
        }

        @Override
        public Path getAppSettingsFilePath() {
            return null;
        }

        @Override
        public void setDefaultDifficulty(DifficultyEnum difficultyEnum) {

        }

        @Override
        public DifficultyEnum getDefaultDifficulty() {
            return null;
        }

        @Override
        public DifficultyEnum getCurrentGameDifficulty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDefaultTheme(ThemeEnum themeEnum) {

        }

        @Override
        public ThemeEnum getDefaultTheme() {
            return null;
        }

        @Override
        public void setHintsEnabled(boolean enabled) {

        }

        @Override
        public long getTimeAllowedPerQuestion() {
            return 0;
        }

        @Override
        public FormattedHint getFormattedHintFromCurrGame() {
            return null;
        }

        @Override
        public int getHintFormatSizeFromCurrentGame() {
            return 0;
        }

        @Override
        public boolean getHintsEnabled() {
            return false;
        }

        @Override
        public void setAvatarId(int avatarId) {

        }

        @Override
        public int getAvatarId() {
            return 0;
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getWordBankFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWordBankFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearActiveWordBankStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentWordBank(ReadOnlyWordBank newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateWordBank(String name) {

        }

        @Override
        public WordBankStatistics getWordBankStatistics() {
            return null;
        }

        @Override
        public void setWordBankStatistics(WordBankStatistics wordBankStats) {

        }

        @Override
        public void clearWordBankStatistics() {

        }

        @Override
        public void clearWordBank() {

        }

        @Override
        public ReadOnlyWordBank getCurrentWordBank() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCard(Card target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCard(Card target, Card editedCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public WordBankList getWordBankList() {
            return null;
        }

        @Override
        public WordBankStatisticsList getWordBankStatisticsList() {
            return null;
        }

        @Override
        public void updateFilteredCardList(Predicate<Card> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GlobalStatistics getGlobalStatistics() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single card.
     */
    private class ModelStubWithCard extends ModelStub {
        private final Card card;

        ModelStubWithCard(Card card) {
            requireNonNull(card);
            this.card = card;
        }

        @Override
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return this.card.isSameMeaning(card);
        }
    }

    /**
     * A Model stub that always delete the card being deleted.
     */
    private class ModelStubAcceptingCardDeleted extends ModelStub {

        private ObservableList<Card> cardList = FXCollections.observableArrayList();

        private Card card;

        ModelStubAcceptingCardDeleted(Card card) {
            requireNonNull(card);
            this.card = card;
            cardList.add(card);
        }

        ModelStubAcceptingCardDeleted() {
        }

        @Override
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return cardList.stream().anyMatch(card::isSameMeaning);
        }

        @Override
        public void deleteCard(Card card) {
            requireNonNull(card);
            cardList.remove(card);
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            return cardList;
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(obj instanceof ModelStubAcceptingCardDeleted)) {
                return false;
            }

            // state check
            ModelStubAcceptingCardDeleted other = (ModelStubAcceptingCardDeleted) obj;
            return cardList.equals(other.cardList);
        }

        public ObservableList<Card> getCardList() {
            return cardList;
        }
    }
}
