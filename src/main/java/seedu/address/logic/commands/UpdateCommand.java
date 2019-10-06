package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIRST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIDDLE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BODIES;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.logic.parser.utility.UpdateEntityDescriptor;
import seedu.address.logic.parser.utility.UpdateWorkerDescriptor;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;


//@@author ambervoong
/**
 * Updates the details of an existing body, worker, or fridge in Mortago.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the details of a body, worker, or fridge, identified "
            + "by the identification number that was automatically assigned to the entity. "
            + "Existing fields will be overwritten by the input values.\n"
            + "Details that were not changed in this command will remain the same as before."
            + "Compulsory fields: "
            + PREFIX_FLAG + "w, b, or f "
            + PREFIX_IDENTIFICATION_NUMBER + "IDENTIFICATION NUMBER \n"
            + "Optional fields are listed below. \n"
            + "Update fields for a Body object: \n"
            + PREFIX_FIRST_NAME + "FIRST_NAME "
            + PREFIX_MIDDLE_NAME + "MIDDLE_NAME "
            + PREFIX_LAST_NAME + "LAST_NAME "
            + PREFIX_SEX + "SEX "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_RELIGION + "RELIGION "
            + PREFIX_CAUSE_OF_DEATH + "CAUSE_OF_DEATH "
            + PREFIX_ORGANS_FOR_DONATION + "ORGANS_FOR_DONATION "
            + PREFIX_STATUS + "STATUS "
            + PREFIX_FRIDGE_ID + "FRIDGE_ID "
            + PREFIX_DATE_OF_BIRTH + "DATE_OF_BIRTH "
            + PREFIX_DATE_OF_DEATH + "DATE_OF_DEATH "
            + PREFIX_NAME_NOK + "NAME_NOK "
            + PREFIX_RELATIONSHIP + "RELATIONSHIP "
            + PREFIX_PHONE_NOK + "PHONE_NOK "
            + "\nUpdate fields for a Worker object: \n"
            + PREFIX_PHONE + "PHONE "
            + PREFIX_SEX + "SEX "
            + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH "
            + PREFIX_DATE_JOINED + "DATE JOINED "
            + PREFIX_DESIGNATION + "DESIGNATION "
            + PREFIX_EMPLOYMENT_STATUS + "EMPLOYMENT STATUS"
            + "\nUpdate fields for a Fridge object: \n" // Todo: update when fridge class is merged in.
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FLAG + "b "
            + PREFIX_IDENTIFICATION_NUMBER + " 1 "
            + PREFIX_FIRST_NAME + " Jane"
            + PREFIX_LAST_NAME + " Cthulhu";

    public static final String MESSAGE_UPDATE_ENTITY_SUCCESS = "Edited Entity: %1$s";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid edits to entity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_ENTITY_NOT_FOUND = "The entity with the specified identification number"
            + "was not found.";


    private final IdentificationNumber id;
    private final UpdateEntityDescriptor updateEntityDescriptor;

    private Entity entity;
    private UpdateEntityDescriptor originalEntityDescriptor;

    /**
     * Creates an UpdateCommand to update one or more fields in the specified {@code Body},
     * {@code Worker} or {@code Fridge}.
     * @param id the identification number of the entity to update
     * @param updateEntityDescriptor details to edit the entity with
     */
    public UpdateCommand(IdentificationNumber id, UpdateEntityDescriptor updateEntityDescriptor) {
        requireNonNull(id);
        requireNonNull(updateEntityDescriptor);

        this.id = id;
        this.updateEntityDescriptor = updateEntityDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        this.entity = getEntityFromId(model, id, updateEntityDescriptor);
        if (!model.hasEntity(entity)) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }

        this.originalEntityDescriptor = saveOriginalFields(model, entity);

        model.setEntity(entity, updateEntityDescriptor.apply(entity));
        model.updateFilteredBodyList(PREDICATE_SHOW_ALL_BODIES);
        return new CommandResult(String.format(MESSAGE_UPDATE_ENTITY_SUCCESS, entity));
    }

    public Entity getEntityFromId(Model model, IdentificationNumber id, UpdateEntityDescriptor descriptor)
            throws CommandException {
        if (descriptor instanceof UpdateBodyDescriptor) {
            List<Body> lastShownList = model.getFilteredBodyList();
            for (Body body : lastShownList) {
                if (body.getBodyIdNum().equals(id)) {
                    return body;
                }
            }
        } else if (descriptor instanceof UpdateWorkerDescriptor) {
            List<Worker> lastShownList = model.getFilteredWorkerList();
            for (Worker worker : lastShownList) {
                if (worker.getWorkerIdNum().equals(id)) {
                    return worker;
                }
            }
        }
        // todo: add support for worker, fridge
        throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_ID);
    }

    /**
     * Saves the original fields of the given {@code Entity} into an UpdateEntityDescriptor.
     * @param model model of Mortago
     * @param entity the entity to save
     * @return an UpdateEntityDescriptor with the entity's current fields
     * @throws CommandException
     */
    public UpdateEntityDescriptor saveOriginalFields(Model model, Entity entity) throws CommandException {
        if (entity instanceof Body) {
            return new UpdateBodyDescriptor((Body) entity);
        } else if (entity instanceof Worker) {
            return new UpdateWorkerDescriptor((Worker) entity);
        }
        // todo: add support for fridge
        throw new CommandException("Could not find original entity.");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        // state check
        UpdateCommand e = (UpdateCommand) other;
        return entity.equals(e.entity)
                && updateEntityDescriptor.equals(e.updateEntityDescriptor)
                && originalEntityDescriptor.equals(e.originalEntityDescriptor);
    }
}
