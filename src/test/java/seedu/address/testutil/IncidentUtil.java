package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CALLER_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;

import seedu.address.logic.commands.EditIncidentCommand;


/**
 * A utility class for Incident.
 */
public class IncidentUtil {
    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditIncidentDetails(EditIncidentCommand.EditIncident editor) {
        StringBuilder sb = new StringBuilder();
        editor.getDistrict().ifPresent(district ->
                sb.append(PREFIX_DISTRICT).append(district.getDistrictNum()).append(" "));
        editor.getDesc().ifPresent(desc -> sb.append(PREFIX_DESCRIPTION).append(desc.toString()).append(" "));
        editor.getCaller().ifPresent(caller -> sb.append(PREFIX_CALLER_NUMBER).append(caller.value).append(" "));
        return sb.toString();
    }
}
