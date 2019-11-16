package seedu.ifridge.logic.parser.wastelist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_MONTH;

import seedu.ifridge.logic.commands.wastelist.ListWasteCommand;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Parser;
import seedu.ifridge.logic.parser.ParserUtil;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * List command parser to parse the months specified by the user.
 */
public class ListWasteCommandParser implements Parser<ListWasteCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ListWasteCommand parse(String userInput) throws ParseException {
        String args = userInput;

        if (args.trim().isEmpty()) {
            WasteMonth.getCurrentWasteMonth();
            return new ListWasteCommand(WasteMonth.getCurrentWasteMonth());
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONTH);

        WasteMonth wasteMonth;

        try {
            wasteMonth = ParserUtil.parseWasteMonth(argMultimap.getValue(PREFIX_MONTH).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListWasteCommand.MESSAGE_USAGE), pe);
        }

        return new ListWasteCommand(wasteMonth);
    }
}
