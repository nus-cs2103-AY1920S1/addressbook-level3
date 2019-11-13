package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.planner.logic.commands.LoadCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.field.Name;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser implements Parser<LoadCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns a LoadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadCommand parse(String args) throws ParseException {
        Name name = null;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        if (name == null) {
            throw new ParseException(LoadCommand.MESSAGE_NO_NAME);
        }

        return new LoadCommand(name);
    }
}
