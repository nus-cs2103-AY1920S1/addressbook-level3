package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheet;

public class DeleteCheatSheetCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the cheatsheet identified by the index number used in the displayed cheatsheet list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Cheatsheet: %1$s";

    private final Index targetIndex;

    public DeleteCheatSheetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<CheatSheet> lastShownList = model.getFilteredCheatSheetList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        CheatSheet cheatsheetToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCheatSheet(cheatsheetToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, cheatsheetToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCheatSheetCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCheatSheetCommand) other).targetIndex)); // state check
    }
}
