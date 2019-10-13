package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an entity in Mortago. \n"
            + "Parameters: "
            + PREFIX_FLAG
            + "b / w / f "
            + "IDENTIFICATION NUMBER"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FLAG
            + "w "
            + "5";

    public static final String MESSAGE_DELETE_ENTITY_SUCCESS = "Deleted Entity: %1$s";

    private final Index targetIndexNum;
    private final String entityType;

    public DeleteCommand(Index targetIndexNum, String entityType) {
        this.targetIndexNum = targetIndexNum;
        this.entityType = entityType.toUpperCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        IdentificationNumber targetIdNum = IdentificationNumber.customGenerateId(entityType,
                targetIndexNum.getZeroBased());

        Entity entityToDelete = null;

        if (entityType.equals("B")) {
            List<Body> lastShownList = model.getFilteredBodyList();
            for (Body body : lastShownList) {
                if (body.getBodyIdNum().equals(targetIdNum)) {
                    entityToDelete = body;
                    break;
                }
            }
        } else if (entityType.equals("W")) {
            List<Worker> lastShownList = model.getFilteredWorkerList();
            for (Worker worker : lastShownList) {
                if (worker.getWorkerIdNum().equals(targetIdNum)) {
                    entityToDelete = worker;
                    break;
                }
            }
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_FLAG);
        }

        if (entityToDelete != null) {
            model.deleteEntity(entityToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_ENTITY_SUCCESS, entityToDelete));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndexNum.equals(((DeleteCommand) other).targetIndexNum)); // state check
    }
}
