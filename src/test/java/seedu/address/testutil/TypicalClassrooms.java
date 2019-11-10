package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.classroom.Classroom;

/**
 * A utility class containing a list of {@code Classroom} objects to be used in tests.
 */
public class TypicalClassrooms {

    public static final Classroom CLASSROOM_ONE = new ClassroomBuilder()
            .withClassroomName("Classroom One")
            .withStudents(TypicalStudents.getTypicalStudents())
            .withAssignments(TypicalAssignments.getTypicalAssignments()).build();

    public static final Classroom CLASSROOM_TWO = new ClassroomBuilder()
            .withClassroomName("Classroom Two")
            .withStudents(TypicalStudents.getTypicalStudents())
            .withAssignments(TypicalAssignments.getTypicalAssignments()).build();

    private TypicalClassrooms() {};

    public static List<Classroom> getTypicalClassroom() {
        return new ArrayList<>(Arrays.asList(CLASSROOM_ONE));
    }

}
