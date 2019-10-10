package seedu.address.model.answerable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAnswerables.ALICE;
import static seedu.address.testutil.TypicalAnswerables.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.answerable.exceptions.DuplicateAnswerableException;
import seedu.address.model.answerable.exceptions.AnswerableNotFoundException;
import seedu.address.testutil.AnswerableBuilder;

public class UniqueAnswerableListTest {

    private final UniqueAnswerableList uniqueAnswerableList = new UniqueAnswerableList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueAnswerableList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueAnswerableList.add(ALICE);
        assertTrue(uniqueAnswerableList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAnswerableList.add(ALICE);
        Answerable editedAlice = new AnswerableBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueAnswerableList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueAnswerableList.add(ALICE);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerable(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerable(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(AnswerableNotFoundException.class, () -> uniqueAnswerableList.setAnswerable(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueAnswerableList.add(ALICE);
        uniqueAnswerableList.setAnswerable(ALICE, ALICE);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(ALICE);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueAnswerableList.add(ALICE);
        Answerable editedAlice = new AnswerableBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueAnswerableList.setAnswerable(ALICE, editedAlice);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(editedAlice);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueAnswerableList.add(ALICE);
        uniqueAnswerableList.setAnswerable(ALICE, BOB);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(BOB);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueAnswerableList.add(ALICE);
        uniqueAnswerableList.add(BOB);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList.setAnswerable(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(AnswerableNotFoundException.class, () -> uniqueAnswerableList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueAnswerableList.add(ALICE);
        uniqueAnswerableList.remove(ALICE);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerables((UniqueAnswerableList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueAnswerableList.add(ALICE);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(BOB);
        uniqueAnswerableList.setAnswerables(expectedUniqueAnswerableList);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerables((List<Answerable>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueAnswerableList.add(ALICE);
        List<Answerable> answerableList = Collections.singletonList(BOB);
        uniqueAnswerableList.setAnswerables(answerableList);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(BOB);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Answerable> listWithDuplicateAnswerables = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList.setAnswerables(listWithDuplicateAnswerables));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueAnswerableList.asUnmodifiableObservableList().remove(0));
    }
}
