//package seedu.jarvis.logic.commands.finance;
//
//import seedu.jarvis.logic.commands.Command;
//import seedu.jarvis.logic.commands.CommandResult;
//import seedu.jarvis.logic.commands.exceptions.CommandException;
//import seedu.jarvis.model.Model;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.jarvis.logic.parser.CliSyntax.*;
//
//public class PaidCommand extends Command {
//
//    public static final String COMMAND_WORD = "paid";
//
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a payment to the finance tracker. "
//            + "Parameters: "
//            + PREFIX_DESCRIPTION + "DESCRIPTION "
//            + PREFIX_MONEY + "MONEY "
//            + "[" + PREFIX_TAG + "TAG]...\n"
//            + "Example: " + COMMAND_WORD + " "
//            + PREFIX_DESCRIPTION + "Aston's for Lunch "
//            + PREFIX_MONEY + "9.50 ";
//
//    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
//
//    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted Payment: %1$s";
//    public static final String MESSAGE_INVERSE_PAYMENT_NOT_FOUND = "Payment already deleted: %1$s";
//
//    public static final boolean HAS_INVERSE = false;
//
//
//    @Override
//    public boolean hasInverseExecution() {
//        return HAS_INVERSE;
//    }
//
//    @Override
//    public CommandResult execute(Model model) throws CommandException {
//        requireNonNull(model);
//        model.addPayment();
//    }
//
//    @Override
//    public CommandResult executeInverse(Model model) throws CommandException {
//        return null;
//    }
//}
