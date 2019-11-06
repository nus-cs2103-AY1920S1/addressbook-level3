package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.savenus.logic.commands.WithdrawCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.util.Money;

/**
 * Parses input arguments and creates a new {@code WithdrawCommand} to be executed later
 * to withdraw {@code Savings} from user into his/her {@code SavingsAccount}.
 */
public class WithdrawCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the WithdrawCommand
     * and returns an SaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WithdrawCommand parse(String args) throws ParseException {
        try {
            Money savings = ParserUtil.parseSavings(args);
            return new WithdrawCommand(savings.toString());
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, WithdrawCommand.MESSAGE_USAGE), e);
        }
    }
}
