package seedu.algobase.logic.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAB_INDEX;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAB_TYPE;
import static seedu.algobase.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.SwitchTabCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.gui.TabType;

/**
 *  Parses input arguments and creates a new SwitchTabCommand object.
 */
public class SwitchTabCommandParser implements Parser<SwitchTabCommand> {
    @Override
    public SwitchTabCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_TAB_TYPE, PREFIX_TAB_INDEX);

        TabType tabType;
        if (arePrefixesPresent(argMultimap, PREFIX_TAB_TYPE)) {
            tabType = ParserUtil.parseTabType(argMultimap.getValue(PREFIX_TAB_TYPE).get());
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
        }

        Index index;
        if (arePrefixesPresent(argMultimap, PREFIX_TAB_INDEX)) {
            index = ParserUtil.parseTabIndex(argMultimap.getValue(PREFIX_TAB_INDEX).get());
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
        }

        return new SwitchTabCommand(tabType, index);
    }
}
