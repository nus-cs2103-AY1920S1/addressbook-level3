package seedu.address.model.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHEATSHEET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_MATH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCheatSheets.CS6;
import static seedu.address.testutil.TypicalCheatSheets.CS7;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.cheatsheet.exceptions.CheatSheetNotFoundException;
import seedu.address.model.cheatsheet.exceptions.DuplicateCheatSheetException;
import seedu.address.testutil.CheatSheetBuilder;

public class UniqueCheatSheetListTest {
    private final UniqueCheatSheetList uniqueCheatSheetList = new UniqueCheatSheetList();

    @Test
    public void contains_nullCheatSheet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCheatSheetList.contains(null));
    }

    @Test
    public void contains_cheatSheetNotInList_returnsFalse() {
        assertFalse(uniqueCheatSheetList.contains(CS6));
    }

    @Test
    public void contains_cheatSheetInList_returnsTrue() {
        uniqueCheatSheetList.add(CS6);
        assertTrue(uniqueCheatSheetList.contains(CS6));
    }

    @Test
    public void contains_cheatSheetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCheatSheetList.add(CS6);
        CheatSheet editedCs6 = new CheatSheetBuilder(CS6)
                .withTitle(VALID_TITLE_MATH)
                .withTags(VALID_TAG_CHEATSHEET)
                .build();
        assertTrue(uniqueCheatSheetList.contains(editedCs6));
    }

    @Test
    public void add_nullCheatSheet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCheatSheetList.add(null));
    }

    @Test
    public void add_duplicateCheatSheet_throwsDuplicateCheatSheetException() {
        uniqueCheatSheetList.add(CS6);
        assertThrows(DuplicateCheatSheetException.class, () -> uniqueCheatSheetList.add(CS6));
    }

    @Test
    public void setCheatSheet_nullTargetCheatSheet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
                ) -> uniqueCheatSheetList.setCheatSheet(null, CS6));
    }

    @Test
    public void setCheatSheet_nullEditedCheatSheet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
                ) -> uniqueCheatSheetList.setCheatSheet(CS6, null));
    }

    @Test
    public void setCheatSheet_targetCheatSheetNotInList_throwsCheatSheetNotFoundException() {
        assertThrows(CheatSheetNotFoundException.class, () -> uniqueCheatSheetList.setCheatSheet(CS6, CS6));
    }

    @Test
    public void setCheatSheet_editedCheatSheetIsSameCheatSheet_success() {
        uniqueCheatSheetList.add(CS6);
        uniqueCheatSheetList.setCheatSheet(CS6, CS6);
        UniqueCheatSheetList expectedUniqueCheatSheetList = new UniqueCheatSheetList();
        expectedUniqueCheatSheetList.add(CS6);
        assertEquals(expectedUniqueCheatSheetList, uniqueCheatSheetList);
    }

    @Test
    public void setCheatSheet_editedCheatSheetHasSameIdentity_success() {
        uniqueCheatSheetList.add(CS6);
        CheatSheet editedCS6 = new CheatSheetBuilder(CS6)
                .withTitle(VALID_TITLE_MATH)
                .withTags(VALID_TAG_CHEATSHEET)
                .build();
        uniqueCheatSheetList.setCheatSheet(CS6, editedCS6);
        UniqueCheatSheetList expectedUniqueCheatSheetList = new UniqueCheatSheetList();
        expectedUniqueCheatSheetList.add(editedCS6);
        assertEquals(expectedUniqueCheatSheetList, uniqueCheatSheetList);
    }

    @Test
    public void setCheatSheet_editedCheatSheetHasDifferentIdentity_success() {
        uniqueCheatSheetList.add(CS6);
        uniqueCheatSheetList.setCheatSheet(CS6, CS7);
        UniqueCheatSheetList expectedUniqueCheatSheetList = new UniqueCheatSheetList();
        expectedUniqueCheatSheetList.add(CS7);
        assertEquals(expectedUniqueCheatSheetList, uniqueCheatSheetList);
    }

    @Test
    public void setCheatSheet_editedCheatSheetHasNonUniqueIdentity_throwsDuplicateCheatSheetException() {
        uniqueCheatSheetList.add(CS6);
        uniqueCheatSheetList.add(CS7);
        assertThrows(DuplicateCheatSheetException.class, () -> uniqueCheatSheetList.setCheatSheet(CS6, CS7));
    }

    @Test
    public void remove_nullCheatSheet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCheatSheetList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(CheatSheetNotFoundException.class, () -> uniqueCheatSheetList.remove(CS6));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueCheatSheetList.add(CS6);
        uniqueCheatSheetList.remove(CS6);
        UniqueCheatSheetList expectedUniqueCheatSheetList = new UniqueCheatSheetList();
        assertEquals(expectedUniqueCheatSheetList, uniqueCheatSheetList);
    }

    @Test
    public void setCheatSheets_nullUniqueCheatSheetList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
                ) -> uniqueCheatSheetList.setCheatSheets((UniqueCheatSheetList) null));
    }

    @Test
    public void setCheatSheets_uniqueCheatSheetList_replacesOwnListWithProvidedUniqueCheatSheetList() {
        uniqueCheatSheetList.add(CS6);
        UniqueCheatSheetList expectedUniquePersonList = new UniqueCheatSheetList();
        expectedUniquePersonList.add(CS7);
        uniqueCheatSheetList.setCheatSheets(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueCheatSheetList);
    }

    @Test
    public void setCheatSheets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
                ) -> uniqueCheatSheetList.setCheatSheets((List<CheatSheet>) null));
    }

    @Test
    public void setCheatSheets_list_replacesOwnListWithProvidedList() {
        uniqueCheatSheetList.add(CS6);
        List<CheatSheet> personList = Collections.singletonList(CS7);
        uniqueCheatSheetList.setCheatSheets(personList);
        UniqueCheatSheetList expectedUniqueCheatSheetList = new UniqueCheatSheetList();
        expectedUniqueCheatSheetList.add(CS7);
        assertEquals(expectedUniqueCheatSheetList, uniqueCheatSheetList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<CheatSheet> listWithDuplicateCheatSheets = Arrays.asList(CS6, CS6);
        assertThrows(
                DuplicateCheatSheetException.class, (
                        ) -> uniqueCheatSheetList.setCheatSheets(listWithDuplicateCheatSheets));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, (
                        ) -> uniqueCheatSheetList.asUnmodifiableObservableList().remove(0));
    }
}
