package seedu.flashcard.logic.parser;

import java.util.stream.Stream;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import seedu.flashcard.logic.commands.DeleteTagCommand;
import seedu.flashcard.logic.commands.ListCardByTagCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.tag.Tag;

/**
 * Parses the user inputs into a {@code DeleteTagCommand}
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    @Override
    public DeleteTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_TAG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCardByTagCommand.MESSAGE_USAGE));
        }

        Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());

        return new DeleteTagCommand(tag);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
