package seedu.address.logic.parser.wastelist;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;

import java.time.LocalDate;

import seedu.address.logic.commands.wastelist.ListWasteCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.waste.WasteMonth;

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
        String trimmedArgs = userInput.trim();

        if (trimmedArgs.isEmpty()) {
            LocalDate today = LocalDate.now();
            int month = today.getMonthValue();
            int year = today.getYear();
            return new ListWasteCommand(new WasteMonth(month, year));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_MONTH);

        WasteMonth wasteMonth = ParserUtil.parseWasteMonth(argMultimap.getValue(PREFIX_MONTH).get());

        return new ListWasteCommand(wasteMonth);
    }
}
