package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_CHOICE;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.flashcard.logic.commands.AddCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * Parses input arguments to generate {@Code AddCommand}
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final String ANSWER_CHOICE_MISMATCH = "The answer must be the same as a given choice.";
    /**
     * Parses the string of arguments to be added.
     * @param args string containing the parameters for the new flashcard
     * @return new add command
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_QUESTION, PREFIX_CHOICE, PREFIX_DEFINITION, PREFIX_TAG, PREFIX_ANSWER);
        if (!arePrefixesPresent(argMultimap,
            PREFIX_QUESTION, PREFIX_DEFINITION, PREFIX_ANSWER)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + AddCommand.MESSAGE_USAGE));
        }

        Question question = ParserUtil.parseWord(argMultimap.getValue(PREFIX_QUESTION).get());
        List<Choice> choices = ParserUtil.parseChoices(argMultimap.getAllValues(PREFIX_CHOICE));
        Definition definition = ParserUtil.parseDefinition(argMultimap.getValue(PREFIX_DEFINITION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Answer answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());

        Flashcard flashcard;
        if (arePrefixesPresent(argMultimap, PREFIX_CHOICE)) {
            if (!choices.contains(new Choice(answer.getAnswer()))) {
                throw new ParseException(ANSWER_CHOICE_MISMATCH);
            } else {
                flashcard = new McqFlashcard(question, choices, definition, tagList, answer);
            }
        } else {
            flashcard = new ShortAnswerFlashcard(question, definition, tagList, answer);
        }

        return new AddCommand(flashcard);
    }
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
