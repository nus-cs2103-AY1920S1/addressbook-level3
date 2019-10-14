package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.Employee.*;
import seedu.address.model.Employee.EmployeeAddress;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code employee}'s details
     */
    public EditPersonDescriptorBuilder(Employee employee) {
        descriptor = new EditPersonDescriptor();
        descriptor.setEmployeeName(employee.getEmployeeName());
        descriptor.setEmployeePhone(employee.getEmployeePhone());
        descriptor.setEmployeeEmail(employee.getEmployeeEmail());
        descriptor.setEmployeeAddress(employee.getEmployeeAddress());
        descriptor.setTags(employee.getTags());
    }

    /**
     * Sets the {@code EmployeeName} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setEmployeeName(new EmployeeName(name));
        return this;
    }

    /**
     * Sets the {@code EmployeePhone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setEmployeePhone(new EmployeePhone(phone));
        return this;
    }

    /**
     * Sets the {@code EmployeeEmail} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmployeeEmail(new EmployeeEmail(email));
        return this;
    }

    /**
     * Sets the {@code EmployeeAddress} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setEmployeeAddress(new EmployeeAddress(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
