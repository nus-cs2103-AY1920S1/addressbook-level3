package seedu.address.testutil;

import seedu.address.logic.commands.editcommand.EditMentorCommand.EditMentorDescriptor;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.SubjectName;

/**
 * A utility class to help with building {@code EditMentorDescriptor} objects.
 */
public class EditMentorDescriptorBuilder {

    private EditMentorDescriptor descriptor;

    public EditMentorDescriptorBuilder() {
        this.descriptor = new EditMentorDescriptor();
    }

    public EditMentorDescriptorBuilder(EditMentorDescriptor descriptor) {
        this.descriptor = new EditMentorDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMentorDescriptor} with fields containing {@code Mentor}'s details
     */
    public EditMentorDescriptorBuilder(Mentor mentor) {
        descriptor = new EditMentorDescriptor();
        descriptor.setName(mentor.getName());
        descriptor.setPhone(mentor.getPhone());
        descriptor.setEmail(mentor.getEmail());
        descriptor.setOrganization(mentor.getOrganization());
        descriptor.setSubject(mentor.getSubject());
    }

    /**
     * Sets the {@code Name} of the {@code EditMentorDescriptor} that we are building.
     */
    public EditMentorDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditMentorDescriptor} that we are building.
     */
    public EditMentorDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditMentorDescriptor} that we are building.
     */
    public EditMentorDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Organization} of the {@code EditMentorDescriptor} that we are building.
     */
    public EditMentorDescriptorBuilder withOrganization(String organization) {
        descriptor.setOrganization(new Name(organization));
        return this;
    }

    /**
     * Sets the {@code SubjectName} of the {@code EditMentorDescriptor} that we are building.
     */
    public EditMentorDescriptorBuilder withSubject(String subject) {
        descriptor.setSubject(SubjectName.valueOf(subject.toUpperCase()));
        return this;
    }

    /**
     * Constructor.
     */
    public EditMentorDescriptor build() {
        return descriptor;
    }

}
