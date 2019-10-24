package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.RewindCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RewindCommand object
 */
public class RewindCommandParser implements Parser<RewindCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args the user input to be parsed
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public RewindCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RewindCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RewindCommand.MESSAGE_USAGE), pe);
        }
    }

}
