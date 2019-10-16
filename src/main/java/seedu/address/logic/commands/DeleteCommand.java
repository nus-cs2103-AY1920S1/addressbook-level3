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
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an entity in Mortago. \n"
            + "Parameters: "
            + "IDENTIFICATION NUMBER";

    public static final String MESSAGE_DELETE_ENTITY_SUCCESS = "Deleted Entity: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid deleting this entity: %1$s";

    private final IdentificationNumber targetIdNum;
    private final String entityType;

    private Entity entityToDelete;
    private boolean isRedoable = false;

    public DeleteCommand(IdentificationNumber targetIdNum) {
        this.targetIdNum = targetIdNum;
        this.entityType = targetIdNum.toString().charAt(0) + "";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<? extends Entity> lastShownList = model.getFilteredEntityList(entityType);

        if (!IdentificationNumber.isExistingIdentificationNumber(targetIdNum) && !isRedoable) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
        }

        entityToDelete = lastShownList.get(targetIdNum.getIdNum() - 1);
        model.deleteEntity(entityToDelete);
        targetIdNum.removeMapping();
        setUndoable();
        isRedoable = true;
        model.addExecutedCommand(this);
        return new CommandResult(String.format(MESSAGE_DELETE_ENTITY_SUCCESS, entityToDelete));
    }

    //@@author ambervoong
    /**
     * Undoes the effects of the DeleteCommand. Only can be executed if this command was previously executed before.
     * @return result of undoing the command.
     */
    @Override
    public CommandResult undo(Model model) throws CommandException {
        if (!(getCommandState().equals(UndoableCommandState.UNDOABLE))) {
            throw new CommandException(MESSAGE_NOT_EXECUTED_BEFORE);
        }

        try {
            model.addEntity(entityToDelete);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }

        setRedoable();
        model.addUndoneCommand(this);

        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, entityToDelete));
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIdNum.equals(((DeleteCommand) other).targetIdNum)); // state check
    }
}
