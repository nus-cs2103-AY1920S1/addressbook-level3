package seedu.planner.logic.parser;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.planner.logic.commands.NewCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.field.Name;

/**
 * Parses input arguments and creates a new NewCommand object
 */
public class NewCommandParser implements Parser<NewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the NewCommand
     * and returns a NewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NewCommand parse(String args) throws ParseException {
        Name name = null;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        }

        if (name == null) {
            throw new ParseException(NewCommand.MESSAGE_NO_NAME);
        }

        return new NewCommand(name);
    }
}
