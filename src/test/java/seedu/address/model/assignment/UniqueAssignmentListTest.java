package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.AssignmentBuilder.EDITED_ASSIGNMENT_DEADLINE;
import static seedu.address.testutil.AssignmentBuilder.EDITED_ASSIGNMENT_NAME;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_MATH;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_SCIENCE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.testutil.AssignmentBuilder;

public class UniqueAssignmentListTest {
    private final UniqueAssignmentList uniqueAssignmentList = new UniqueAssignmentList();

    @Test
    public void contains_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAssignmentList.contains(null));
    }

    @Test
    public void contains_assignmentNotInList_returnsFalse() {
        assertFalse(uniqueAssignmentList.contains(ASSIGNMENT_MATH));
    }

    @Test
    public void contains_assignmentInList_returnsTrue() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        assertTrue(uniqueAssignmentList.contains(ASSIGNMENT_MATH));
    }

    @Test
    public void contains_assignmentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        Assignment editedAss = new AssignmentBuilder(ASSIGNMENT_MATH)
                .withAssignmentDeadline(EDITED_ASSIGNMENT_DEADLINE).build();
        assertTrue(uniqueAssignmentList.contains(editedAss));
    }
    @Test
    public void add_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAssignmentList.add(null));
    }

    @Test
    public void add_duplicateAssignment_throwsDuplicateAssignmentException() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        assertThrows(DuplicateAssignmentException.class, () -> uniqueAssignmentList.add(ASSIGNMENT_MATH));
    }

    @Test
    public void setAssignment_nullTargetAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAssignmentList.setAssignment(null, ASSIGNMENT_MATH));
    }

    @Test
    public void setAssignment_nullEditedAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAssignmentList.setAssignment(ASSIGNMENT_MATH, null));
    }

    @Test
    public void setAssignment_targetAssignmentNotInList_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () ->
                uniqueAssignmentList.setAssignment(ASSIGNMENT_MATH, ASSIGNMENT_MATH));
    }

    @Test
    public void setAssignment_editedAssignmentIsSameAssignment_success() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        uniqueAssignmentList.setAssignment(ASSIGNMENT_MATH, ASSIGNMENT_MATH);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(ASSIGNMENT_MATH);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasSameIdentity_success() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        Assignment editedAss = new AssignmentBuilder(ASSIGNMENT_MATH)
                .withAssignmentName(EDITED_ASSIGNMENT_NAME)
                .withAssignmentDeadline(EDITED_ASSIGNMENT_DEADLINE).build();
        uniqueAssignmentList.setAssignment(ASSIGNMENT_MATH, editedAss);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(editedAss);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignment_editedAssignmentHasDifferentIdentity_success() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        uniqueAssignmentList.setAssignment(ASSIGNMENT_MATH, ASSIGNMENT_SCIENCE);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(ASSIGNMENT_SCIENCE);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void remove_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAssignmentList.remove(null));
    }

    @Test
    public void remove_assignmentDoesNotExist_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () -> uniqueAssignmentList.remove(ASSIGNMENT_MATH));
    }

    @Test
    public void remove_existingAssignment_removesAssignment() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        uniqueAssignmentList.remove(ASSIGNMENT_MATH);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignments_nullUniqueAssignmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAssignmentList.setAssignments((UniqueAssignmentList) null));
    }

    @Test
    public void setAssignments_uniqueAssignmentList_replacesOwnListWithProvidedUniqueAssignmentList() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(ASSIGNMENT_SCIENCE);
        uniqueAssignmentList.setAssignments(expectedUniqueAssignmentList);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAssignmentList.setAssignments((List<Assignment>) null));
    }

    @Test
    public void setAssignments_list_replacesOwnListWithProvidedList() {
        uniqueAssignmentList.add(ASSIGNMENT_MATH);
        List<Assignment> assignmentList = Collections.singletonList(ASSIGNMENT_SCIENCE);
        uniqueAssignmentList.setAssignments(assignmentList);
        UniqueAssignmentList expectedUniqueAssignmentList = new UniqueAssignmentList();
        expectedUniqueAssignmentList.add(ASSIGNMENT_SCIENCE);
        assertEquals(expectedUniqueAssignmentList, uniqueAssignmentList);
    }

    @Test
    public void setAssignments_listWithDuplicateAssignments_throwsDuplicateAssignmentException() {
        List<Assignment> listWithDuplicateAssignments = Arrays.asList(ASSIGNMENT_MATH, ASSIGNMENT_MATH);
        assertThrows(
                DuplicateAssignmentException.class, () ->
                        uniqueAssignmentList.setAssignments(listWithDuplicateAssignments));
    }
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueAssignmentList.asUnmodifiableObservableList().remove(0));
    }
}
