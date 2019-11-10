package seedu.address.testutil;

import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;

/**
 * A utility class to help with building EditAssignmentDescriptor objects.
 */
public class EditAssignmentDescriptorBuilder {

    private EditAssignmentCommand.EditAssignmentDescriptor descriptor;

    public EditAssignmentDescriptorBuilder() {
        descriptor = new EditAssignmentCommand.EditAssignmentDescriptor();
    }

    public EditAssignmentDescriptorBuilder(EditAssignmentDescriptor descriptor) {
        this.descriptor = new EditAssignmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAssignmentDescriptor} with fields containing {@code Assignment}'s details
     */
    public EditAssignmentDescriptorBuilder(Assignment assignment) {
        descriptor = new EditAssignmentCommand.EditAssignmentDescriptor();
        descriptor.setAssignmentName(assignment.getAssignmentName());
        descriptor.setAssignmentDeadline(assignment.getAssignmentDeadline());
    }

    /**
     * Sets the {@code AssignmentName} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withAssignmentName(String assignmentName) {
        descriptor.setAssignmentName(new AssignmentName(assignmentName));
        return this;
    }

    /**
     * Sets the {@code AssignmentDeadline} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withAssignmentDeadline(String assignmentDeadline) {
        descriptor.setAssignmentDeadline(new AssignmentDeadline(assignmentDeadline));
        return this;
    }

    public EditAssignmentCommand.EditAssignmentDescriptor build() {
        return descriptor;
    }
}
