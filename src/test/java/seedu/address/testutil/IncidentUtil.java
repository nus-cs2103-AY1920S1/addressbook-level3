package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;

import seedu.address.logic.commands.EditCommand;


/**
 * A utility class for Incident.
 */
public class IncidentUtil {
    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditIncidentDetails(EditCommand.EditIncident editor) {
        StringBuilder sb = new StringBuilder();
        editor.getDistrict().ifPresent(district -> sb.append(PREFIX_DISTRICT).append(district.districtNum).append(" "));
        editor.getDesc().ifPresent(desc -> sb.append(PREFIX_DESCRIPTION).append(desc.toString()).append(" "));
        editor.getDateTime().ifPresent(dateTime -> sb.append(PREFIX_DATETIME).append(dateTime.toString()).append(" "));
        editor.getCaller().ifPresent(caller -> sb.append(PREFIX_CALLER).append(caller.value).append(" "));
        return sb.toString();
    }
}
