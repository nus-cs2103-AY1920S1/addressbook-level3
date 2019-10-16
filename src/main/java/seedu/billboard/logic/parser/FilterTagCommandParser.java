package seedu.billboard.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.billboard.logic.commands.FilterTagCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.tag.ContainsTagPredicate;
import seedu.billboard.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterTagCommand object
 */
public class FilterTagCommandParser implements Parser<FilterTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterTagCommand
     * and returns a FilterTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterTagCommand.MESSAGE_USAGE));
        }

        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new FilterTagCommand(new ContainsTagPredicate(tags));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
