package seedu.address.logic.parser;

import seedu.address.logic.commands.ListPartCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses argument input and creates new ListPartCommand.
 */
public class ListPartCommandParser implements Parser<ListPartCommand> {
    /**
     * Parses the given String {@code userInput} into a ListPartCommand and returns it for execution.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public ListPartCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListPartCommand();
        }

        Name compName = ParserUtil.parseName(trimmedArgs);

        return new ListPartCommand(compName);
    }
}
