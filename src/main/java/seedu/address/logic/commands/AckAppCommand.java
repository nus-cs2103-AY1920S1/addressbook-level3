package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;


import javafx.collections.ObservableList;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.ContainsKeywordsPredicate;
import seedu.address.model.events.Event;
import seedu.address.model.person.Person;

public class AckAppCommand extends ReversibleCommand {
//    public static final Prefix PREFIX_START = new Prefix("str/");
//    public static final Prefix PREFIX_END = new Prefix("end/");
    public static final String COMMAND_WORD = "ackappt";
    private Event appointment;
    private Integer ackIdx = 0;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ack a appointment to the address book. [make sure you call appointments first] "
            + "Parameters: "
            + "Integer " + "index number \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_NOTING_ACK = "Make sure [appointments] first and there are appointments to be acked";
    public static final String MESSAGE_IDX_TOO_LARGE = "the idx is too large, it is not exist in appointments list";
    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful! Appointment '%1$s' has been removed.";
    public static final String MESSAGE_UNDO_ADD_ERROR = "Could not undo the addition of appointment: %1$s";

    /**
     * Creates an AckAppCommand to add the specified {@code Person}
     */
    public AckAppCommand(Integer idx) {
        requireNonNull(idx);
        this.ackIdx = idx;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Event> currentList = model.getFilteredEventList();
//
//        if (true) {
//            throw new CommandException(String.format("debug currentList in ackCommand", getString(currentList)));
//        }


        if (currentList.size() == 0) {
            throw new CommandException(MESSAGE_NOTING_ACK);
        }

        if (currentList.size() < ackIdx) {
            throw new CommandException(MESSAGE_IDX_TOO_LARGE);
        }
        appointment = currentList.get(ackIdx - 1);
        ackEvent(appointment);

//        model.updateFilteredEventList(appointment);
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }

    private String getString(ObservableList<Event> currentList) {
        String str = "";
        for(Event ev: currentList){
            str += ev.toString();
        }
        return str;
    }

    public void ackEvent(Event idxEvent){
        idxEvent.setStausAsAck();
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasEvent(appointment)) {
            throw new CommandException(String.format(MESSAGE_UNDO_ADD_ERROR, appointment));
        }

        model.deleteEvent(appointment);
        return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, appointment));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AckAppCommand // instanceof handles nulls
                && appointment.equals(((AckAppCommand) other).appointment));
    }

}
