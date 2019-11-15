package seedu.address.financialtracker.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.financialtracker.logic.commands.SortFinCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortFinCommand object
 */
public class SortFinCommandParser implements Parser<SortFinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortFinCommand
     * and returns a SortFinCommand object for execution.
     */
    public SortFinCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String comparingType = args.toUpperCase().trim();
        if (comparingType.equals("TIME") || comparingType.equals("DATE") || comparingType.equals("AMOUNT")
            || comparingType.equals("TYPE") || comparingType.equals("DEFAULT")) {
            return new SortFinCommand(comparingType);
        } else {
            throw new ParseException("Incorrect argument! only 'time', 'date, 'amount',"
                    + " 'type or 'default' are allowed!");
        }
    }
}
