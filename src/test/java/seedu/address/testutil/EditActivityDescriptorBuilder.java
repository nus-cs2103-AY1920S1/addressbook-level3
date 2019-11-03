package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditActivityDescriptor;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;

/**
 * A utility class to help with building EditActivityDescriptor objects.
 */
public class EditActivityDescriptorBuilder {

    private EditActivityDescriptor descriptor;

    public EditActivityDescriptorBuilder() {
        descriptor = new EditActivityDescriptor();
    }

    public EditActivityDescriptorBuilder(EditActivityDescriptor descriptor) {
        this.descriptor = new EditActivityDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditActivityDescriptor} with fields containing {@code activity}'s details
     */
    public EditActivityDescriptorBuilder(Activity activity) {
        descriptor = new EditActivityDescriptor();
        descriptor.setTitle(activity.getTitle());
    }

    /**
     * Sets the {@code Title} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    public EditActivityDescriptor build() {
        return descriptor;
    }
}
