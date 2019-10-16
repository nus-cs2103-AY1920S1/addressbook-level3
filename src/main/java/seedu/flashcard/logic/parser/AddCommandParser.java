package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_CHOICE;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_WORD;

import java.util.Set;
import java.util.stream.Stream;

import seedu.flashcard.logic.commands.AddCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.Word;
import seedu.flashcard.model.tag.Tag;

/**
 * Parses input arguments to generate {@Code AddCommand}
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the string of arguments to be added.
     * @param args string containing the parameters for the new flashcard
     * @return new add command
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WORD, PREFIX_CHOICE, PREFIX_DEFINITION, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_WORD, PREFIX_CHOICE, PREFIX_DEFINITION, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Word word = ParserUtil.parseWord(argMultimap.getValue(PREFIX_WORD).get());
        Set<Choice> choices = ParserUtil.parseChoices(argMultimap.getAllValues(PREFIX_CHOICE));
        Definition definition = ParserUtil.parseDefinition(argMultimap.getValue(PREFIX_DEFINITION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Flashcard flashcard = new Flashcard(word, choices, definition, tagList);

        return new AddCommand(flashcard);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
