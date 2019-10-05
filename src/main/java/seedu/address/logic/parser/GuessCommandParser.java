package seedu.address.logic.parser;

import seedu.address.logic.commands.GuessCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.game.Guess;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class GuessCommandParser implements Parser<GuessCommand> {

    @Override
    public GuessCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GuessCommand.MESSAGE_USAGE));
        }

        return new GuessCommand(new Guess(trimmedArgs));
    }
}
