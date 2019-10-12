package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.diary.Diary;

/**
 * A utility class for Diary.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code diary}.
     */
    public static String getAddCommand(Diary diary) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(diary);
    }

    /**
     * Returns the part of command string for the given {@code diary}'s details.
     */
    public static String getPersonDetails(Diary diary) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + diary.getName().fullName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        return sb.toString();
    }
}
