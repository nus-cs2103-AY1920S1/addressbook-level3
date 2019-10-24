package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_FRIDGE_DOES_NOT_EXIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.logic.parser.utility.UpdateEntityDescriptor;
import seedu.address.logic.parser.utility.UpdateFridgeDescriptor;
import seedu.address.logic.parser.utility.UpdateWorkerDescriptor;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;


//@@author ambervoong

/**
 * Updates the details of an existing body, worker, or fridge in Mortago.
 */
public class UpdateCommand extends UndoableCommand {

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
            + PREFIX_NAME + "NAME "
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
            + PREFIX_PHONE_NUMBER + "PHONE "
            + PREFIX_SEX + "SEX "
            + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH "
            + PREFIX_DATE_JOINED + "DATE JOINED "
            + PREFIX_DESIGNATION + "DESIGNATION "
            + PREFIX_EMPLOYMENT_STATUS + "EMPLOYMENT STATUS"
            + "\nUpdate fields for a Fridge object: \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FLAG + "b "
            + PREFIX_IDENTIFICATION_NUMBER + " 1 "
            + PREFIX_BODY + " 2";

    public static final String MESSAGE_UPDATE_ENTITY_SUCCESS = "Edited Entity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid updates to entity: %1$s";

    private final IdentificationNumber id;
    private final UpdateEntityDescriptor updateEntityDescriptor;

    private Entity entity;
    private UpdateEntityDescriptor originalEntityDescriptor;

    //@@author arjavibahety
    private boolean updateFromNotif;
    //@@author

    /**
     * Creates an UpdateCommand to update one or more fields in the specified {@code Body},
     * {@code Worker} or {@code Fridge}.
     *
     * @param id                     the identification number of the entity to update
     * @param updateEntityDescriptor details to edit the entity with
     */
    public UpdateCommand(IdentificationNumber id, UpdateEntityDescriptor updateEntityDescriptor) {
        requireNonNull(id);
        requireNonNull(updateEntityDescriptor);

        this.id = id;
        this.updateEntityDescriptor = updateEntityDescriptor;
        this.updateFromNotif = false;
    }


    /**
     * Saves the original fields of the given {@code Entity} into an UpdateEntityDescriptor.
     *
     * @param entity the entity to save
     * @return an UpdateEntityDescriptor with the entity's current fields
     * @throws CommandException if the Entity is not a Body, Worker, or Fridge.
     */
    public static UpdateEntityDescriptor saveOriginalFields(Entity entity) throws CommandException {
        if (entity instanceof Body) {
            return new UpdateBodyDescriptor((Body) entity);
        } else if (entity instanceof Worker) {
            return new UpdateWorkerDescriptor((Worker) entity);
        } else if (entity instanceof Fridge) {
            return new UpdateFridgeDescriptor((Fridge) entity);
        } else {
            throw new CommandException("Could not find original entity.");
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        this.entity = getEntityFromId(model, id, updateEntityDescriptor);
        if (!model.hasEntity(entity)) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }
        if (updateEntityDescriptor instanceof UpdateFridgeDescriptor) {
            try {
                UpdateFridgeDescriptor fridgeDescriptor = (UpdateFridgeDescriptor) updateEntityDescriptor;
                getBodyFromId(model, fridgeDescriptor.getBodyId().orElse(null), fridgeDescriptor);
            } catch (CommandException e) {
                return new CommandResult(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_ID);
            }
        }

        try {
            this.originalEntityDescriptor = saveOriginalFields(entity);
            if (originalEntityDescriptor instanceof UpdateBodyDescriptor) {
                UpdateBodyDescriptor originalBodyDescriptor = (UpdateBodyDescriptor) originalEntityDescriptor;
                UpdateBodyDescriptor updateBodyDescriptor = (UpdateBodyDescriptor) updateEntityDescriptor;
                if (!originalBodyDescriptor.getFridgeId().equals(updateBodyDescriptor.getFridgeId())) {
                    handleUpdatingFridgeAndEntity(model, originalBodyDescriptor, updateBodyDescriptor);
                } else {
                    model.setEntity(entity, updateEntityDescriptor.apply(entity));
                }
            } else {
                model.setEntity(entity, updateEntityDescriptor.apply(entity));
            }
            SelectCommand selectCommand = new SelectCommand(Integer.MAX_VALUE);
            selectCommand.execute(model);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }


        //@@author arjavibahety
        if (!updateFromNotif) {
            setUndoable();
            model.addExecutedCommand(this);
        }
        //@@author

        return new CommandResult(String.format(MESSAGE_UPDATE_ENTITY_SUCCESS, entity));
    }

    //@@author arjavibahety

    /**
     * Assigns body to the new fridge when fridgeId is updated and removes it from the old fridge.
     *
     * @param model                  refers to the AddressBook model.
     * @param originalBodyDescriptor refers to the original description of the body.
     * @param updateBodyDescriptor   refers to the updated description of the body.
     * @throws CommandException when a fridge for the new fridgeId does not exist.
     */
    private void handleUpdatingFridgeAndEntity(Model model, UpdateBodyDescriptor originalBodyDescriptor,
                                               UpdateBodyDescriptor updateBodyDescriptor) throws CommandException {
        List<Fridge> fridgeList = model.getFilteredFridgeList();
        Fridge originalFridge = null;
        Fridge updatedFridge = null;
        boolean initallyNoFridge = true;
        for (Fridge fridge : fridgeList) {
            if (Optional.ofNullable(fridge.getIdNum()).equals(originalBodyDescriptor.getFridgeId())) {
                originalFridge = fridge;
                initallyNoFridge = false;
            }
            if (!(updateBodyDescriptor.getFridgeId() == null)) {
                if (fridge.getIdNum().equals(updateBodyDescriptor.getFridgeId().get())) {
                    updatedFridge = fridge;
                }
                if (Optional.ofNullable(fridge.getIdNum()).equals(updateBodyDescriptor.getFridgeId())) {
                    updatedFridge = fridge;
                }
            }
        }

        if ((originalFridge != null && updatedFridge != null)) {
            originalFridge.setBody(null);
            updatedFridge.setBody((Body) entity);
            model.setEntity(entity, updateEntityDescriptor.apply(entity));
        } else if (initallyNoFridge) {
            updatedFridge.setBody((Body) entity);
            model.setEntity(entity, updateEntityDescriptor.apply(entity));
        } else if (updatedFridge == null) {
            throw new CommandException(MESSAGE_FRIDGE_DOES_NOT_EXIST);
        }

    }
    //@@author

    /**
     * Undoes the effects of the UpdateCommand. Only can be executed if this command was previously executed before.
     *
     * @return result of undoing the command.
     */
    @Override
    public CommandResult undo(Model model) throws CommandException {
        if (!(getCommandState().equals(UndoableCommandState.UNDOABLE))) {
            throw new CommandException(MESSAGE_NOT_EXECUTED_BEFORE);
        }
        try {
            model.setEntity(entity, originalEntityDescriptor.apply(entity));
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }
        setRedoable();
        model.addUndoneCommand(this);
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, entity));
    }

    public Entity getEntityFromId(Model model, IdentificationNumber id, UpdateEntityDescriptor descriptor)
            throws CommandException {
        if (descriptor instanceof UpdateBodyDescriptor) {
            List<Body> lastShownList = model.getFilteredBodyList();
            for (Body body : lastShownList) {
                if (body.getIdNum().equals(id)) {
                    return body;
                }
            }
        } else if (descriptor instanceof UpdateWorkerDescriptor) {
            List<Worker> lastShownList = model.getFilteredWorkerList();
            for (Worker worker : lastShownList) {
                if (worker.getIdNum().equals(id)) {
                    return worker;
                }
            }
        } else if (descriptor instanceof UpdateFridgeDescriptor) {
            List<Fridge> lastShownList = model.getFilteredFridgeList();
            for (Fridge fridge : lastShownList) {
                if (fridge.getIdNum().equals(id)) {
                    return fridge;
                }
            }
        }

        throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_ID);
    }

    /**
     * Gets a Body in Mortago according to a given Identification Number and add it to the UpdateFridgeDescriptor, if
     * present.
     *
     * @param model      the current model of the program.
     * @param id         an identification number.
     * @param descriptor an UpdateFridgeDescriptor containing changes to a Fridge object.
     * @return an UpdateFridgeDescriptor
     * @throws CommandException if there is no Body object with the given identification number.
     */
    public UpdateFridgeDescriptor getBodyFromId(Model model, IdentificationNumber id, UpdateFridgeDescriptor descriptor)
            throws CommandException {
        if (id == null) {
            return descriptor;
        }
        List<Body> lastShownList = model.getFilteredBodyList();
        for (Body body : lastShownList) {
            if (body.getIdNum().equals(id)) {
                descriptor.setNewBody(body);
                return descriptor;
            }
        }
        throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_ID);
    }

    //@@author arjavibahety
    public void setUpdateFromNotif(boolean updateFromNotif) {
        this.updateFromNotif = updateFromNotif;
    }
    //@@

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
        return id.equals(e.id)
                && updateEntityDescriptor.equals(e.updateEntityDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, updateEntityDescriptor);
    }
}
