package seedu.address.testutil;

import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_EMAIL;
import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_NAME;
import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    //    /**
    //     * Returns an add command string for adding the {@code person}.
    //     */
    //    public static String getAddCommand(Record record) {
    //        return AddCommand.COMMAND_WORD + " " + getRecordDetails();
    //    }
    //
    //    /**
    //     * Returns the part of command string for the given {@code person}'s details.
    //     */
    //    public static String getRecordDetails() {
    //        StringBuilder sb = new StringBuilder();
    //        sb.append(PREFIX_BLOODSUGAR_CONCENTRATION + "12.34" + " ");
    //        sb.append(PREFIX_DATETIME + "datetime" + " ");
    //        return sb.toString();
    //    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));

        return sb.toString();
    }
}
