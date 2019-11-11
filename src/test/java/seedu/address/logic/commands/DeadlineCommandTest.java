package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.deadline.Deadline;
import seedu.address.testutil.DeadlineBuilder;

public class DeadlineCommandTest {

    @Test
    public void constructor_nullDeadline_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeadlineCommand(null));
    }

    @Test
    public void equals() {
        Deadline test = new DeadlineBuilder().withTask("Test").build();
        Deadline exam = new DeadlineBuilder().withTask("Exam").build();
        DeadlineCommand addTestCommand = new DeadlineCommand(test);
        DeadlineCommand addExamCommand = new DeadlineCommand(exam);

        // same object -> returns true
        assertTrue(addTestCommand.equals(addTestCommand));

        // same values -> returns true
        DeadlineCommand addTestCommandCopy = new DeadlineCommand(test);
        assertTrue(addTestCommand.equals(addTestCommandCopy));

        // different types -> returns false
        assertFalse(addTestCommand.equals(1));

        // null -> returns false
        assertFalse(addTestCommand.equals(null));

        // different flashCard -> returns false
        assertFalse(addTestCommand.equals(addExamCommand));

        //same question different answer
        Deadline testCopy = new DeadlineBuilder(test).withDueDate("22/12/2020").build();
        addTestCommandCopy = new DeadlineCommand(testCopy);
        assertFalse(addTestCommandCopy.equals(testCopy));
    }

    @Test
    public void toStringTest() {
        Deadline validDeadline = new DeadlineBuilder().build();
        DeadlineCommand deadlineCommand = new DeadlineCommand(validDeadline);
        //same object
        assertTrue(deadlineCommand.toString().equals(deadlineCommand.toString()));

        //same value
        DeadlineCommand deadlineCommandCopy = new DeadlineCommand(validDeadline);
        assertTrue(deadlineCommand.toString().equals(deadlineCommandCopy.toString()));

        //same question diff answer
        deadlineCommandCopy = new DeadlineCommand(new DeadlineBuilder(validDeadline).withDueDate("10/10/2020").build());
        assertFalse(deadlineCommand.toString().equals(deadlineCommandCopy.toString()));
    }
}
