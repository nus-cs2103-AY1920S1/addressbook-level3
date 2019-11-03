package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.ExpandCommandResult;
import seedu.mark.model.Model;
import seedu.mark.storage.Storage;

/**
 * Expands the folders by a certain amount.
 */
public class ExpandCommand extends Command {

    public static final String COMMAND_WORD = "expand";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Expands the folder hierarchy.\n"
            + "If not specified, the default level to expand by is 1.\n"
            + "Parameters: [LEVEL] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_EXPAND_FOLDER_ACKNOWLEDGEMENT = "Expanding folders by %1$d level(s)";

    private final int levelsToExpand;

    public ExpandCommand(int levelsToExpand) {
        this.levelsToExpand = levelsToExpand;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) {
        requireAllNonNull(model, storage);

        return new ExpandCommandResult(
                String.format(MESSAGE_EXPAND_FOLDER_ACKNOWLEDGEMENT, levelsToExpand), levelsToExpand);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpandCommand // instanceof handles nulls
                && levelsToExpand == (((ExpandCommand) other).levelsToExpand)); // state check
    }

}
