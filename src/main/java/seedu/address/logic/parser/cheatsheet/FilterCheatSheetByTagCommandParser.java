package seedu.address.logic.parser.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.logic.commands.cheatsheet.FilterCheatSheetByTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cheatsheet.CheatSheetContainsTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterCheatSheetByTagCommand object
 */
public class FilterCheatSheetByTagCommandParser implements Parser<FilterCheatSheetByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCheatSheetByTagCommand
     * and returns a FilterCheatSheetByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCheatSheetByTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCheatSheetByTagCommand.MESSAGE_USAGE));
        }

        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        if (tags.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCheatSheetByTagCommand.MESSAGE_USAGE));
        }
        ArrayList<String> tagKeywords = new ArrayList<>();
        for (Tag t : tags) {
            tagKeywords.add(t.toString());
        }
        return new FilterCheatSheetByTagCommand(new CheatSheetContainsTagPredicate(tags), tagKeywords);
    }

}
