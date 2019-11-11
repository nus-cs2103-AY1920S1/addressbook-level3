package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;

import seedu.revision.logic.commands.Command;
import seedu.revision.model.Model;
import seedu.revision.model.RevisionTool;

/**
 * Clears the revision tool.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Test bank has been cleared!";
    public static final String MESSAGE_ADDITIONAL_COMMAND_BEHIND = "Unexpected trailing command!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setRevisionTool(new RevisionTool());
        return new CommandResultBuilder().withFeedBack(MESSAGE_SUCCESS).build();
    }
}
