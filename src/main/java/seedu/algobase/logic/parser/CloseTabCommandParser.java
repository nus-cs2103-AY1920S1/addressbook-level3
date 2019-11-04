package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAB_INDEX;
import static seedu.algobase.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.gui.CloseTabCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 *  Parses input arguments and creates a new SwitchTabCommand object.
 */
public class CloseTabCommandParser implements Parser<CloseTabCommand> {
    @Override
    public CloseTabCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_TAB_INDEX);

        Index index;
        if (arePrefixesPresent(argMultimap, PREFIX_TAB_INDEX)) {
            index = ParserUtil.parseTabIndex(
                argMultimap.getValue(PREFIX_TAB_INDEX).get(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseTabCommand.MESSAGE_USAGE)
            );
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseTabCommand.MESSAGE_USAGE));
        }

        return new CloseTabCommand(index);
    }
}
