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
import seedu.planner.model.accommodation.Accommodation;

/**
 * Views the specified accommodation.
 */
public class ViewAccommodationCommand extends ViewCommand {

    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Opens the specified accommodation on the info tab or "
                    + "simply opens the accommodation side panel",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " INDEX",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 3"
    );

    public static final String MESSAGE_SUCCESS = "Opened the accommodation tab!";

    private final Index index;

    /**
     * Creates an ViewAccommodationCommand to view the specific accommodation of {@code index}
     */
    public ViewAccommodationCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Accommodation> lastShownList = model.getFilteredAccommodationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACCOMMODATION_DISPLAYED_INDEX);
        }

        return new CommandResult(MESSAGE_SUCCESS,
                new ResultInformation[]{
                    new ResultInformation(
                        lastShownList.get(index.getZeroBased()),
                        index,
                        ""
                    )
                },
                new UiFocus[] {UiFocus.ACCOMMODATION, UiFocus.INFO});
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewAccommodationCommand);
    }
}
