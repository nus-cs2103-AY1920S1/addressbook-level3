package seedu.address.testutil;

import seedu.address.model.classroom.Classroom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalClassrooms {

    public static final Classroom CLASSROOM_ONE = new ClassroomBuilder()
            .withStudents(TypicalStudents.getTypicalStudents())
            .withAssignments(TypicalAssignments.getTypicalAssignments()).build();

    private TypicalClassrooms() {};

    public static List<Classroom> getTypicalClassroom() {
        return new ArrayList<>(Arrays.asList(CLASSROOM_ONE));
    }

}
