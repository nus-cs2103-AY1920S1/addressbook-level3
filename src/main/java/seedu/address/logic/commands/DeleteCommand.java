package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an entity in Mortago. \n"
            + "Parameters: "
            + "IDENTIFICATION NUMBER";

    public static final String MESSAGE_DELETE_ENTITY_SUCCESS = "Deleted Entity: %1$s";

    private final IdentificationNumber targetIdNum;
    private final String entityType;

    public DeleteCommand(IdentificationNumber targetIdNum) {
        this.targetIdNum = targetIdNum;
        this.entityType = targetIdNum.toString().charAt(0) + "";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<? extends Entity> lastShownList = model.getFilteredEntityList(entityType);

        if (!IdentificationNumber.isExistingidentificationNumber(targetIdNum)) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
        }

        Entity entityToDelete = lastShownList.get(targetIdNum.getIdNum());
        model.deleteEntity(entityToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ENTITY_SUCCESS, entityToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIdNum.equals(((DeleteCommand) other).targetIdNum)); // state check
    }
}
