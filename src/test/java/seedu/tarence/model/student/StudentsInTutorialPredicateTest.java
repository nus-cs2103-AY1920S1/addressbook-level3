package seedu.tarence.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_SECOND_IN_LIST;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.builder.StudentBuilder;

public class StudentsInTutorialPredicateTest {

    @Test
    public void equals() {
        StudentsInTutorialPredicate firstPredicate =
                new StudentsInTutorialPredicate(INDEX_FIRST_IN_LIST);
        StudentsInTutorialPredicate secondPredicate =
                new StudentsInTutorialPredicate(INDEX_SECOND_IN_LIST);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StudentsInTutorialPredicate firstPredicateCopy = new StudentsInTutorialPredicate(INDEX_FIRST_IN_LIST);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tutorialListContainsStudent_returnsTrue() {
        StudentsInTutorialPredicate predicate = new StudentsInTutorialPredicate(INDEX_FIRST_IN_LIST);
        Student student = new StudentBuilder().build();
        predicate.setModCode(student.getModCode());
        predicate.setTutName(student.getTutName());
        assertTrue(predicate.test(student));
    }

    @Test
    public void test_tutorialListDoesntContainStudent_returnsFalse() {
        StudentsInTutorialPredicate predicate = new StudentsInTutorialPredicate(INDEX_FIRST_IN_LIST);
        Student student = new StudentBuilder().build();
        predicate.setModCode(student.getModCode());
        predicate.setTutName(student.getTutName());

        Student mismatchedTutNameStudent = new StudentBuilder().withTutName("Not real tutorial").build();
        assertFalse(predicate.test(mismatchedTutNameStudent));

        Student mismatchedModCodeStudent = new StudentBuilder().withModCode("RE4000").build();
        assertFalse(predicate.test(mismatchedModCodeStudent));
    }
}
