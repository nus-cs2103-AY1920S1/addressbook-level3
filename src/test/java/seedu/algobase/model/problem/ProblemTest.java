package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_AUTHOR_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_AUTHOR_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DIFFICULTY_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_REMARK_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_SOURCE_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_DIFFICULT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_WEBLINK_QUICK_SORT;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.FACTORIAL;
import static seedu.algobase.testutil.TypicalProblems.TWO_SUM;

import org.junit.jupiter.api.Test;

import seedu.algobase.testutil.ProblemBuilder;

public class ProblemTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Problem problem = new ProblemBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> problem.getTags().remove(0));
    }

    @Test
    public void isSameProblem() {
        // same object -> returns true
        assertTrue(TWO_SUM.isSameProblem(TWO_SUM));

        // null -> returns false
        assertFalse(TWO_SUM.isSameProblem(null));

        // different author and weblink -> returns false
        Problem editedTwoSum = new ProblemBuilder(TWO_SUM).withAuthor(VALID_AUTHOR_QUICK_SORT)
            .withWeblink(VALID_WEBLINK_QUICK_SORT).build();
        assertFalse(TWO_SUM.isSameProblem(editedTwoSum));

        // different name -> returns false
        editedTwoSum = new ProblemBuilder(TWO_SUM).withName(VALID_NAME_QUICK_SORT).build();
        assertFalse(TWO_SUM.isSameProblem(editedTwoSum));

        // same name, same author, different attributes -> returns true
        editedTwoSum = new ProblemBuilder(TWO_SUM).withDescription(VALID_DESCRIPTION_QUICK_SORT)
            .withTags(VALID_TAG_DIFFICULT).build();
        assertTrue(TWO_SUM.isSameProblem(editedTwoSum));

        // same name, same weblink, different attributes -> returns true
        editedTwoSum = new ProblemBuilder(TWO_SUM).withAuthor(VALID_AUTHOR_FACTORIAL)
            .withTags(VALID_TAG_DIFFICULT).build();
        assertTrue(TWO_SUM.isSameProblem(editedTwoSum));

        // same name, same phone, same email, different attributes -> returns true
        editedTwoSum = new ProblemBuilder(TWO_SUM).withSource(VALID_SOURCE_FACTORIAL)
            .withTags(VALID_TAG_DIFFICULT).build();
        assertTrue(TWO_SUM.isSameProblem(editedTwoSum));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Problem twoSumCopy = new ProblemBuilder(TWO_SUM).build();
        assertEquals(TWO_SUM, twoSumCopy);

        // same object -> returns true
        assertEquals(TWO_SUM, TWO_SUM);

        // null -> returns false
        assertNotEquals(null, TWO_SUM);

        // different type -> returns false
        assertNotEquals(5, TWO_SUM);

        // different problems -> returns false
        assertNotEquals(TWO_SUM, FACTORIAL);

        // different name -> returns false
        Problem editedTwoSum = new ProblemBuilder(TWO_SUM).withName(VALID_NAME_FACTORIAL).build();
        assertNotEquals(TWO_SUM, editedTwoSum);

        // different author -> returns false
        editedTwoSum = new ProblemBuilder(TWO_SUM).withAuthor(VALID_AUTHOR_QUICK_SORT).build();
        assertNotEquals(TWO_SUM, editedTwoSum);

        // different weblink -> returns false
        editedTwoSum = new ProblemBuilder(TWO_SUM).withWeblink(VALID_WEBLINK_QUICK_SORT).build();
        assertNotEquals(TWO_SUM, editedTwoSum);

        // different source -> returns false
        editedTwoSum = new ProblemBuilder(TWO_SUM).withSource(VALID_SOURCE_FACTORIAL).build();
        assertNotEquals(TWO_SUM, editedTwoSum);

        // different difficulty -> returns false
        editedTwoSum = new ProblemBuilder(TWO_SUM).withDifficulty(VALID_DIFFICULTY_FACTORIAL).build();
        assertNotEquals(TWO_SUM, editedTwoSum);

        // different description -> returns false
        editedTwoSum = new ProblemBuilder(TWO_SUM).withDescription(VALID_DESCRIPTION_QUICK_SORT).build();
        assertNotEquals(TWO_SUM, editedTwoSum);

        // different remark -> returns false
        editedTwoSum = new ProblemBuilder(TWO_SUM).withRemark(VALID_REMARK_FACTORIAL).build();
        assertNotEquals(TWO_SUM, editedTwoSum);

        // different tags -> returns false
        editedTwoSum = new ProblemBuilder(TWO_SUM).withTags(VALID_TAG_DIFFICULT).build();
        assertNotEquals(TWO_SUM, editedTwoSum);
    }
}
