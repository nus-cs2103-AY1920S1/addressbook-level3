//package seedu.guilttrip.testutil;
//
//import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
//import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;
//
//import java.util.Set;
//
//import seedu.guilttrip.logic.commands.addcommands.AddCommand;
//import seedu.guilttrip.logic.commands.editcommands.EditCommand.EditPersonDescriptor;
//import seedu.guilttrip.model.entry.Person;
//import seedu.guilttrip.model.tag.Tag;
//
///**
// * A utility class for Person.
// */
//public class PersonUtil {
//
//    /**
//     * Returns an add command string for adding the {@code entry}.
//     */
//    public static String getAddCommand(Person entry) {
//        return AddCommand.COMMAND_WORD + " " + getPersonDetails(entry);
//    }
//
//    /**
//     * Returns the part of command string for the given {@code entry}'s details.
//     */
//    public static String getPersonDetails(Person entry) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(PREFIX_DESC + entry.getName().fullDesc + " ");
//        sb.append(PREFIX_PHONE + entry.getPhone().value + " ");
//        sb.append(PREFIX_EMAIL + entry.getEmail().value + " ");
//        sb.append(PREFIX_ADDRESS + entry.getAddress().value + " ");
//        entry.getTags().stream().forEach(
//            s -> sb.append(PREFIX_TAG + s.tagName + " ")
//        );
//        return sb.toString();
//    }
//
//    /**
//     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
//     */
//    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
//        StringBuilder sb = new StringBuilder();
//        descriptor.getName().ifPresent(name -> sb.append(PREFIX_DESC).append(name.fullDesc).append(" "));
//        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
//        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
//        descriptor.getAddress().ifPresent(guilttrip -> sb.append(PREFIX_ADDRESS).append(guilttrip.value).append(" "));
//        if (descriptor.getTags().isPresent()) {
//            Set<Tag> tags = descriptor.getTags().get();
//            if (tags.isEmpty()) {
//                sb.append(PREFIX_TAG);
//            } else {
//                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
//            }
//        }
//        return sb.toString();
//    }
//}
