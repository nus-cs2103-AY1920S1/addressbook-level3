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
import seedu.address.model.DukeCooks;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.diary.Diary;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Diary validDiary = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validDiary).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validDiary), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDiary), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Diary validDiary = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validDiary);
        ModelStub modelStub = new ModelStubWithPerson(validDiary);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_DIARY, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Diary alice = new PersonBuilder().withName("Alice").build();
        Diary bob = new PersonBuilder().withName("Bob").build();
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

        // different diary -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public Path getDukeCooksFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDukeCooksFilePath(Path dukeCooksFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDiary(Diary diary) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDukeCooks(ReadOnlyDukeCooks dukeCooks) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDukeCooks getDukeCooks() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDiary(Diary diary) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDiary(Diary target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDiary(Diary target, Diary editedDiary) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Diary> getFilteredDiaryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDiaryList(Predicate<Diary> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single diary.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Diary diary;

        ModelStubWithPerson(Diary diary) {
            requireNonNull(diary);
            this.diary = diary;
        }

        @Override
        public boolean hasDiary(Diary diary) {
            requireNonNull(diary);
            return this.diary.isSameDiary(diary);
        }
    }

    /**
     * A Model stub that always accept the diary being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Diary> personsAdded = new ArrayList<>();

        @Override
        public boolean hasDiary(Diary diary) {
            requireNonNull(diary);
            return personsAdded.stream().anyMatch(diary::isSameDiary);
        }

        @Override
        public void addDiary(Diary diary) {
            requireNonNull(diary);
            personsAdded.add(diary);
        }

        @Override
        public ReadOnlyDukeCooks getDukeCooks() {
            return new DukeCooks();
        }
    }

}
