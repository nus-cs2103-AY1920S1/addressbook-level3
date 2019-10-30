package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.ExpandCommandResult;
import seedu.mark.model.Model;
import seedu.mark.storage.Storage;

/**
 * Collapses the folders by a certain amount.
 */
public class CollapseCommand extends Command {

    public static final String COMMAND_WORD = "collapse";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Collapses the folder hierarchy.\n"
            + "If not specified, the default level to collapse by is 1.\n"
            + "Parameters: [LEVEL] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COLLAPSE_FOLDER_ACKNOWLEDGEMENT = "Collapsing folders by %1$d level(s)";

    private final int levelsToCollapse;

    public CollapseCommand(int levelsToCollapse) {
        this.levelsToCollapse = levelsToCollapse;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) {
        requireAllNonNull(model, storage);

        return new ExpandCommandResult(
                String.format(MESSAGE_COLLAPSE_FOLDER_ACKNOWLEDGEMENT, levelsToCollapse), -levelsToCollapse);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CollapseCommand // instanceof handles nulls
                && levelsToCollapse == (((CollapseCommand) other).levelsToCollapse)); // state check
    }

}
