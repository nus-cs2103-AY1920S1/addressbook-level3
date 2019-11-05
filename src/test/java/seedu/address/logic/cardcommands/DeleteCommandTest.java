package seedu.address.logic.cardcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.cardcommands.AddCommand;
import seedu.address.logic.commands.cardcommands.DeleteCommand;
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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
class DeleteCommandTest {

    private Model model = new ModelManager();

    //    @Test
    //    void execute_validIndexUnfilteredList_success() {
    //        Card personToDelete = model.getFilteredCardList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
    //
    //        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CARD_SUCCESS, personToDelete);
    //
    //        ModelManager expectedModel = new ModelManager(model.toModelType(), new UserPrefs());
    //        expectedModel.deleteCard(personToDelete);
    //
    //        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    //    }
    //
    //        @Test
    //        public void execute_invalidIndexUnfilteredList_throwsCommandException() {
    //            Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
    //            DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //            assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    //        }

    //    @Test
    //    public void execute_validIndexFilteredList_success() {
    //        showCardAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Card personToDelete = model.getFilteredCardList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
    //
    //        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CARD_SUCCESS, personToDelete);
    //
    //        Model expectedModel = new ModelManager(model.toModelType(), new UserPrefs());
    //        expectedModel.deleteCard(personToDelete);
    //        showNoPerson(expectedModel);
    //
    //        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void execute_invalidIndexFilteredList_throwsCommandException() {
    //        showCardAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Index outOfBoundIndex = INDEX_SECOND_PERSON;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.toModelType().getCardList().size());
    //
    //        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    //    }

    //    @Test
    //    public void equals() {
    //        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
    //        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
    //
    //        // same object -> returns true
    //        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
    //
    //        // same values -> returns true
    //        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
    //        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
    //
    //        // different types -> returns false
    //        assertFalse(deleteFirstCommand.equals(1));
    //
    //        // null -> returns false
    //        assertFalse(deleteFirstCommand.equals(null));
    //
    //        // different person -> returns false
    //        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    //    }

    //    /**
    //     * Updates {@code model}'s filtered list to show no one.
    //     */
    //    private void showNoPerson(Model model) {
    //        model.updateFilteredCardList(p -> false);
    //
    //        assertTrue(model.getFilteredCardList().isEmpty());
    //    }
    //
    //    @Test
    //    void constructor_nullCard_throwsNullPointerException() {
    //        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    //    }
    //
    //    @Test
    //    void execute_cardAcceptedByModel_addSuccessful() throws Exception {
    //        ModelStubAcceptingCardDeleted modelStub = new ModelStubAcceptingCardDeleted();
    //        Card validCard = new CardBuilder().build();
    //
    //        CommandResult commandResult = new AddCommand(validCard).execute(modelStub);
    //
    //        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCard), commandResult.getFeedbackToUser());
    //        assertEquals(Arrays.asList(validCard), modelStub.cardsRemoved);
    //    }
    //
    //    @Test
    //    void execute_duplicateCard_throwsCommandException() {
    //        Card validCard = new CardBuilder().build();
    //        AddCommand addCommand = new AddCommand(validCard);
    //        ModelStub modelStub = new ModelStubWithCard(validCard);
    //
    //        assertThrows(CommandException.class, Messages.MESSAGE_DUPLICATE_CARD, () -> addCommand.execute(modelStub));
    //    }

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
        public FormattedHint getHintFormatFromCurrentGame() {
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
     * A Model stub that always accept the card being added.
     */
    private class ModelStubAcceptingCardDeleted extends ModelStub {
        final ArrayList<Card> cardsRemoved = new ArrayList<>();

        @Override
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return cardsRemoved.stream().anyMatch(card::isSameMeaning);
        }

        @Override
        public void deleteCard(Card card) {
            requireNonNull(card);
            cardsRemoved.remove(card);
        }

        @Override
        public ReadOnlyWordBank getCurrentWordBank() {
            return new WordBank("abc");
        }
    }
}
