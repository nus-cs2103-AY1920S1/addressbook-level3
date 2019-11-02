package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_FRIDGE_DOES_NOT_EXIST;
import static seedu.address.logic.commands.AddCommand.NOTIF_PERIOD;
import static seedu.address.logic.commands.AddCommand.NOTIF_TIME_UNIT;
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
import static seedu.address.model.entity.body.BodyStatus.ARRIVED;
import static seedu.address.model.entity.body.BodyStatus.CONTACT_POLICE;

import java.util.ArrayList;
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
import seedu.address.model.notif.Notif;
import seedu.address.model.person.Name;


//@@author ambervoong
/**
 * Updates the details of an existing body or worker in Mortago.
 */
public class UpdateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "update";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the details of a body or worker, identified "
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
            + PREFIX_EMPLOYMENT_STATUS + "EMPLOYMENT STATUS";

    public static final String MESSAGE_UPDATE_ENTITY_SUCCESS = "Edited Entity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid updates to entity: %1$s";

    private final IdentificationNumber id;
    private final UpdateEntityDescriptor updateEntityDescriptor;
    private UpdateEntityDescriptor originalEntityDescriptor;

    private Entity entity;
    private Fridge originalFridge;
    private Fridge updatedFridge;
    private List<Notif> toDeleteNotif;


    /**
     * Creates an UpdateCommand to update one or more fields in the specified {@code Body} or
     * {@code Worker}.
     *
     * @param id                     the identification number of the entity to update
     * @param updateEntityDescriptor details to edit the entity with
     */
    public UpdateCommand(IdentificationNumber id, UpdateEntityDescriptor updateEntityDescriptor) {
        requireNonNull(id);
        requireNonNull(updateEntityDescriptor);

        this.id = id;
        this.updateEntityDescriptor = updateEntityDescriptor;
    }


    /**
     * Saves the original fields of the given {@code Entity} into an {@code UpdateEntityDescriptor}.
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

        //@@author arjavibahety
        try {
            this.originalEntityDescriptor = saveOriginalFields(entity);

            if (originalEntityDescriptor instanceof UpdateBodyDescriptor) {
                UpdateBodyDescriptor originalBodyDescriptor = (UpdateBodyDescriptor) originalEntityDescriptor;
                UpdateBodyDescriptor updateBodyDescriptor = (UpdateBodyDescriptor) updateEntityDescriptor;

                //@@author ambervoong
                if (!originalBodyDescriptor.getFridgeId().equals(updateBodyDescriptor.getFridgeId())
                    && updateBodyDescriptor.getFridgeId().isPresent()) {
                    handleUpdatingFridgeAndEntity(model, originalBodyDescriptor, updateBodyDescriptor);
                }
                //@@author

                if ((originalBodyDescriptor.getBodyStatus().equals(Optional.of(CONTACT_POLICE))
                        && !updateBodyDescriptor.getBodyStatus().equals(Optional.of(CONTACT_POLICE)))
                        || (originalBodyDescriptor.getBodyStatus().equals(Optional.of(ARRIVED))
                        && (!updateBodyDescriptor.getBodyStatus().equals(Optional.of(ARRIVED))
                            && !updateBodyDescriptor.getBodyStatus().equals(Optional.of(CONTACT_POLICE))))) {
                    handleRemovingNotifs(model, originalBodyDescriptor, updateBodyDescriptor);
                }

                if (!originalBodyDescriptor.getBodyStatus().equals(Optional.of(ARRIVED))
                        && updateBodyDescriptor.getBodyStatus().equals(Optional.of(ARRIVED))) {
                    addNotificationsForBody(model);
                }

            }
            //@@author

            model.setEntity(entity, updateEntityDescriptor.apply(entity));

            //@@author shaoyi1997
            SelectCommand selectCommand = new SelectCommand(Integer.MAX_VALUE);
            selectCommand.execute(model);
            //@@author

        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }

        setUndoable();
        model.addExecutedCommand(this);

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
        boolean initallyNoFridge = true;
        for (Fridge fridge : fridgeList) {
            if (Optional.ofNullable(fridge.getIdNum()).equals(originalBodyDescriptor.getFridgeId())) {
                this.originalFridge = fridge;
                initallyNoFridge = false;
            }
            if (!(updateBodyDescriptor.getFridgeId() == null)) {

                if (fridge.getIdNum().equals(updateBodyDescriptor.getFridgeId().get())) {
                    this.updatedFridge = fridge;
                }
                if (Optional.ofNullable(fridge.getIdNum()).equals(updateBodyDescriptor.getFridgeId())) {
                    this.updatedFridge = fridge;
                }
            }
        }

        if ((this.originalFridge != null && this.updatedFridge != null)) {
            this.originalFridge.setBody(null);
            this.updatedFridge.setBody((Body) entity);
            model.setEntity(entity, updateEntityDescriptor.apply(entity));
        } else if (initallyNoFridge) {
            this.updatedFridge.setBody((Body) entity);
            model.setEntity(entity, updateEntityDescriptor.apply(entity));
        } else if (this.updatedFridge == null) {
            throw new CommandException(MESSAGE_FRIDGE_DOES_NOT_EXIST);
        }

    }

    /**
     * Removes all the associated notifs when the status of a body is changed from CONTACT_POLICE.
     *
     * @param model                  refers to the AddressBook model.
     * @param originalBodyDescriptor refers to the original description of the body.
     * @param updateBodyDescriptor   refers to the updated description of the body.
     */
    private void handleRemovingNotifs(Model model, UpdateBodyDescriptor originalBodyDescriptor,
                                      UpdateBodyDescriptor updateBodyDescriptor) {
        List<Notif> notifList = model.getFilteredNotifList();
        this.toDeleteNotif = new ArrayList<>();
        Name bodyName = originalBodyDescriptor.getName().get();
        for (Notif notif : notifList) {
            if (notif.getBody().getName().equals(bodyName)) {
                toDeleteNotif.add(notif);
            }
        }

        for (Notif notif : toDeleteNotif) {
            model.deleteNotif(notif);
        }
        model.setEntity(entity, updateEntityDescriptor.apply(entity));
    }

    private void addNotificationsForBody(Model model) throws CommandException {
        Body body = (Body) entity;
        NotifCommand notifCommand = new NotifCommand(new Notif(body), NOTIF_PERIOD, NOTIF_TIME_UNIT);
        notifCommand.execute(model);
    }
    //@@author

    /**
     * Undoes the effects of the UpdateCommand. Only can be executed if this command was previously executed before.
     *
     * @param model the model of Mortago
     * @return result of undoing the command
     */
    @Override
    public CommandResult undo(Model model) throws CommandException {
        if (!(getCommandState().equals(UndoableCommandState.UNDOABLE))) {
            throw new CommandException(MESSAGE_NOT_EXECUTED_BEFORE);
        }
        try {
            model.setEntity(entity, originalEntityDescriptor.applyOriginal(entity));

            if (entity instanceof Body) {
                // Undo automated fridge changes
                Body body = (Body) originalEntityDescriptor.applyOriginal(entity);
                if ((originalFridge != null && updatedFridge != null)) {
                    originalFridge.setBody(body);
                    updatedFridge.setBody(null);
                } else if (originalFridge == null && updatedFridge != null) {
                    updatedFridge.setBody(null);
                    body.setFridgeId(null);
                }

                // Undo Notif removal
                if (toDeleteNotif != null) {
                    for (Notif n : toDeleteNotif) {
                        model.addNotif(n);
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }
        setRedoable();
        model.addUndoneCommand(this);
        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, entity));
    }

    /**
     * Returns an Entity given its IdentificationNumber if it exists in the {@code Model}.
     *
     * @param model      the model of Mortago
     * @param id         the id given
     * @param descriptor the descriptor given to UpdateCommmand, which is used to determine entity type
     * @return the entity in Mortago
     * @throws CommandException if the entity was not found in the Model
     */
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
//@@author
