package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_IN_SERVE_MODE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_OUTSTANDING_FINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOLLAR;

import seedu.address.commons.util.FineUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.borrower.Borrower;

/**
 * Pays fines incurred by Borrower with amount.
 */
public class PayCommand extends Command {
    public static final String COMMAND_WORD = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pays outstanding fines incurred by the borrower.\n"
            + "Command can only be used in Serve mode.\n"
            + "Parameters: " + PREFIX_DOLLAR
            + "AMOUNT (must be a positive number with at most 2 decimal places)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DOLLAR + "1.20";

    public static final String MESSAGE_SUCCESS =
            "Fine of %1$s\npaid by\nBorrower: %2$s\nOutstanding fine: %3$s\nChange given: %4$s";

    private final int amountInCents;

    /**
     * Creates a PayCommand to pay the specified {@code amountInCents} towards fine incurred by the Borrower.
     *
     * @param amountInCents Amount in cents paid by the Borrower.
     */
    public PayCommand(int amountInCents) {
        this.amountInCents = amountInCents;
    }

    /**
     * Executes the PayCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isServeMode()) {
            throw new CommandException(MESSAGE_NOT_IN_SERVE_MODE);
        }

        Borrower servingBorrower = model.getServingBorrower();
        if (!servingBorrower.hasOutstandingFine()) {
            throw new CommandException(MESSAGE_NO_OUTSTANDING_FINE);
        }

        model.resetCommandHistory();

        int change = model.payFines(amountInCents);

        String amountPaidInDollars = FineUtil.centsToDollarString(amountInCents - change);
        // servingBorrower was updated in payFine method
        String outstandingFineInDollars = FineUtil.centsToDollarString(
                model.getServingBorrower().getOutstandingFineAmount());
        String changeInDollars = FineUtil.centsToDollarString(change);

        return new CommandResult(String.format(MESSAGE_SUCCESS, amountPaidInDollars,
                model.getServingBorrower(), outstandingFineInDollars, changeInDollars));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof PayCommand)) {
            return false;
        }

        PayCommand otherPayCommand = (PayCommand) o;
        return this.amountInCents == otherPayCommand.amountInCents;
    }
}
