package seedu.ezwatchlist.testutil;

import java.util.Set;

import seedu.ezwatchlist.logic.commands.AddCommand;
import seedu.ezwatchlist.logic.commands.EditCommand.EditShowDescriptor;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Show;

import static seedu.ezwatchlist.logic.parser.CliSyntax.*;

/**
 * A utility class for Show.
 */
public class ShowUtil {

    /**
     * Returns an add command string for adding the {@code show}.
     */
    public static String getAddCommand(Show show) {
        return AddCommand.COMMAND_WORD + " " + getShowDetails(show);
    }

    /**
     * Returns the part of command string for the given {@code show}'s details.
     */
    public static String getShowDetails(Show show) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + show.getName().toString() + " ");
        sb.append(PREFIX_DESCRIPTION + show.getDescription().toString() + " ");
        sb.append(PREFIX_IS_WATCHED +show.isWatched().toString() + " ");
        sb.append(PREFIX_DATE_OF_RELEASE + show.getDateOfRelease().value + " ");
        sb.append(PREFIX_RUNNING_TIME + show.getRunningTime().toString() + " ");
        show.getActors().stream().forEach(
            s -> sb.append(PREFIX_ACTOR + s.actorName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditShowDescriptor}'s details.
     */
    public static String getEditShowDescriptorDetails(EditShowDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.showName).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION).append(description).append(" "));
        descriptor.getIsWatched().ifPresent(isWatched -> sb.append(PREFIX_IS_WATCHED).append(isWatched.value).append(" "));
        descriptor.getDateOfRelease().ifPresent(date -> sb.append(PREFIX_DATE_OF_RELEASE).append(date.value).append(" "));
        descriptor.getRunningTime().ifPresent(runningTime -> sb.append(PREFIX_RUNNING_TIME).append(runningTime).append(" "));
        if (descriptor.getActors().isPresent()) {
            Set<Actor> Actors = descriptor.getActors().get();
            if (Actors.isEmpty()) {
                sb.append(PREFIX_ACTOR);
            } else {
                Actors.forEach(s -> sb.append(PREFIX_ACTOR).append(s.actorName).append(" "));
            }
        }
        return sb.toString();
    }
}
