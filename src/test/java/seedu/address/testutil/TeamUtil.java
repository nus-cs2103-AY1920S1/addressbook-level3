package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT_NAME;

import seedu.address.logic.commands.addcommand.AddTeamCommand;
import seedu.address.logic.commands.editcommand.EditTeamCommand.EditTeamDescriptor;
import seedu.address.model.entity.Team;

/**
 * Utility class for a team.
 */
public class TeamUtil {

    /**
     * Returns an add command string for adding the {@code team}.
     */
    public static String getAddCommand(Team team) {
        return AddTeamCommand.COMMAND_WORD + " " + getTeamDetails(team);
    }

    /**
     * Returns the part of command string for the given {@code team}'s details.
     */
    public static String getTeamDetails(Team team) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + team.getName().fullName + " ");
        sb.append(PREFIX_PROJECT_NAME + team.getProjectName().fullName + " ");
        sb.append(PREFIX_SUBJECT_NAME + team.getSubject().toString() + " ");
        sb.append(PREFIX_LOCATION + "" + team.getLocation().getTableNumber() + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMentorDescriptor}'s details.
     */
    public static String getEditMentorDescriptorDetails(EditTeamDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getProjectName().ifPresent(pn -> sb.append(PREFIX_PROJECT_NAME).append(pn.fullName).append(" "));
        descriptor.getLocation().ifPresent(loc -> sb.append(PREFIX_LOCATION).append(loc.getTableNumber()).append(" "));
        descriptor.getSubject().ifPresent(sub -> sb.append(PREFIX_SUBJECT_NAME).append(sub.toString()).append(" "));

        return sb.toString();
    }

}
