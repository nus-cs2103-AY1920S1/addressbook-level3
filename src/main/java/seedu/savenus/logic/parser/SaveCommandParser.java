package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.savenus.logic.commands.SaveCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.util.Money;

/**
 * Parses input arguments and creates a new {@code SaveCommand} to be executed later
 * to add {@code Savings} from user into his/her {@code SavingsAccount}.
 */
public class SaveCommandParser implements Parser<SaveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SaveCommand parse(String args) throws ParseException {
        try {
            Money savings = ParserUtil.parseSavings(args);
            return new SaveCommand(savings.toString());
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE), e);
        }
    }
}
