package seedu.revision.logic.parser;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CORRECT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION_TYPE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_WRONG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.revision.logic.commands.AddCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.category.Category;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION_TYPE, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_WRONG,
                PREFIX_DIFFICULTY, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_WRONG, PREFIX_CATEGORY,
                PREFIX_DIFFICULTY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        QuestionType questionType = ParserUtil.parseType(argMultimap.getValue(PREFIX_QUESTION_TYPE).get());
        Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Set<Answer> correctAnswerSet = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_CORRECT));
        Set<Answer> wrongAnswerSetSet = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_WRONG));
        Difficulty difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
        Set<Category> categories = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));

        if (questionType.getType().equals("mcq")) {
            if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_WRONG, PREFIX_CATEGORY,
                    PREFIX_DIFFICULTY) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } else if (questionType.getType().equals("saq")) {
            if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_CATEGORY,
                    PREFIX_DIFFICULTY) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Answerable answerable;

        switch (questionType.getType()) {
        case "mcq":
            answerable = new Mcq(question, correctAnswerSet, wrongAnswerSetSet, difficulty, categories);
            return new AddCommand(answerable);
        case "saq":
            //TODO: Implement Saq properly i.e. remove need to add wrongAnswerSet
            answerable = new Saq(question, correctAnswerSet, wrongAnswerSetSet, difficulty, categories);
            return new AddCommand(answerable);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
