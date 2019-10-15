package seedu.address.model.answerable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAnswerables.A_ANSWERABLE;
import static seedu.address.testutil.TypicalAnswerables.BETA;

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
    public void contains_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueAnswerableList.contains(A_ANSWERABLE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        assertTrue(uniqueAnswerableList.contains(A_ANSWERABLE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        Answerable editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueAnswerableList.contains(editedAlice));
    }

    @Test
    public void add_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.add(null));
    }

    @Test
    public void add_duplicateAnswerable_throwsDuplicateAnswerableException() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList.add(A_ANSWERABLE));
    }

    @Test
    public void setAnswerable_nullTargetAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerable(null, A_ANSWERABLE));
    }

    @Test
    public void setAnswerable_nullEditedAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerable(A_ANSWERABLE, null));
    }

    @Test
    public void setAnswerable_targetAnswerableNotInList_throwsAnswerableNotFoundException() {
        assertThrows(AnswerableNotFoundException.class, () -> uniqueAnswerableList.setAnswerable(A_ANSWERABLE, A_ANSWERABLE));
    }

    @Test
    public void setAnswerable_editedAnswerableIsSameAnswerable_success() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        uniqueAnswerableList.setAnswerable(A_ANSWERABLE, A_ANSWERABLE);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(A_ANSWERABLE);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerable_editedAnswerableHasSameIdentity_success() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        Answerable editedAlice = new AnswerableBuilder(A_ANSWERABLE).withCategory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueAnswerableList.setAnswerable(A_ANSWERABLE, editedAlice);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(editedAlice);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerable_editedAnswerableHasDifferentIdentity_success() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        uniqueAnswerableList.setAnswerable(A_ANSWERABLE, BETA);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(BETA);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerable_editedAnswerableHasNonUniqueIdentity_throwsDuplicateAnswerableException() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        uniqueAnswerableList.add(BETA);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList.setAnswerable(A_ANSWERABLE, BETA));
    }

    @Test
    public void remove_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsAnswerableNotFoundException() {
        assertThrows(AnswerableNotFoundException.class, () -> uniqueAnswerableList.remove(A_ANSWERABLE));
    }

    @Test
    public void remove_existingAnswerable_removesAnswerable() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        uniqueAnswerableList.remove(A_ANSWERABLE);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerables_nullUniqueAnswerableList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerables((UniqueAnswerableList) null));
    }

    @Test
    public void setAnswerables_uniqueAnswerableList_replacesOwnListWithProvidedUniqueAnswerableList() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(BETA);
        uniqueAnswerableList.setAnswerables(expectedUniqueAnswerableList);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerables_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerables((List<Answerable>) null));
    }

    @Test
    public void setAnswerables_list_replacesOwnListWithProvidedList() {
        uniqueAnswerableList.add(A_ANSWERABLE);
        List<Answerable> answerableList = Collections.singletonList(BETA);
        uniqueAnswerableList.setAnswerables(answerableList);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(BETA);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerables_listWithDuplicateAnswerables_throwsDuplicateAnswerableException() {
        List<Answerable> listWithDuplicateAnswerables = Arrays.asList(A_ANSWERABLE, A_ANSWERABLE);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList.setAnswerables(listWithDuplicateAnswerables));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueAnswerableList.asUnmodifiableObservableList().remove(0));
    }
}
