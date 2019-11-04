package seedu.address.logic.parser.general;

import java.time.LocalDate;

import seedu.address.logic.commands.general.SetAppDateCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetAppDateCommand object
 */
public class SetAppDateCommandParser implements Parser<SetAppDateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetAppDateCommand
     * and returns a SetAppDateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetAppDateCommand parse(String args) throws ParseException {
        LocalDate date = ParserUtil.parseAnyDate(args);
        return new SetAppDateCommand(date);
    }

}
