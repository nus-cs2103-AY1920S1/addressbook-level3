package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalAssignments {

    public static final Assignment assignmentOne = new AssignmentBuilder()
            .withAssignmentName("Math Homework 3")
            .withAssignmentDeadline("12/12/2019 1800").build();

    public static final Assignment assignmentTwo = new AssignmentBuilder()
            .withAssignmentName("Science Assignemnt 2")
            .withAssignmentDeadline("08/02/2020 2020").build();

    public static final Assignment assignmentThree = new AssignmentBuilder()
            .withAssignmentName("English Worksheet 1")
            .withAssignmentDeadline("02/12/2024 1029").build();

    private TypicalAssignments() {}

    public static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(assignmentOne, assignmentTwo, assignmentThree));
    }

}
