package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.notif.Notif;

/**
 * Adds a person to Mortago.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final int NOTIF_PERIOD = 10;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entity to Mortago.\n"
            + "Adding a worker:\n"
            + "Compulsory Fields: "
            + PREFIX_FLAG + "w "
            + PREFIX_NAME + " NAME "
            + PREFIX_SEX + " SEX "
            + PREFIX_DATE_JOINED + " DATE JOINED\n"
            + "Optional Fields: "
            + PREFIX_PHONE_NUMBER + " PHONE "
            + PREFIX_DATE_OF_BIRTH + " DATE OF BIRTH "
            + PREFIX_DESIGNATION + " DESIGNATION "
            + PREFIX_EMPLOYMENT_STATUS + " EMPLOYMENT STATUS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe"
            + PREFIX_PHONE_NUMBER + " 91234567 "
            + PREFIX_SEX + " Male "
            + PREFIX_DATE_OF_BIRTH + " 12/12/1997 "
            + PREFIX_DATE_JOINED + " 1/1/2019 "
            + PREFIX_DESIGNATION + " Technician "
            + PREFIX_EMPLOYMENT_STATUS + " Cleaning\n"
            + "Adding a body:\n"
            + "Compulsory Fields: "
            + PREFIX_FLAG + "b "
            + PREFIX_NAME + " NAME "
            + PREFIX_SEX + " SEX "
            + PREFIX_DATE_OF_DEATH + " DATE OF DEATH "
            + PREFIX_DATE_OF_ADMISSION + " DATE OF ADMISSION\n"
            + "Optional Fields: "
            + PREFIX_NRIC + " NRICE "
            + PREFIX_RELIGION + " RELIGION "
            + PREFIX_NAME_NOK + " NAME OF NEXT OF KIN "
            + PREFIX_RELATIONSHIP + " RELATIONSHIP OF NEXT OF KIN"
            + PREFIX_PHONE_NOK + " PHONE OF NEXT OF KIN "
            + PREFIX_ORGANS_FOR_DONATION + " ORGANS FOR DONATION"
            + PREFIX_STATUS + " BODY STATUS "
            + PREFIX_FRIDGE_ID + " FRIDGE ID\n"
            + "Adding a fridge: add -f";

    public static final String MESSAGE_SUCCESS = "New entity added: %1$s";
    public static final String MESSAGE_DUPLICATE_ENTITY = "This entity already exists in Mortago";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid adding this entity: %1$s";

    private final Entity toAdd;

    private NotifCommand notifCommand;

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
            toAdd.getIdNum().removeMapping();
            throw new CommandException(MESSAGE_DUPLICATE_ENTITY);
        }

        model.addEntity(toAdd);

        if (toAdd instanceof Body) {
            SelectCommand selectCommand = new SelectCommand(toAdd.getIdNum().getIdNum());
            selectCommand.execute(model);
            Body body = (Body) toAdd;
            NotifCommand notifCommand = new NotifCommand(new Notif(body), NOTIF_PERIOD, TimeUnit.SECONDS);
            this.notifCommand = notifCommand;

            notifCommand.execute(model);
            Optional<IdentificationNumber> fridgeId = body.getFridgeId();
            if (!fridgeId.equals(Optional.empty())) {
                List<Fridge> fridgeList = model.getFilteredFridgeList();
                for (Fridge fridge : fridgeList) {
                    if (fridge.getIdNum().equals(fridgeId.get())) {
                        fridge.setBody(body);
                    }
                }
            }
        }
        setUndoable();
        model.addExecutedCommand(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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
            notifCommand.removeNotif(model);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ENTITY_NOT_FOUND);
        }
        setRedoable();
        model.addUndoneCommand(this);

        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, toAdd));
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
