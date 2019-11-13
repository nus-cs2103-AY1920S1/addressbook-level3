package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {

    public static final String DEFAULT_ASSIGNMENT_NAME = "Math Assignment 1";
    public static final String DEFAULT_ASSIGNMENT_DEADLINE = "31/12/2019 2359";
    public static final String EDITED_ASSIGNMENT_NAME = "Science Assignment 1";
    public static final String EDITED_ASSIGNMENT_DEADLINE = "31/10/2019 2359";



    private AssignmentName assignmentName;
    private AssignmentDeadline assignmentDeadline;

    public AssignmentBuilder() {
        assignmentName = new AssignmentName(DEFAULT_ASSIGNMENT_NAME);
        assignmentDeadline = new AssignmentDeadline(DEFAULT_ASSIGNMENT_DEADLINE);
    }

    /**
     * Initializes the Assignment with the data of {@code assignmentToCopy}.
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        assignmentName = assignmentToCopy.getAssignmentName();
        assignmentDeadline = assignmentToCopy.getAssignmentDeadline();
    }

    /**
     * Sets the {@code AssignmentName} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withAssignmentName(String assignmentName) {
        this.assignmentName = new AssignmentName(assignmentName);
        return this;
    }

    /**
     * Sets the {@code AssignmentDeadline} and set it to the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withAssignmentDeadline(String assignmentDeadline) {
        this.assignmentDeadline = new AssignmentDeadline(assignmentDeadline);
        return this;
    }

    public Assignment build() {
        return new Assignment(assignmentName, assignmentDeadline);
    }

}
