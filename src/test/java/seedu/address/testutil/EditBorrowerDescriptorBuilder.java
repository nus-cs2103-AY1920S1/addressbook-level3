package seedu.address.testutil;

import seedu.address.logic.commands.EditBorrowerCommand;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.borrower.Email;
import seedu.address.model.borrower.Name;
import seedu.address.model.borrower.Phone;

/**
 * A utility class to help with building EditBorrowerDescriptor objects.
 */
public class EditBorrowerDescriptorBuilder {

    private EditBorrowerCommand.EditBorrowerDescriptor descriptor;

    public EditBorrowerDescriptorBuilder() {
        descriptor = new EditBorrowerCommand.EditBorrowerDescriptor();
    }

    public EditBorrowerDescriptorBuilder(EditBorrowerCommand.EditBorrowerDescriptor descriptor) {
        this.descriptor = new EditBorrowerCommand.EditBorrowerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBorrowerDescriptor} with fields containing {@code borrower}'s details
     */
    public EditBorrowerDescriptorBuilder(Borrower borrower) {
        descriptor = new EditBorrowerCommand.EditBorrowerDescriptor();
        descriptor.setName(borrower.getName());
        descriptor.setPhone(borrower.getPhone());
        descriptor.setEmail(borrower.getEmail());
    }

    /**
     * Sets the {@code Name} of the {@code EditBorrowerDescriptor} that we are building.
     */
    public EditBorrowerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditBorrowerDescriptor} that we are building.
     */
    public EditBorrowerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditBorrowerDescriptor} that we are building.
     */
    public EditBorrowerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }


    public EditBorrowerCommand.EditBorrowerDescriptor build() {
        return descriptor;
    }

    /**
     * Sets the {@code id} of the {@code EditBorrowerDescriptor} that we are building.
     */
    public EditBorrowerDescriptorBuilder withBorrowerId(String id) {
        descriptor.setId(new BorrowerId(id));
        return this;
    }
}
