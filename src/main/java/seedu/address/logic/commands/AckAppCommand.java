//package seedu.address.logic.commands;
//
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
//
//
//import seedu.address.logic.commands.common.CommandResult;
//import seedu.address.logic.commands.common.ReversibleCommand;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
//import seedu.address.model.events.Appointment;
//import seedu.address.model.events.Event;
//import seedu.address.model.person.Person;
//
//public class AckAppCommand extends ReversibleCommand {
////    public static final Prefix PREFIX_START = new Prefix("str/");
////    public static final Prefix PREFIX_END = new Prefix("end/");
//    public static final String COMMAND_WORD = "ackappt";
//
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Ack a appointment to the address book. "
//            + "Parameters: "
//            + PREFIX_ID + "REFERENCE ID \n"
//            + "Example: " + COMMAND_WORD + " "
//            + PREFIX_ID + "001A ";
//
//    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
//    public static final String MESSAGE_DUPLICATE_PERSON = "This appointment already exists in the address book";
//    public static final String MESSAGE_wrong_PERSON = "This appointment already exists in the address book";
//    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful! Appointment '%1$s' has been removed.";
//    public static final String MESSAGE_UNDO_ADD_ERROR = "Could not undo the addition of appointment: %1$s";
//
//    private final Event appointment;
//
//    /**
//     * Creates an AckAppCommand to add the specified {@code Person}
//     */
//    public AckAppCommand(Event appt) {
//        requireNonNull(appt);
//        appointment = appt;
//    }
//
//    @Override
//    public CommandResult execute(Model model) throws CommandException {
//        requireNonNull(model);
//
//        if (model.hasEvent(appointment)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
//
//        model.addEvent(appointment);
//        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
//    }
//
//    @Override
//    public CommandResult undo(Model model) throws CommandException {
//        requireNonNull(model);
//
//        if (!model.hasEvent(appointment)) {
//            throw new CommandException(String.format(MESSAGE_UNDO_ADD_ERROR, appointment));
//        }
//
//        model.deleteEvent(appointment);
//        return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, appointment));
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof AckAppCommand // instanceof handles nulls
//                && appointment.equals(((AckAppCommand) other).appointment));
//    }
//
//}
