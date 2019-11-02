package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.SortFilter;

/**
 * Parses the user input to return a SortCommand
 */
//@@author {lawncegoh}
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the user input
     * @param userInput
     * @return SortCommand with the specific sort filter
     * @throws ParseException if user input does not conform to the expected format
     */
    @Override
    public SortCommand parse(String userInput) throws ParseException {
        SortFilter sortFilter = ParserUtil.parseFilter(userInput);
        return new SortCommand(sortFilter);
    }
}
