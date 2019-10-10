package seedu.savenus.logic.parser;

import seedu.savenus.logic.commands.BudgetCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.wallet.Wallet;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new BudgetCommand object
 */
public class BudgetCommandParser implements Parser<BudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCommand
     * and returns a BudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCommand parse(String args) throws ParseException {
        try {
            Wallet newWallet = ParserUtil.parseWallet(args);
            return new BudgetCommand(newWallet);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE), pe);
        }
    }

}
