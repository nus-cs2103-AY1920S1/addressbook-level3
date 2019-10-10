package seedu.address.logic.commands.earnings;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Changes the remark of an existing person in the address book.
 */
public class AddEarningsCommand extends Command {

    public static final String COMMAND_WORD = "add/earnings ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the earnings of the user " + "\n"

            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_AMOUNT + "AMOUNT(in dollars) \n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "03/05/2020 "
            + PREFIX_MODULE + "CS2103T "
            + PREFIX_AMOUNT + "$55.30";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Add earnings command not implemented yet";
    public static final String MESSAGE_ARGUMENTS = "Date: %1$10s, Module: %2$s, Amount: $ %3$.2f"; // For date, maybe can use this instead --> %tm/%td/%ty

//    private final String date;
//    private final String module;
//    private final double amount;

//    public AddEarningsCommand(String date, String module, double amount) {
//        requireAllNonNull(date, module, amount);
//
//        this.date = date;
//        this.module = module;
//        this.amount = amount;
//    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("earnings not completed yet");
        //throw new CommandException(String.format(MESSAGE_ARGUMENTS, date, module, amount));
    }

//    @Override
//    public boolean equals(Object other) {
//        // short circuit if same object
//        if (other == this) {
//            return true;
//        }
//
//        // instanceof handles nulls
//        if (!(other instanceof AddEarningsCommand)) {
//            return false;
//        }
//
//        // state check
//        AddEarningsCommand e = (AddEarningsCommand) other;
//        return date.equals(e.date)
//                && module.equals(e.module)
//                && amount == e.amount;
//    }

    // AT PARSE UNIT INPUT!!!!!!!!!!!!!!!!!!!!!!
}