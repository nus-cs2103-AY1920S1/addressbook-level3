package seedu.address.testutil;

import seedu.address.logic.commands.editcommand.EditParticipantCommand.EditParticipantDescriptor;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;

/**
 * A utility class to help with building {@code EditParticipantDescriptor} objects.
 */
public class EditParticipantDescriptorBuilder {

    private EditParticipantDescriptor descriptor;

    public EditParticipantDescriptorBuilder() {
        this.descriptor = new EditParticipantDescriptor();
    }

    public EditParticipantDescriptorBuilder(EditParticipantDescriptor descriptor) {
        this.descriptor = new EditParticipantDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditParticipantDescriptor} with fields containing {@code Participant}'s details
     */
    public EditParticipantDescriptorBuilder(Participant participant) {
        descriptor = new EditParticipantDescriptor();
        descriptor.setName(participant.getName());
        descriptor.setPhone(participant.getPhone());
        descriptor.setEmail(participant.getEmail());
    }

    /**
     * Sets the {@code Name} of the {@code EditParticipantDescriptor} that we are building.
     */
    public EditParticipantDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditParticipantDescriptor} that we are building.
     */
    public EditParticipantDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditParticipantDescriptor} that we are building.
     */
    public EditParticipantDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Constructor.
     */
    public EditParticipantDescriptor build() {
        return descriptor;
    }

}
