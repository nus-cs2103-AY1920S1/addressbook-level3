package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;

/**
 * Views the contact tab in the side panel.
 */
public class ViewContactCommand extends ViewCommand {

    public static final String SECOND_COMMAND_WORD = "contact";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Opens the contact tab on the side panel.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            COMMAND_WORD + " " + SECOND_COMMAND_WORD
    );

    public static final String MESSAGE_SUCCESS = "Opened the contact tab!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, new UiFocus[] {UiFocus.CONTACT});
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewContactCommand);
    }
}
