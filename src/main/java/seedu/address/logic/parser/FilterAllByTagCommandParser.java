package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.logic.commands.global.FilterAllByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.StudyBuddyItemContainsTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ListAllByTagCommand object
 */
public class FilterAllByTagCommandParser implements Parser<FilterAllByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListAllByTagCommand
     * and returns a ListAllByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterAllByTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterAllByTagCommand.MESSAGE_USAGE));
        }

        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        if (tags.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterAllByTagCommand.MESSAGE_USAGE));
        }
        ArrayList<String> tagKeywords = new ArrayList<>();
        for (Tag t : tags) {
            tagKeywords.add(t.toString());
        }
        return new FilterAllByTagCommand(new StudyBuddyItemContainsTagPredicate(tags), tagKeywords);
    }

}
