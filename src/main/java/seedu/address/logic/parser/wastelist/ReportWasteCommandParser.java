package seedu.address.logic.parser.wastelist;

import seedu.address.logic.commands.wastelist.ReportWasteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class ReportWasteCommandParser implements Parser<ReportWasteCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ReportWasteCommand parse(String userInput) throws ParseException {
        // Will modify to parse start and end dates in v1.3
        return new ReportWasteCommand();
    }
}
