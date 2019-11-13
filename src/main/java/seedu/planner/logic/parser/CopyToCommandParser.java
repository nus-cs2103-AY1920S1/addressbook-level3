package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.planner.logic.commands.CopyToCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.field.Name;

/**
 * Parses input arguments and creates a new CopyToCommand object
 */
public class CopyToCommandParser implements Parser<CopyToCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CopyToCommand
     * and returns a CopyToCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyToCommand parse(String args) throws ParseException {
        Name name = null;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        if (name == null) {
            throw new ParseException(CopyToCommand.MESSAGE_NO_NAME);
        }

        return new CopyToCommand(name);
    }
}
