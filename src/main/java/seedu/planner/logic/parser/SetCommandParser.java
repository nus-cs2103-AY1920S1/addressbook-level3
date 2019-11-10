package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;

import seedu.planner.logic.commands.SetCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.field.Name;

/**
 * Parses input arguments and creates a new SetCommand object
 */
public class SetCommandParser implements Parser<SetCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SetCommand
     * and returns an SetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCommand parse(String args) throws ParseException {
        Name name = null;
        LocalDate startDate = null;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_DATE);
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        }

        if (name == null && startDate == null) {
            throw new ParseException(SetCommand.MESSAGE_NOTHING_TO_SET);
        }

        return new SetCommand(name, startDate);
    }
}
