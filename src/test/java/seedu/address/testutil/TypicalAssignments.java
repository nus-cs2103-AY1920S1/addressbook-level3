package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment ASSIGNMENT_ONE = new AssignmentBuilder()
            .withAssignmentName("Math Homework 3")
            .withAssignmentDeadline("12/12/2019 1800").build();

    public static final Assignment ASSIGNMENT_TWO = new AssignmentBuilder()
            .withAssignmentName("Science Assignemnt 2")
            .withAssignmentDeadline("08/02/2020 2020").build();

    public static final Assignment ASSIGNMENT_THREE = new AssignmentBuilder()
            .withAssignmentName("English Worksheet 1")
            .withAssignmentDeadline("02/12/2024 1029").build();

    private TypicalAssignments() {}

    public static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT_ONE, ASSIGNMENT_TWO, ASSIGNMENT_THREE));
    }

}
