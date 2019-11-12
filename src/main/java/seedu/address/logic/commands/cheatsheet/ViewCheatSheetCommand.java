package seedu.address.logic.commands.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.VIEW;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.CheatSheetCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheet;

/**
 * Views a cheatsheet identified by its displayed index.
 */
public class ViewCheatSheetCommand extends Command {

    public static final String COMMAND_WORD = VIEW;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a cheatsheet.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String VIEW_CHEATSHEET_SUCCESS = "Viewing cheatsheet: ";

    private final Index targetIndex;

    private final Logger logger = LogsCenter.getLogger(ViewCheatSheetCommand.class.getName());

    public ViewCheatSheetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        logger.info("View cheatsheet command created.");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CheatSheet> lastShownList = model.getFilteredCheatSheetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
        }

        CheatSheet cheatSheet = lastShownList.get(targetIndex.getZeroBased());
        return new CheatSheetCommandResult(
                String.format(VIEW_CHEATSHEET_SUCCESS + cheatSheet.getTitle(), cheatSheet), Optional.of(cheatSheet));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCheatSheetCommand // instanceof handles nulls
                && targetIndex.equals(((ViewCheatSheetCommand) other).targetIndex)); // state check
    }
}
