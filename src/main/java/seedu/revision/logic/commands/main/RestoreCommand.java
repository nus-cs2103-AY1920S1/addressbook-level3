package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.model.Model;

/**
 * Format Restore instructions for every command for display.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String SHOWING_CONFIRMATION_MESSAGE = " ";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult().withFeedBack(SHOWING_CONFIRMATION_MESSAGE).withRestore(true)
                .withModel(model).build();
    }
}
