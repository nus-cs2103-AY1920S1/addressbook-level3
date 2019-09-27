//package seedu.address.logic.commands;
//
//import seedu.address.logic.commands.common.ReversibleCommand;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
//import seedu.address.model.events.Appointment;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
//
//
//public class AddAppCommand  {
//    public static final String COMMAND_WORD = "addappt";
//
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new appointment to the scheduling calendar. "
//            + "Parameters: "
//            + PREFIX_NAME + "NAME "
//            + PREFIX_PHONE + "PHONE "
//            + PREFIX_EMAIL + "EMAIL "
//            + PREFIX_ADDRESS + "ADDRESS "
//            + "[" + PREFIX_TAG + "TAG]...\n"
//            + "Example: " + COMMAND_WORD + " "
//            + PREFIX_NAME + "John Doe "
//            + PREFIX_PHONE + "98765432 "
//            + PREFIX_EMAIL + "johnd@example.com "
//            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
//            + PREFIX_TAG + "fever "
//            + PREFIX_TAG + "owesMoney"
//            + PREFIX_EVENT + "31-08-1982 10:20:00, 31-08-1982 11:20:00";
//
//    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
//    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists scheduled.";
//    public static final String MESSAGE_UNDO_ADD_SUCCESS = "Undo successful! Person '%1$s' has been removed.";
//    public static final String MESSAGE_UNDO_ADD_ERROR = "Could not undo the addition of person: %1$s";
//
//    private final Appointment toAdd;
//
//    public AddAppCommand(Appointment toAdd) {
//        requireAllNonNull(toAdd);
//        this.toAdd = toAdd;
//    }
//
//    @Override
//    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
//        requireNonNull(model);
//        if (model.hasEvent(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
//        }
//        if (model.hasAppointmentClash(toAdd)) {
//            throw new CommandException(MESSAGE_CLASH_APPOINTMENT);
//        }
//        model.addAppointment(toAdd);
//        model.commitClinicIo();
//        EventsCenter.getInstance().post(new SwitchTabEvent(1));
//        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
//    }
//
//    @Override
//    public CommandResult execute(Model model) throws CommandException {
//        requireNonNull(model);
//
//        if (model.hasPerson(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
//
//        model.addPerson(toAdd);
//        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
//    }
//
//    @Override
//    public CommandResult undo(Model model) throws CommandException {
//        requireNonNull(model);
//
//        if (!model.hasPerson(toAdd)) {
//            throw new CommandException(String.format(MESSAGE_UNDO_ADD_ERROR, toAdd));
//        }
//
//        model.deletePerson(toAdd);
//        return new CommandResult(String.format(MESSAGE_UNDO_ADD_SUCCESS, toAdd));
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof AddApptCommand // instanceof handles nulls
//                && toAdd.equals(((AddApptCommand) other).toAdd));
//    }
//
//}
