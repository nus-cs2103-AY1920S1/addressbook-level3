package seedu.savenus.logic.commands;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.Model;

/**
 * Filters the user's savings history to display only what the users intend to see.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the User's display to show either Withdrawals, Savings, or Both"
            + "Parameters: DISPLAY_FROM_SAVINGS\n"
            + "Restriction: Parameter can only be 'withdrawals', 'savings' or 'both'\n"
            + "Example: " + COMMAND_WORD + " withdrawals";

    private static final String MESSAGE_DISPLAY_SUCCESS = "Displaying %s in your Savings History.";

    private static final String MESSAGE_INVALID_COMMAND_FORMAT =
            "Please provide 'savings'/'withdrawals'/'both' with your show input!";

    private final String toDisplay;

    public ShowCommand(String args) throws ParseException {
        String[] splitArguments = args.trim().split("\\s+");
        if (splitArguments.length != 1) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        } else {
            this.toDisplay = splitArguments[0];
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (toDisplay.toLowerCase()) {
        case ("withdrawals"): {
            return new CommandResult(String.format(MESSAGE_DISPLAY_SUCCESS, "withdrawals"),
                    false, true);
        }
        case ("savings"): {
            return new CommandResult(String.format(MESSAGE_DISPLAY_SUCCESS, "savings"),
                    true, false);
        }
        case ("both"): {
            return new CommandResult(String.format(MESSAGE_DISPLAY_SUCCESS, "savings and withdrawals"));
        }
        default: {
            throw new CommandException("Please key in only 'savings'/'withdrawals'/'both'.");
        }
        }
    }
}
