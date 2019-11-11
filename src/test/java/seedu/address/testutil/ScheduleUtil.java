package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Calendar;
import java.util.Set;

import seedu.address.logic.commands.addcommand.AddScheduleCommand;
import seedu.address.logic.commands.editcommand.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Schedule.
 */
public class ScheduleUtil {

    /**
     * Returns an add command string for adding the {@code schedule}.
     */
    public static String getAddCommand(Schedule schedule) {
        return AddScheduleCommand.COMMAND_WORD + " " + getScheduleDetails(schedule);
    }

    /**
     * Returns the part of command string for the given {@code schedule}'s details.
     */
    public static String getScheduleDetails(Schedule schedule) {
        StringBuilder sb = new StringBuilder();
        sb.append("1" + " ");
        sb.append(PREFIX_DATE + "2019.12.1" + " ");
        sb.append(PREFIX_TIME + "23.30" + " ");
        sb.append(PREFIX_VENUE + schedule.getVenue().toString() + " ");
        schedule.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditScheduleDescriptor}'s details.
     */
    public static String getEditScheduleDescriptorDetails(EditScheduleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();

        descriptor.getDate().ifPresent(date ->
                sb.append(PREFIX_DATE)
                        .append(String.format("%d.%d.%d", date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1,
                                date.get(Calendar.DAY_OF_MONTH)))
                        .append(" "));

        descriptor.getTime().ifPresent(time ->
                sb.append(PREFIX_TIME)
                        .append(String.format("%d.%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE)))
                        .append(" "));

        descriptor.getVenue().ifPresent(venue ->
                sb.append(PREFIX_VENUE).append(venue.toString()).append(" "));

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
