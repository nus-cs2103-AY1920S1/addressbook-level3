package seedu.algobase.logic.parser.tag;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG_COLOR;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.tag.EditTagCommand;
import seedu.algobase.logic.parser.ArgumentMultimap;
import seedu.algobase.logic.parser.ArgumentTokenizer;
import seedu.algobase.logic.parser.Parser;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.Prefix;
import seedu.algobase.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTagCommand object
 */
public class EditTagCommandParser implements Parser<EditTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTagCommand
     * and returns an EditTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public EditTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_TAG_COLOR);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE), pe);
        }

        Optional<String> name;
        if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            name = Optional.ofNullable(argMultimap.getValue(PREFIX_TAG).get());
        } else {
            name = Optional.empty();
        }
        Optional<String> color;
        if (arePrefixesPresent(argMultimap, PREFIX_TAG_COLOR)) {
            color = Optional.ofNullable(argMultimap.getValue(PREFIX_TAG_COLOR).get());
        } else {
            color = Optional.empty();
        }

        return new EditTagCommand(index, name, color);
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
