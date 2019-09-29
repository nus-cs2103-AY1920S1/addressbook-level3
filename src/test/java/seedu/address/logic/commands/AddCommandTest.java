package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WordBank;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyWordBank;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

public class AddCommandTest {

//    @Test
//    public void constructor_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new AddCommand(null));
//    }
//
//    @Test
//    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
//        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
//        Card validPerson = new CardBuilder().build();
//
//        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);
//
//        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
//        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
//    }
//
//    @Test
//    public void execute_duplicatePerson_throwsCommandException() {
//        Card validPerson = new CardBuilder().build();
//        AddCommand addCommand = new AddCommand(validPerson);
//        ModelStub modelStub = new ModelStubWithPerson(validPerson);
//
//        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
//    }
//
//    @Test
//    public void equals() {
//        Card alice = new CardBuilder().withName("Alice").build();
//        Card bob = new CardBuilder().withName("Bob").build();
//        AddCommand addAliceCommand = new AddCommand(alice);
//        AddCommand addBobCommand = new AddCommand(bob);
//
//        // same object -> returns true
//        assertTrue(addAliceCommand.equals(addAliceCommand));
//
//        // same values -> returns true
//        AddCommand addAliceCommandCopy = new AddCommand(alice);
//        assertTrue(addAliceCommand.equals(addAliceCommandCopy));
//
//        // different types -> returns false
//        assertFalse(addAliceCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(addAliceCommand.equals(null));
//
//        // different person -> returns false
//        assertFalse(addAliceCommand.equals(addBobCommand));
//    }
//
//    /**
//     * A default model stub that have all of the methods failing.
//     */
//    private class ModelStub implements Model {
//        @Override
//        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyUserPrefs getUserPrefs() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public GuiSettings getGuiSettings() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setGuiSettings(GuiSettings guiSettings) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public Path getWordBankFilePath() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setWordBankFilePath(Path addressBookFilePath) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void addCard(Card person) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setWordBank(ReadOnlyWordBank newData) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ReadOnlyWordBank getWordBank() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public boolean hasCard(Card person) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void deleteCard(Card target) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void setCard(Card target, Card editedPerson) {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public ObservableList<Card> getFilteredCardList() {
//            throw new AssertionError("This method should not be called.");
//        }
//
//        @Override
//        public void updateFilteredCardList(Predicate<Card> predicate) {
//            throw new AssertionError("This method should not be called.");
//        }
//    }
//
//    /**
//     * A Model stub that contains a single person.
//     */
//    private class ModelStubWithPerson extends ModelStub {
//        private final Card person;
//
//        ModelStubWithPerson(Card person) {
//            requireNonNull(person);
//            this.person = person;
//        }
//
//        @Override
//        public boolean hasCard(Card person) {
//            requireNonNull(person);
//            return this.person.isSamePerson(person);
//        }
//    }
//
//    /**
//     * A Model stub that always accept the person being added.
//     */
//    private class ModelStubAcceptingPersonAdded extends ModelStub {
//        final ArrayList<Card> personsAdded = new ArrayList<>();
//
//        @Override
//        public boolean hasCard(Card person) {
//            requireNonNull(person);
//            return personsAdded.stream().anyMatch(person::isSamePerson);
//        }
//
//        @Override
//        public void addCard(Card person) {
//            requireNonNull(person);
//            personsAdded.add(person);
//        }
//
//        @Override
//        public ReadOnlyWordBank getWordBank() {
//            return new WordBank();
//        }
//    }

}
