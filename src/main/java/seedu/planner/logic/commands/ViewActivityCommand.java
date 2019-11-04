package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.activity.Activity;

/**
 * Views the specified activity.
 */
public class ViewActivityCommand extends ViewCommand {

    public static final String SECOND_COMMAND_WORD = "activity";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Opens the specified activity on the info tab or simply opens"
                    + " the contact side panel",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " [INDEX]",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 1"
    );

    public static final String MESSAGE_SUCCESS = "Opened the activity tab!";

    private final Index index;

    /**
     * Creates an ViewActivityCommand to view the specific activity of {@code index}
     */
    public ViewActivityCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        return new CommandResult(MESSAGE_SUCCESS,
                new ResultInformation[]{
                    new ResultInformation(
                            lastShownList.get(index.getZeroBased()),
                        index,
                        ""
                    )
                },
                new UiFocus[] {UiFocus.ACTIVITY, UiFocus.INFO});
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewActivityCommand);
    }
}
