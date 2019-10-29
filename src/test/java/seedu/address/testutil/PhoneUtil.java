package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.addcommand.AddPhoneCommand;
import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.model.phone.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Phone.
 */
public class PhoneUtil {

    /**
     * Returns an add command string for adding the {@code phone}.
     */
    public static String getAddCommand(Phone phone) {
        return AddPhoneCommand.COMMAND_WORD + " " + getPhoneDetails(phone);
    }

    /**
     * Returns the part of command string for the given {@code phone}'s details.
     */
    public static String getPhoneDetails(Phone phone) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_IDENTITY_NUM + phone.getIdentityNumber().toString() + " ");
        sb.append(PREFIX_SERIAL_NUM + phone.getSerialNumber().toString() + " ");
        sb.append(PREFIX_PHONE_NAME + phone.getPhoneName().toString() + " ");
        sb.append(PREFIX_BRAND + phone.getBrand().toString() + " ");
        sb.append(PREFIX_COLOUR + phone.getColour().toString() + " ");
        sb.append(PREFIX_CAPACITY + phone.getCapacity().value
                .substring(0, phone.getCapacity().value.length() - 2) + " ");
        sb.append(PREFIX_COST + phone.getCost().toString() + " ");
        phone.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPhoneDescriptor}'s details.
     */
    public static String getEditPhoneDescriptorDetails(EditPhoneDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getSerialNumber().ifPresent(serialNumber ->
                sb.append(PREFIX_SERIAL_NUM).append(serialNumber.value).append(" "));

        descriptor.getIdentityNumber().ifPresent(identityNumber ->
                sb.append(PREFIX_IDENTITY_NUM).append(identityNumber.value).append(" "));

        descriptor.getBrand().ifPresent(brand -> sb.append(PREFIX_BRAND).append(brand.value).append(" "));

        descriptor.getPhoneName().ifPresent(name -> sb.append(PREFIX_PHONE_NAME).append(name.fullName).append(" "));

        descriptor.getColour().ifPresent(colour -> sb.append(PREFIX_COLOUR).append(colour.value).append(" "));

        descriptor.getCapacity().ifPresent(capacity -> sb.append(PREFIX_CAPACITY).append(capacity.value
                .substring(0, capacity.value.length() - 2)).append(" "));

        descriptor.getCost().ifPresent(cost -> sb.append(PREFIX_COST).append(cost.value).append(" "));

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
