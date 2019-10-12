package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalDukeCooks;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.exceptions.DuplicateDiaryException;
import seedu.address.testutil.PersonBuilder;

public class DukeCooksTest {

    private final DukeCooks dukeCooks = new DukeCooks();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), dukeCooks.getDiaryList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dukeCooks.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDukeCooks_replacesData() {
        DukeCooks newData = getTypicalDukeCooks();
        dukeCooks.resetData(newData);
        assertEquals(newData, dukeCooks);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two diaries with the same identity fields
        Diary editedAlice = new PersonBuilder(ALICE).build();
        List<Diary> newDiaries = Arrays.asList(ALICE, editedAlice);
        DukeCooksStub newData = new DukeCooksStub(newDiaries);

        assertThrows(DuplicateDiaryException.class, () -> dukeCooks.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> dukeCooks.hasDiary(null));
    }

    @Test
    public void hasPerson_personNotInDukeCooks_returnsFalse() {
        assertFalse(dukeCooks.hasDiary(ALICE));
    }

    @Test
    public void hasPerson_personInDukeCooks_returnsTrue() {
        dukeCooks.addDiary(ALICE);
        assertTrue(dukeCooks.hasDiary(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInDukeCooks_returnsTrue() {
        dukeCooks.addDiary(ALICE);
        Diary editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(dukeCooks.hasDiary(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> dukeCooks.getDiaryList().remove(0));
    }

    /**
     * A stub ReadOnlyDukeCooks whose diaries list can violate interface constraints.
     */
    private static class DukeCooksStub implements ReadOnlyDukeCooks {
        private final ObservableList<Diary> diaries = FXCollections.observableArrayList();

        DukeCooksStub(Collection<Diary> diaries) {
            this.diaries.setAll(diaries);
        }

        @Override
        public ObservableList<Diary> getDiaryList() {
            return diaries;
        }
    }

}
