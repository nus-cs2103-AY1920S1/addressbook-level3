package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.EventUtil.formatIndexVEventPair;

import java.util.List;

import org.apache.commons.math3.util.Pair;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.exceptions.VEventNotFoundException;

/**
 * Returns the index of event
 */
public class EventIndexCommand extends EventCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets Index of a Event\n"
            + "Parameters:\n"
            + "Example: event indexOf/cs2100 lecture";
    public static final String NO_EVENT = "There are no events in nJoy assistant.";
    public static final String MESSAGE_SUGGESTION_EVENT =
            "Could not find event specified. This is the closest event we can find: \n%s";
    private final String desiredEventName;

    /**
     * Find the index of event with the same eventName
     *
     * @param desiredEventName used to find event.
     */
    public EventIndexCommand(String desiredEventName) {
        requireNonNull(desiredEventName);

        this.desiredEventName = desiredEventName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Pair<Index, VEvent>> resultVEventIndexList = model.findVEvents(desiredEventName);
        if (resultVEventIndexList.isEmpty()) {
            try {
                Pair<Index, VEvent> suggestedEventPair = model.findMostSimilarVEvent(desiredEventName);
                return new CommandResult(generateSuggestionMessage(suggestedEventPair),
                        CommandResultType.SHOW_SCHEDULE);
            } catch (VEventNotFoundException ex) {
                throw new CommandException(NO_EVENT, ex);
            }

        } else {
            return new CommandResult(generateResultMessage(resultVEventIndexList), CommandResultType.SHOW_SCHEDULE);
        }
    }

    /**
     * Generates a result VEvent List success message. Which includes the index
     *
     * @param resultVEventIndexList a list of pair of VEvents and their indexes which have the same eventName
     */
    private String generateResultMessage(List<Pair<Index, VEvent>> resultVEventIndexList) {
        StringBuilder resultStringBuilder = new StringBuilder();
        for (Pair<Index, VEvent> indexVEventPair : resultVEventIndexList) {
            resultStringBuilder.append(formatIndexVEventPair(indexVEventPair));
        }
        return resultStringBuilder.toString();
    }

    /**
     * Generates a result VEvent suggestion message.
     *
     * @param resultVEventPair the index, vEvent pair that is to be shown to the user
     */
    private String generateSuggestionMessage(Pair<Index, VEvent> resultVEventPair) {
        return String.format(MESSAGE_SUGGESTION_EVENT, formatIndexVEventPair(resultVEventPair));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventIndexCommand)) {
            return false;
        }

        // state check
        EventIndexCommand e = (EventIndexCommand) other;
        return desiredEventName.equals(e.desiredEventName);
    }
}
