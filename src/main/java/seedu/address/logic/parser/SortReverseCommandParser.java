package seedu.address.logic.parser;

import seedu.address.logic.commands.SortReverseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.SortFilter;

/**
 * Parses the user input to create a SortReverseCommand
 */
//@@author {lawncegoh}
public class SortReverseCommandParser implements Parser<SortReverseCommand> {

    /**
     * Parses the user input
     * @param userInput
     * @return SortCommand with the specific sort filter
     * @throws ParseException if user input does not conform to the expected format
     */
    @Override
    public SortReverseCommand parse(String userInput) throws ParseException {
        SortFilter sortFilter = ParserUtil.parseFilter(userInput);
        return new SortReverseCommand(sortFilter);
    }
}
