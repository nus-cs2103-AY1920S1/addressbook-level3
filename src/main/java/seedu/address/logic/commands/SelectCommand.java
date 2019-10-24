package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.IdentificationNumber;
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

    private final int targetId;

    public SelectCommand(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        IdentificationNumber targetIdNum = IdentificationNumber.customGenerateId("B",
            targetId);
        if (!IdentificationNumber.isExistingIdentificationNumber(targetIdNum)) {
            model.setSelectedBody(null);
            if (targetId != Integer.MAX_VALUE) {
                throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
            }
        }
        Body selectedBody = (Body) targetIdNum.getMapping();
        model.setSelectedBody(selectedBody);
        return new CommandResult(String.format(MESSAGE_SELECT_BODY_SUCCESS, targetId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectCommand // instanceof handles nulls
            && targetId == ((SelectCommand) other).targetId); // state check
    }
}
//@@ shaoyi1997-reused
