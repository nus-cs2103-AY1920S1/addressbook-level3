package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Adds a Spending to the MoneyGoWhere list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Spending to MoneyGoWhere. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_COST + "COST "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Apple "
            + PREFIX_DATE + "today "
            + PREFIX_COST + "1.50 "
            + PREFIX_REMARK + "Keeps the doctor away "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String NO_DUPLICATE_MESSAGE_SUCCESS = "New Spending added: %1$s";

    public static final String MESSAGE_DUPLICATE_FOUND = "A spending entry with the same attributes was found.";

    public static final String DUPLICATE_MESSAGE_SUCCESS = "Duplicate spending added: %1$s";

    private final Spending toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Spending spending) {
        requireNonNull(spending);
        toAdd = spending;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String feedbackToUser;
        Spending tempSpending = toAdd;

        if (!model.getCurrencyInUse().name.equalsIgnoreCase("SGD")) {
            double updatedCost = Double.parseDouble(toAdd.getCost().value) / model.getCurrencyInUse().rate;
            Cost cost = new Cost(String.format("%.2f", updatedCost));
            tempSpending = new Spending(toAdd.getName(), toAdd.getDate(), toAdd.getRemark(), cost,
                    toAdd.getTags());
        }

        if (model.hasSpending(tempSpending)) {
            feedbackToUser = MESSAGE_DUPLICATE_FOUND + "\n" + String.format(DUPLICATE_MESSAGE_SUCCESS, tempSpending);
        } else {
            feedbackToUser = String.format(NO_DUPLICATE_MESSAGE_SUCCESS, tempSpending);
        }

        model.addSpending(tempSpending);
        return new CommandResult(feedbackToUser);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
