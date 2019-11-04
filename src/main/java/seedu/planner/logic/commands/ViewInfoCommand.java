package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;

/**
 * Views the information tab.
 */
public class ViewInfoCommand extends ViewCommand {

    public static final String SECOND_COMMAND_WORD = "info";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Opens the information tab.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            COMMAND_WORD + " " + SECOND_COMMAND_WORD
    );

    public static final String MESSAGE_SUCCESS = "Opened the info tab!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, new UiFocus[] {UiFocus.INFO});
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewInfoCommand);
    }
}
