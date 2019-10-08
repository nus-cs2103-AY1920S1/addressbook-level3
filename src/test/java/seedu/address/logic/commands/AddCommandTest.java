package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.card.Card;
import seedu.address.model.game.Game;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.testutil.CardBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_CardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();
        Card validCard = new CardBuilder().build();

        CommandResult commandResult = new AddCommand(validCard).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCard), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCard), modelStub.CardsAdded);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() {
        Card validCard = new CardBuilder().build();
        AddCommand addCommand = new AddCommand(validCard);
        ModelStub modelStub = new ModelStubWithCard(validCard);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_CARD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Card abra = new CardBuilder().withName("Abra").build();
        Card butterfree = new CardBuilder().withName("Butterfree").build();
        AddCommand addAbraCommand = new AddCommand(abra);
        AddCommand addButterfreeCommand = new AddCommand(butterfree);

        // same object -> returns true
        assertEquals(addAbraCommand, addAbraCommand);

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(abra);
        assertEquals(addAbraCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAbraCommand);

        // null -> returns false
        assertNotEquals(null, addAbraCommand);

        // different Card -> returns false
        assertNotEquals(addAbraCommand, addButterfreeCommand);
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
        public void addCard(Card Card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setWordBank(ReadOnlyWordBank newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWordBank getWordBank() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCard(Card Card) {
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
        public void updateFilteredCardList(Predicate<Card> predicate) {
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
            return this.card.isSameName(card);
        }
    }

    /**
     * A Model stub that always accept the card being added.
     */
    private class ModelStubAcceptingCardAdded extends ModelStub {
        final ArrayList<Card> CardsAdded = new ArrayList<>();

        @Override
        public boolean hasCard(Card Card) {
            requireNonNull(Card);
            return CardsAdded.stream().anyMatch(Card::isSameName);
        }

        @Override
        public void addCard(Card Card) {
            requireNonNull(Card);
            CardsAdded.add(Card);
        }

        @Override
        public ReadOnlyWordBank getWordBank() {
            return new WordBank();
        }
    }
}
