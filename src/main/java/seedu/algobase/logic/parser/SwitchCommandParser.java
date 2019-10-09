package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.EditCommand;
import seedu.algobase.logic.commands.SwitchCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 *  Parses input arguments and creates a new SwitchCommand object.
 */
public class SwitchCommandParser implements Parser<SwitchCommand> {
    @Override
    public SwitchCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        return new SwitchCommand(index);
    }
}
