package seedu.ichifund.logic.commands.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_END_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_START_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.repeater.Repeater;

/**
 * Adds a repeater to IchiFund.
 */
public class AddRepeaterCommand extends Command {

    public static final String COMMAND_WORD = "addrep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a repeater to IchiFund. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_TRANSACTION_TYPE + "TRANSACTION_TYPE "
            + PREFIX_MONTH_START_OFFSET + "MONTH_START_OFFSET "
            + PREFIX_MONTH_END_OFFSET + "MONTH_END_OFFSET "
            + PREFIX_START_DAY + "START_DAY "
            + PREFIX_START_MONTH + "START_MONTH "
            + PREFIX_START_YEAR + "START_YEAR "
            + PREFIX_END_DAY + "END_DAY "
            + PREFIX_END_MONTH + "END_MONTH "
            + PREFIX_END_YEAR + "END_YEAR "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Phone bills "
            + PREFIX_AMOUNT + "42.15 "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_TRANSACTION_TYPE + "expenditure "
            + PREFIX_MONTH_END_OFFSET + "2 "
            + PREFIX_START_DAY + "1 "
            + PREFIX_START_MONTH + "1 "
            + PREFIX_START_YEAR + "2019 "
            + PREFIX_END_DAY + "31 "
            + PREFIX_END_MONTH + "12 "
            + PREFIX_END_YEAR + "2025";

    public static final String MESSAGE_SUCCESS = "New repeater added: %1$s";

    private final Repeater toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Repeater}
     */
    public AddRepeaterCommand(Repeater repeater) {
        requireNonNull(repeater);
        toAdd = repeater;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.addRepeater(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRepeaterCommand // instanceof handles nulls
                && toAdd.equals(((AddRepeaterCommand) other).toAdd));
    }
}
