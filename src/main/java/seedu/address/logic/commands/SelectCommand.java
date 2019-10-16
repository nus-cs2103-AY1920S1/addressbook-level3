package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.body.Body;

//@@ shaoyi1997-reused
//Reused from SE-EDU Address Book Level 4
/**
 * Selects a body identified using it's displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the body identified by the index number used in the displayed body list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_BODY_SUCCESS = "Selected Body: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Body> filteredBodyList = model.getFilteredBodyList();

        if (targetIndex.getZeroBased() >= filteredBodyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
        }

        model.setSelectedBody(filteredBodyList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_BODY_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectCommand // instanceof handles nulls
            && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
//@@ shaoyi1997-reused
