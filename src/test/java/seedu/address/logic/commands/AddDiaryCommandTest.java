package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DiaryRecords;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyDiary;
import seedu.address.model.diary.Diary;
import seedu.address.testutil.DiaryBuilder;

public class AddDiaryCommandTest {

    @Test
    public void constructor_nullDiary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddDiaryCommand(null));
    }

    @Test
    public void execute_diaryAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDiaryAdded modelStub = new ModelStubAcceptingDiaryAdded();
        Diary validDiary = new DiaryBuilder().build();

        CommandResult commandResult = new AddDiaryCommand(validDiary).execute(modelStub);

        assertEquals(String.format(AddDiaryCommand.MESSAGE_SUCCESS, validDiary), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDiary), modelStub.diariesAdded);
    }

    @Test
    public void execute_duplicateDiary_throwsCommandException() {
        Diary validDiary = new DiaryBuilder().build();
        AddDiaryCommand addCommand = new AddDiaryCommand(validDiary);
        ModelStub modelStub = new ModelStubWithDiary(validDiary);

        assertThrows(CommandException.class,
                AddDiaryCommand.MESSAGE_DUPLICATE_DIARY, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Diary alice = new DiaryBuilder().withName("Alice").build();
        Diary bob = new DiaryBuilder().withName("Bob").build();
        AddDiaryCommand addAliceCommand = new AddDiaryCommand(alice);
        AddDiaryCommand addBobCommand = new AddDiaryCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddDiaryCommand addAliceCommandCopy = new AddDiaryCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different diaries -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }


    /**
     * A Model stub that contains a single diary.
     */
    private class ModelStubWithDiary extends ModelStub {
        private final Diary diary;

        ModelStubWithDiary(Diary diary) {
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
    private class ModelStubAcceptingDiaryAdded extends ModelStub {
        final ArrayList<Diary> diariesAdded = new ArrayList<>();

        @Override
        public boolean hasDiary(Diary diary) {
            requireNonNull(diary);
            return diariesAdded.stream().anyMatch(diary::isSameDiary);
        }

        @Override
        public void addDiary(Diary diary) {
            requireNonNull(diary);
            diariesAdded.add(diary);
        }

        @Override
        public ReadOnlyDiary getDiaryRecords() {
            return new DiaryRecords();
        }
    }

}
