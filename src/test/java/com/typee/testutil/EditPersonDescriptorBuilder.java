package com.typee.testutil;

import com.typee.logic.commands.EditCommand;
import com.typee.model.person.Name;
import com.typee.model.person.Person;

/**
 * A utility class to help with building EditEngagementDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCommand.EditEngagementDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditCommand.EditEngagementDescriptor();
    }

    public EditPersonDescriptorBuilder(EditCommand.EditEngagementDescriptor descriptor) {
        this.descriptor = new EditCommand.EditEngagementDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEngagementDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditCommand.EditEngagementDescriptor();
        descriptor.setName(person.getName());
    }

    /**
     * Sets the {@code Name} of the {@code EditEngagementDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditCommand.EditEngagementDescriptor build() {
        return descriptor;
    }
}
