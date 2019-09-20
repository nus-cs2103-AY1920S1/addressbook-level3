package seedu.address.model.Problem;

import org.junit.jupiter.api.Test;
import seedu.address.model.Problem.exceptions.DuplicateProblemException;
import seedu.address.model.Problem.exceptions.ProblemNotFoundException;
import seedu.address.testutil.ProblemBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProblems.ALICE;
import static seedu.address.testutil.TypicalProblems.BOB;

public class UniqueProblemListTest {

    private final UniqueProblemList uniqueProblemList = new UniqueProblemList();

    @Test
    public void contains_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProblemList.contains(null));
    }

    @Test
    public void contains_problemNotInList_returnsFalse() {
        assertFalse(uniqueProblemList.contains(ALICE));
    }

    @Test
    public void contains_problemInList_returnsTrue() {
        uniqueProblemList.add(ALICE);
        assertTrue(uniqueProblemList.contains(ALICE));
    }

    @Test
    public void contains_problemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueProblemList.add(ALICE);
        Problem editedAlice = new ProblemBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueProblemList.contains(editedAlice));
    }

    @Test
    public void add_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProblemList.add(null));
    }

    @Test
    public void add_duplicateProblem_throwsDuplicateProblemException() {
        uniqueProblemList.add(ALICE);
        assertThrows(DuplicateProblemException.class, () -> uniqueProblemList.add(ALICE));
    }

    @Test
    public void setProblem_nullTargetProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProblemList.setProblem(null, ALICE));
    }

    @Test
    public void setProblem_nullEditedProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProblemList.setProblem(ALICE, null));
    }

    @Test
    public void setProblem_targetProblemNotInList_throwsProblemNotFoundException() {
        assertThrows(ProblemNotFoundException.class, () -> uniqueProblemList.setProblem(ALICE, ALICE));
    }

    @Test
    public void setProblem_editedProblemIsSameProblem_success() {
        uniqueProblemList.add(ALICE);
        uniqueProblemList.setProblem(ALICE, ALICE);
        UniqueProblemList expectedUniqueProblemList = new UniqueProblemList();
        expectedUniqueProblemList.add(ALICE);
        assertEquals(expectedUniqueProblemList, uniqueProblemList);
    }

    @Test
    public void setProblem_editedProblemHasSameIdentity_success() {
        uniqueProblemList.add(ALICE);
        Problem editedAlice = new ProblemBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueProblemList.setProblem(ALICE, editedAlice);
        UniqueProblemList expectedUniqueProblemList = new UniqueProblemList();
        expectedUniqueProblemList.add(editedAlice);
        assertEquals(expectedUniqueProblemList, uniqueProblemList);
    }

    @Test
    public void setProblem_editedProblemHasDifferentIdentity_success() {
        uniqueProblemList.add(ALICE);
        uniqueProblemList.setProblem(ALICE, BOB);
        UniqueProblemList expectedUniqueProblemList = new UniqueProblemList();
        expectedUniqueProblemList.add(BOB);
        assertEquals(expectedUniqueProblemList, uniqueProblemList);
    }

    @Test
    public void setProblem_editedProblemHasNonUniqueIdentity_throwsDuplicateProblemException() {
        uniqueProblemList.add(ALICE);
        uniqueProblemList.add(BOB);
        assertThrows(DuplicateProblemException.class, () -> uniqueProblemList.setProblem(ALICE, BOB));
    }

    @Test
    public void remove_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProblemList.remove(null));
    }

    @Test
    public void remove_problemDoesNotExist_throwsProblemNotFoundException() {
        assertThrows(ProblemNotFoundException.class, () -> uniqueProblemList.remove(ALICE));
    }

    @Test
    public void remove_existingProblem_removesProblem() {
        uniqueProblemList.add(ALICE);
        uniqueProblemList.remove(ALICE);
        UniqueProblemList expectedUniqueProblemList = new UniqueProblemList();
        assertEquals(expectedUniqueProblemList, uniqueProblemList);
    }

    @Test
    public void setProblems_nullUniqueProblemList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProblemList.setProblems((UniqueProblemList) null));
    }

    @Test
    public void setProblems_uniqueProblemList_replacesOwnListWithProvidedUniqueProblemList() {
        uniqueProblemList.add(ALICE);
        UniqueProblemList expectedUniqueProblemList = new UniqueProblemList();
        expectedUniqueProblemList.add(BOB);
        uniqueProblemList.setProblems(expectedUniqueProblemList);
        assertEquals(expectedUniqueProblemList, uniqueProblemList);
    }

    @Test
    public void setProblems_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProblemList.setProblems((List<Problem>) null));
    }

    @Test
    public void setProblems_list_replacesOwnListWithProvidedList() {
        uniqueProblemList.add(ALICE);
        List<Problem> problemList = Collections.singletonList(BOB);
        uniqueProblemList.setProblems(problemList);
        UniqueProblemList expectedUniqueProblemList = new UniqueProblemList();
        expectedUniqueProblemList.add(BOB);
        assertEquals(expectedUniqueProblemList, uniqueProblemList);
    }

    @Test
    public void setProblems_listWithDuplicateProblems_throwsDuplicateProblemException() {
        List<Problem> listWithDuplicateProblems = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateProblemException.class, () -> uniqueProblemList.setProblems(listWithDuplicateProblems));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueProblemList.asUnmodifiableObservableList().remove(0));
    }
}
