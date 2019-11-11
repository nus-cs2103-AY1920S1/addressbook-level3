package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.builder.StudentBuilder;
import seedu.tarence.model.builder.TutorialBuilder;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Tutorial;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // no valid tab given
        assertFalse(commandResult.isChangeTabs());

        // no valid tutorial given
        assertFalse(commandResult.isShowAttendance());

        // different hasAttendanceDisplay value -> returns false
        Tutorial tutorial = new TutorialBuilder().build();
        assertFalse(commandResult.equals(new CommandResult("feedback", tutorial)));

        // Same tutorial, everything else same, returns true
        commandResult = new CommandResult("feedback", tutorial);
        assertTrue(commandResult.equals(new CommandResult("feedback", tutorial)));

        // Show attendance feature is set
        assertTrue(commandResult.isShowAttendance());
        assertTrue(tutorial.equals(commandResult.getTutorialAttendance()));

        // Same tutorial is received
        assertTrue(tutorial.equals(commandResult.getTutorialAttendance()));

        // Set tab to display
        commandResult = new CommandResult("feedback", TabNames.MODULES);
        assertTrue(TabNames.MODULES.equals(commandResult.getTabToDisplay()));
        assertTrue(commandResult.isChangeTabs());
        assertFalse(TabNames.STUDENTS.equals(commandResult.getTabToDisplay()));

        // Set assignment to display
        Assignment dummyAssignment =
                new Assignment("Dummy assignment", 10,
                        new Date(1991 / 1 / 1), new Date(1992 / 1 / 1));
        Map<Student, Integer> dummyMap = new HashMap<>();
        Student alice1 = new StudentBuilder().withName("Alice Pauline")
                .withEmail("alice@example.com").withMatricNum("A0123456X").withNusnetId("e0123456")
                .build();
        Student alice2 = new StudentBuilder().withName("Alice Pauline2")
                .withEmail("alice@example.com").withMatricNum("A0123456X").withNusnetId("e0123456")
                .build();
        dummyMap.put(alice1, 5);
        dummyMap.put(alice2, 5);

        commandResult = new CommandResult("feedback", dummyAssignment, dummyMap, DisplayFormat.GRAPH);
        assertTrue(DisplayFormat.GRAPH.equals(commandResult.getAssignmentDisplayFormat()));
        assertFalse(DisplayFormat.TABLE.equals(commandResult.getAssignmentDisplayFormat()));
        assertTrue(dummyMap.equals(commandResult.getStudentScores()));
        assertTrue(dummyAssignment.equals(commandResult.getAssignmentToDisplay()));
        assertTrue(commandResult.isAssignmentDisplay());

        // Set assignment list to display
        List<Assignment> dummyAssignmentList = new ArrayList<>();
        commandResult = new CommandResult("feedback", dummyAssignmentList);
        assertTrue(commandResult.isAssignmentsDisplay());
        assertTrue(dummyAssignmentList.equals(commandResult.getAssignmentsToDisplay()));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }
}
