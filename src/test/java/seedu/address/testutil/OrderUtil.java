package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.addcommand.AddOrderCommand;
import seedu.address.logic.commands.editcommand.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.order.Order;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Order.
 */
public class OrderUtil {

    /**
     * Returns an add command string for adding the {@code order}.
     */
    public static String getAddCommand(Order order) {
        return AddOrderCommand.COMMAND_WORD + " " + getOrderDetails(order);
    }

    /**
     * Returns the part of command string for the given {@code order}'s details.
     */
    public static String getOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CUSTOMER + "1" + " ");
        sb.append(PREFIX_PHONE + "1" + " ");
        sb.append(PREFIX_PRICE + order.getPrice().toString() + " ");
        order.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditOrderDescriptor}'s details.
     */
    public static String getEditOrderDescriptorDetails(EditOrderDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getCustomer().ifPresent(customer ->
                sb.append(PREFIX_CUSTOMER).append(customer.toString()).append(" "));

        descriptor.getPhone().ifPresent(phone ->
                sb.append(PREFIX_PHONE).append(phone.toString()).append(" "));


        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_PRICE).append(price.value).append(" "));

        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
