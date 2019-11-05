package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;

import seedu.exercise.logic.commands.ListCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.ui.ListResourceType;

/**
 * Parses input arguments and creates a new SuggestCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SuggestCommand
     * and returns a SuggestCommand object for execution.
     *
     * @throws ParseException if the user does not conform to the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!argMultimap.arePrefixesPresent(PREFIX_CATEGORY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        ListResourceType listResourceType = ParserUtil
                .parseListResourceType(argMultimap.getValue(PREFIX_CATEGORY).get());
        return new ListCommand(listResourceType);
    }

}
