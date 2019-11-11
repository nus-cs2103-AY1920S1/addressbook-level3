package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.entity.body.BodyStatus.CONTACT_POLICE;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.notif.Notif;

//@@author shaoyi1997-reused
//Reused from AB3
/**
 * Adds an entity to Mortago.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final long NOTIF_PERIOD = 10;
    public static final TimeUnit NOTIF_TIME_UNIT = TimeUnit.SECONDS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entity to Mortago.\n"
            + "Please refer to the User Guide for more details on how to add an entity";

    public static final String MESSAGE_SUCCESS = "New %1$s added. ID Number: %2$s";
    public static final String MESSAGE_DUPLICATE_ENTITY = "This entity already exists in Mortago";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid adding this entity: %1$s";

    private final Entity toAdd;

    private NotifCommand notifCommand;
    private Fridge fridge;

    /**
     * Creates an AddCommand to add the specified {@code Body}, {@code Worker} or {@code Fridge}.
     */
    public AddCommand(Entity entity) {
        requireNonNull(entity);
        toAdd = entity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEntity(toAdd)) {
            toAdd.getIdNum().removeMapping(); // since toAdd should no longer be added, its mapping must be removed
            throw new CommandException(MESSAGE_DUPLICATE_ENTITY);
        }

        model.addEntity(toAdd);

        if (toAdd instanceof Body) {
            Body body = (Body) toAdd;
            //@@author ambervoong
            if (getCommandState().equals(UndoableCommandState.REDOABLE)) {
                toAdd.getIdNum().addMapping(toAdd); // Add mapping again since this is not a new Body.
            } else {
                NotifCommand notifCommand = new NotifCommand(new Notif(body), NOTIF_PERIOD, NOTIF_TIME_UNIT);
                this.notifCommand = notifCommand;

                if (body.getBodyStatus().equals(Optional.of(CONTACT_POLICE))) {
                    notifCommand.addNotif(model); // adds ONLY notif when status of the added body is contact police
                } else {
                    notifCommand.execute(model);
                }
            }
            //@@author
            SelectCommand selectCommand = new SelectCommand(toAdd.getIdNum().getIdNum());
            selectCommand.execute(model);


            Optional<IdentificationNumber> fridgeId = body.getFridgeId();
            if (!fridgeId.equals(Optional.empty())) {
                List<Fridge> fridgeList = model.getFilteredFridgeList();
                for (Fridge fridge : fridgeList) {
                    if (fridge.getIdNum().equals(fridgeId.get())) {
                        this.fridge = fridge;
                        fridge.setBody(body);
                    }
                }
            }
        }
        setUndoable();
        model.addExecutedCommand(this);
        Platform.runLater(() -> model.updateFilteredFridgeList(fridge -> true));
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                toAdd instanceof Body ? "body" : (toAdd instanceof Worker ? "worker" : "fridge"), toAdd.getIdNum()));
    }

    //@@author ambervoong
    /**
     * Undoes the effects of the AddCommand. Only can be executed if this command was previously executed before.
     * @return result of undoing the command.
     */
    @Override
    public CommandResult undo(Model model) throws CommandException {
        if (!(getCommandState().equals(UndoableCommandState.UNDOABLE))) {
            throw new CommandException(MESSAGE_NOT_EXECUTED_BEFORE);
        }
        try {
            model.deleteEntity(toAdd);
            if (notifCommand != null) {
                notifCommand.removeNotif(model);
            }
            if (fridge != null) {
                fridge.setBody(null);
            }
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }
        setRedoable();
        model.addUndoneCommand(this);

        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toAdd.getIdNum()));
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
//@@author
