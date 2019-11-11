package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.earnings.Earnings;

/**
 * Adds the earnings into the address book.
 */
public class AddEarningsCommand extends Command {

    public static final String COMMAND_WORD = "add_earnings";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the earnings of the user " + "\n"

            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_CLASSID + "CLASSID "
            + PREFIX_AMOUNT + "AMOUNT(in dollars) \n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "03/05/2020 "
            + PREFIX_TYPE + "tut "
            + PREFIX_CLASSID + "CS2103T "
            + PREFIX_AMOUNT + "55.30";

    public static final String MESSAGE_SUCCESS = "New earnings added: %1$s";
    public static final String MESSAGE_DUPLICATE_EARNINGS =
            "This earnings with the same module, date and amount already exists in the address book";
    // For date, maybe can use this instead --> %tm/%td/%ty

    private final Earnings toAddEarnings;

    public AddEarningsCommand(Earnings earnings) {
        requireAllNonNull(earnings);

        this.toAddEarnings = earnings;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // If earnings with same date and amount and module has already been added.
        if (model.hasEarnings(toAddEarnings)) {
            throw new CommandException(MESSAGE_DUPLICATE_EARNINGS);
        }

        model.addEarnings(toAddEarnings);
        //Earnings.getEarningsList().add(toAddEarnings);
        model.commitTutorAid();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddEarnings),
                false, false, true, false, false,
                false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEarningsCommand // instanceof handles nulls
                && toAddEarnings.equals(((AddEarningsCommand) other).toAddEarnings));
    }

}
