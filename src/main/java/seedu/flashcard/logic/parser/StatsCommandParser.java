package seedu.flashcard.logic.parser;

import seedu.flashcard.logic.commands.StatsCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.tag.Tag;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;

public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the string of arguments to be calculated by tags.
     *
     * @param args string containing the parameters for the target tags
     * @return new {@code StatsCommand}
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        }

        if(!arePrefixesPresent(argMultimap, PREFIX_TAG)){
            return new StatsCommand(null);
        }else {
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            return new StatsCommand(tagList);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

