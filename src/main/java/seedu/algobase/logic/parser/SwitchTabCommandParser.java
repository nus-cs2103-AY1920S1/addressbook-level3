package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.SwitchTabCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 *  Parses input arguments and creates a new SwitchTabCommand object.
 */
public class SwitchTabCommandParser implements Parser<SwitchTabCommand> {
    @Override
    public SwitchTabCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE), pe);
        }

        return new SwitchTabCommand(index);
    }
}
