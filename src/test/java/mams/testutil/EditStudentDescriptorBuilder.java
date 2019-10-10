package mams.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mams.logic.commands.EditCommand;
import mams.model.student.Email;
import mams.model.student.MatricId;
import mams.model.student.Name;
import mams.model.student.Phone;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * A utility class to help with building EditStudentDescriptor objects.
 */
public class EditStudentDescriptorBuilder {

    private EditCommand.EditStudentDescriptor descriptor;

    public EditStudentDescriptorBuilder() {
        descriptor = new EditCommand.EditStudentDescriptor();
    }

    public EditStudentDescriptorBuilder(EditCommand.EditStudentDescriptor descriptor) {
        this.descriptor = new EditCommand.EditStudentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStudentDescriptor} with fields containing {@code student}'s details
     */
    public EditStudentDescriptorBuilder(Student student) {
        descriptor = new EditCommand.EditStudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setPhone(student.getPhone());
        descriptor.setEmail(student.getEmail());
        descriptor.setMatricId(student.getMatricId());
        descriptor.setTags(student.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code MatricId} of the {@code EditStudentDescriptor} that we are building.
     */
    public EditStudentDescriptorBuilder withMatricId(String matricId) {
        descriptor.setMatricId(new MatricId(matricId));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditStudentDescriptor}
     * that we are building.
     */
    public EditStudentDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditStudentDescriptor build() {
        return descriptor;
    }
}
