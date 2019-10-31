package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;

/**
 * Views the specified accommodation.
 */
public class ViewAccommodationCommand extends ViewCommand {

    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Opens the specified accommodation on the info tab.",
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
        return new CommandResult(MESSAGE_SUCCESS,
                new ResultInformation[]{
                    new ResultInformation(
                        model.getFilteredAccommodationList().get(index.getZeroBased()),
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
