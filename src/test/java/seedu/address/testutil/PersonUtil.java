package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + person.getTitle().title + " ");
        sb.append(PREFIX_CONTENT + person.getContent().content + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditNoteDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditCommand.EditNoteDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(name -> sb.append(PREFIX_TITLE).append(name.title).append(" "));
        descriptor.getContent().ifPresent(address -> sb.append(PREFIX_CONTENT).append(address.content).append(" "));
        return sb.toString();
    }
}
