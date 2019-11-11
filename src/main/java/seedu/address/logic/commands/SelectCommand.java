package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;

//@@author shaoyi1997-reused
//Reused from SE-EDU Address Book Level 4
/**
 * Selects a body identified using it's identification number.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the body identified by the index number used in the displayed body list.\n"
        + "Please refer to the User Guide for more details on how to select a body";

    public static final String MESSAGE_SELECT_BODY_SUCCESS = "Selected Body: %1$s";
    public static final String MESSAGE_ID_MAPS_TO_NULL = "Error in retrieving body from an existing ID";

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
            model.setSelectedBody(null); // deselect any pre-existing selection of body

            // targetId == Integer.MAX_VALUE only when SelectCommand is implicitly executed within the app to deselect
            // targetId != Integer.MAX_VALUE when user supplies a non-existing ID
            if (targetId != Integer.MAX_VALUE) {
                throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
            }

            return new CommandResult("Deselected body.");
        } else {
            Body selectedBody = (Body) targetIdNum.getMapping();
            if (selectedBody == null) {
                LogsCenter.getLogger(SelectCommand.class).severe("Exisiting ID does not map to an existing body");
                throw new CommandException(MESSAGE_ID_MAPS_TO_NULL);
            }
            model.setSelectedBody(selectedBody);
            return new CommandResult(String.format(MESSAGE_SELECT_BODY_SUCCESS, targetId));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectCommand // instanceof handles nulls
            && targetId == ((SelectCommand) other).targetId); // state check
    }
}
//@@author
