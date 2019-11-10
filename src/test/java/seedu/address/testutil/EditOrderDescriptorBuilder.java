package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.editcommand.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.phone.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {

    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOrderDescriptor} with fields containing {@code order}'s details
     */
    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setCustomer(order.getCustomer());
        descriptor.setPhone(order.getPhone());
        descriptor.setPrice(order.getPrice());
        descriptor.setTags(order.getTags());
    }

    /**
     * Sets the {@code Customer} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withCustomer(Customer customer) {
        descriptor.setCustomer(customer);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPhone(Phone phone) {
        descriptor.setPhone(phone);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditOrderDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
