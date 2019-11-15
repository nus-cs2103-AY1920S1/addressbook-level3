package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalMcqs.MCQ_B;
import static seedu.revision.testutil.TypicalMcqs.MCQ_C;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.revision.model.answerable.exceptions.AnswerableNotFoundException;
import seedu.revision.model.answerable.exceptions.DuplicateAnswerableException;
import seedu.revision.testutil.McqBuilder;

public class UniqueAnswerableListTest {

    private final UniqueAnswerableList uniqueAnswerableList = new UniqueAnswerableList();

    @Test
    public void contains_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueAnswerableList.contains(MCQ_C));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueAnswerableList.add(MCQ_C);
        assertTrue(uniqueAnswerableList.contains(MCQ_C));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAnswerableList.add(MCQ_C);
        Answerable editedAlice = new McqBuilder(MCQ_C).withCategories(VALID_CATEGORY_GREENFIELD)
                .build();
        assertTrue(uniqueAnswerableList.contains(editedAlice));
    }

    @Test
    public void add_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.add(null));
    }

    @Test
    public void add_duplicateAnswerable_throwsDuplicateAnswerableException() {
        uniqueAnswerableList.add(MCQ_C);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList.add(MCQ_C));
    }

    @Test
    public void setAnswerable_nullTargetAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerable(null, MCQ_C));
    }

    @Test
    public void setAnswerable_nullEditedAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerable(MCQ_C, null));
    }

    @Test
    public void setAnswerable_targetAnswerableNotInList_throwsAnswerableNotFoundException() {
        assertThrows(AnswerableNotFoundException.class, () -> uniqueAnswerableList
                .setAnswerable(MCQ_C, MCQ_C));
    }

    @Test
    public void setAnswerable_editedAnswerableIsSameAnswerable_success() {
        uniqueAnswerableList.add(MCQ_C);
        uniqueAnswerableList.setAnswerable(MCQ_C, MCQ_C);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(MCQ_C);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerable_editedAnswerableHasSameIdentity_success() {
        uniqueAnswerableList.add(MCQ_C);
        Answerable editedAlice = new McqBuilder(MCQ_C).withCategories(VALID_CATEGORY_GREENFIELD)
                .build();
        uniqueAnswerableList.setAnswerable(MCQ_C, editedAlice);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(editedAlice);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerable_editedAnswerableHasDifferentIdentity_success() {
        uniqueAnswerableList.add(MCQ_C);
        uniqueAnswerableList.setAnswerable(MCQ_C, MCQ_B);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(MCQ_B);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerable_editedAnswerableHasNonUniqueIdentity_throwsDuplicateAnswerableException() {
        uniqueAnswerableList.add(MCQ_C);
        uniqueAnswerableList.add(MCQ_B);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList.setAnswerable(MCQ_C, MCQ_B));
    }

    @Test
    public void remove_nullAnswerable_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsAnswerableNotFoundException() {
        assertThrows(AnswerableNotFoundException.class, () -> uniqueAnswerableList.remove(MCQ_C));
    }

    @Test
    public void remove_existingAnswerable_removesAnswerable() {
        uniqueAnswerableList.add(MCQ_C);
        uniqueAnswerableList.remove(MCQ_C);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerables_nullUniqueAnswerableList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList
                .setAnswerables((UniqueAnswerableList) null));
    }

    @Test
    public void setAnswerables_uniqueAnswerableList_replacesOwnListWithProvidedUniqueAnswerableList() {
        uniqueAnswerableList.add(MCQ_C);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(MCQ_B);
        uniqueAnswerableList.setAnswerables(expectedUniqueAnswerableList);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerables_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnswerableList.setAnswerables((List<Answerable>) null));
    }

    @Test
    public void setAnswerables_list_replacesOwnListWithProvidedList() {
        uniqueAnswerableList.add(MCQ_C);
        List<Answerable> answerableList = Collections.singletonList(MCQ_B);
        uniqueAnswerableList.setAnswerables(answerableList);
        UniqueAnswerableList expectedUniqueAnswerableList = new UniqueAnswerableList();
        expectedUniqueAnswerableList.add(MCQ_B);
        assertEquals(expectedUniqueAnswerableList, uniqueAnswerableList);
    }

    @Test
    public void setAnswerables_listWithDuplicateAnswerables_throwsDuplicateAnswerableException() {
        List<Answerable> listWithDuplicateAnswerables = Arrays.asList(MCQ_C, MCQ_C);
        assertThrows(DuplicateAnswerableException.class, () -> uniqueAnswerableList
                .setAnswerables(listWithDuplicateAnswerables));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueAnswerableList.asUnmodifiableObservableList().remove(0));
    }
}
