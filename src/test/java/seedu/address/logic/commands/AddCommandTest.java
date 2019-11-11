package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyKeyboardFlashCards;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.category.Category;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.FlashCardBuilder;
import seedu.address.ui.TestFlashCardPanel;

public class AddCommandTest {

    @Test
    public void constructor_nullFlashCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_flashCardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFlashCardAdded modelStub = new ModelStubAcceptingFlashCardAdded();
        FlashCard validFlashCard = new FlashCardBuilder().build();

        CommandResult commandResult = new AddCommand(validFlashCard).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFlashCard), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFlashCard), modelStub.flashCardsAdded);
    }

    @Test
    public void execute_duplicateFlashCard_throwsCommandException() {
        FlashCard validFlashCard = new FlashCardBuilder().build();
        AddCommand addCommand = new AddCommand(validFlashCard);
        ModelStub modelStub = new ModelStubWithFlashCard(validFlashCard);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_FLASHCARD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        FlashCard alice = new FlashCardBuilder().withQuestion("Alice").build();
        FlashCard bob = new FlashCardBuilder().withQuestion("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different flashCard -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));

        //same question different answer
        FlashCard aliceCopy = new FlashCardBuilder(alice).withAnswer("123098").build();
        addAliceCommandCopy = new AddCommand(aliceCopy);
        assertFalse(addAliceCommand.equals(addAliceCommandCopy));

        //same question different category
        aliceCopy = new FlashCardBuilder(alice).withCatgeories("1234123").build();
        addAliceCommandCopy = new AddCommand(aliceCopy);
        assertFalse(addAliceCommand.equals(addAliceCommandCopy));

        //same question different rating
        aliceCopy = new FlashCardBuilder(alice).withRating("hard").build();
        addAliceCommandCopy = new AddCommand(aliceCopy);
        assertFalse(addAliceCommand.equals(addAliceCommandCopy));

        //diff question others the same
        aliceCopy = new FlashCardBuilder(alice).withQuestion("sky").build();
        addAliceCommandCopy = new AddCommand(aliceCopy);
        assertFalse(addAliceCommand.equals(addAliceCommandCopy));
    }

    @Test
    public void toStringTest() {
        FlashCard validFlashCard = new FlashCardBuilder().build();
        AddCommand addCommand = new AddCommand(validFlashCard);
        //same object
        assertTrue(addCommand.toString().equals(addCommand.toString()));

        //same value
        AddCommand addComandCopy = new AddCommand(validFlashCard);
        assertTrue(addCommand.toString().equals(addComandCopy.toString()));

        //same question diff answer
        addComandCopy = new AddCommand(new FlashCardBuilder(validFlashCard).withAnswer("wrong").build());
        assertFalse(addCommand.toString().equals(addComandCopy.toString()));
    }
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public void setStyleSheet(String string) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getStyleSheet() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getKeyboardFlashCardsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setKeyboardFlashCardsFilePath(Path keyboardFlashCardsFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFlashCard(FlashCard flashCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setKeyboardFlashCards(ReadOnlyKeyboardFlashCards newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyKeyboardFlashCards getKeyboardFlashCards() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashcard(FlashCard flashCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashCard(FlashCard target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeadline(Deadline deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeadline(Deadline deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeadline(Deadline deadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashCard(FlashCard target, FlashCard editedFlashCard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<FlashCard> getFilteredFlashCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFlashCardList(Predicate<FlashCard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author shutingy
        @Override
        public ObservableList<Category> getCategoryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCategoryList(Predicate<Category> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeadline(Deadline target, Deadline editedDeadline) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Deadline> getFilteredDeadlineList() {
            return null;
        }

        @Override
        public void updateFilteredDeadlineList(Predicate<Deadline> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        //@@author LeonardTay748
        @Override
        public int[] getTestStats() {
            throw new AssertionError("This method should not be called.");
        }

        public void editStats(int type) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<Float> getPerformance() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerformance(Model model) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetPerformance(Model model) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author keiteo-reused
        @Override
        public void initializeTestModel(List<FlashCard> testList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTestFlashCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTestFlashCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getTestQuestion() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getTestAnswer() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TestFlashCardPanel getTestFlashCardPanel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<FlashCard> getFlashCardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FlashCard getCurrentTestFlashCard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void showAnswer() {
            throw new AssertionError("This method should not be called.");
        }

        //@@author LeowWB
        public ObservableList<FlashCard> getFilteredFlashCardListNoCommit(Predicate<FlashCard> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    //@@author
    /**
     * A Model stub that contains a single flashCard.
     */
    private class ModelStubWithFlashCard extends ModelStub {
        private final FlashCard flashCard;

        ModelStubWithFlashCard(FlashCard flashCard) {
            requireNonNull(flashCard);
            this.flashCard = flashCard;
        }

        @Override
        public boolean hasFlashcard(FlashCard flashCard) {
            requireNonNull(flashCard);
            return this.flashCard.isSameFlashCard(flashCard);
        }
    }

    /**
     * A Model stub that always accept the flashCard being added.
     */
    private class ModelStubAcceptingFlashCardAdded extends ModelStub {
        final ArrayList<FlashCard> flashCardsAdded = new ArrayList<>();

        @Override
        public boolean hasFlashcard(FlashCard flashCard) {
            requireNonNull(flashCard);
            return flashCardsAdded.stream().anyMatch(flashCard::isSameFlashCard);
        }

        @Override
        public void addFlashCard(FlashCard flashCard) {
            requireNonNull(flashCard);
            flashCardsAdded.add(flashCard);
        }

        @Override
        public ReadOnlyKeyboardFlashCards getKeyboardFlashCards() {
            return new KeyboardFlashCards();
        }
    }
}
