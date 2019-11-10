package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.fridge.FridgeStatus;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.notif.Notif;

//@@author arjavibahety
/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

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
    public static final String MESSAGE_UNDO_SUCCESS = "Undid deleting this entity: %1$s";

    private final Index targetIndexNum;
    private final String entityType;

    private Entity entityToDelete;
    private Fridge fridge;
    private List<Notif> notifList = Collections.emptyList();

    public DeleteCommand(Index targetIndexNum, String entityType) {
        this.targetIndexNum = targetIndexNum;
        this.entityType = entityType.toUpperCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        IdentificationNumber targetIdNum = IdentificationNumber.customGenerateId(entityType,
                targetIndexNum.getZeroBased());

        entityToDelete = null;

        if (entityType.equals("B")) {
            List<Body> lastShownList = model.getFilteredBodyList();
            for (Body body : lastShownList) {
                if (body.getIdNum().equals(targetIdNum)) {
                    entityToDelete = body;
                    removeBodyNotifFromList(body, model);
                    if (!body.getFridgeId().equals(Optional.empty())) {
                        removeBodyFromFridge(body, model);
                    }
                    break;
                }
            }
        } else if (entityType.equals("W")) {
            List<Worker> lastShownList = model.getFilteredWorkerList();
            for (Worker worker : lastShownList) {
                if (worker.getIdNum().equals(targetIdNum)) {
                    entityToDelete = worker;
                    break;
                }
            }
        } else if (entityType.equals("F")) {
            List<Fridge> lastShownList = model.getFilteredFridgeList();
            for (Fridge fridge : lastShownList) {
                if (fridge.getIdNum().equals(targetIdNum)) {
                    if (fridge.getFridgeStatus().equals(FridgeStatus.OCCUPIED)) {
                        throw new CommandException(Messages.MESSAGE_OCCUPIED_FRIDGE_CANNOT_BE_DELETED);
                    }
                    entityToDelete = fridge;
                    break;
                }
            }
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_FLAG);
        }

        if (entityToDelete != null) {
            model.deleteEntity(entityToDelete);
            targetIdNum.removeMapping();
            setUndoable();
            model.addExecutedCommand(this);
            SelectCommand selectCommand = new SelectCommand(Integer.MAX_VALUE);
            selectCommand.execute(model);
            return new CommandResult(String.format(MESSAGE_DELETE_ENTITY_SUCCESS, entityToDelete));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
        }
    }

    /**
     * Removes a body from the fridge when the body is deleted.
     * @param body refers to the body being deleted.
     * @param model refers to the model in use.
     */
    private void removeBodyFromFridge(Body body, Model model) {
        IdentificationNumber fridgeId = body.getFridgeId().get();
        List<Fridge> lastShownFridgeList = model.getFilteredFridgeList();
        for (Fridge fridge : lastShownFridgeList) {
            if (fridge.getIdNum().equals(fridgeId)) {
                this.fridge = fridge;
                fridge.setBody(null);
            }
        }
    }

    /**
     * Removes a notif from the list of notifs when the body is deleted.
     * @param body refers to the body being deleted.
     * @param model refers to the model in use.
     */
    private void removeBodyNotifFromList(Body body, Model model) {
        List<Notif> lastShownNotificationList = model.getFilteredNotifList();
        List<Notif> notifsToRemove = new ArrayList<>();
        for (Notif notif : lastShownNotificationList) {
            if (notif.getBody().equals(body)) {
                notifsToRemove.add(notif);
            }
        }

        for (NotifCommand nc : NotifCommand.getNotifCommands()) {
            if (nc.getNotif().getBody().equals(body)) {
                nc.getChangeBodyStatusEvent().cancel(true);
                nc.getChangeUiEvent().cancel(true);
            }
        }

        this.notifList = new ArrayList<>(notifsToRemove);
        for (Notif notif : notifsToRemove) {
            model.deleteNotif(notif);
        }

        Platform.runLater(() -> model.updateFilteredFridgeList(fridge -> true));

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
            if (fridge != null) {
                fridge.setBody((Body) entityToDelete);
            }
            for (Notif notif : notifList) {
                model.addNotif(notif);
            }
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }

        setRedoable();
        model.addUndoneCommand(this);

        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, entityToDelete));
    }
    //@@author

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndexNum.equals(((DeleteCommand) other).targetIndexNum)); // state check
    }
}
//@@author
