package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.quiz.commands.AddCommand.MESSAGE_STRING_LIMIT_EXCEEDED;
import static seedu.address.logic.quiz.commands.AddCommand.MESSAGE_TOO_SHORT;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TYPE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.quiz.commands.AddCommand;
import seedu.address.logic.quiz.parser.exceptions.ParseException;
import seedu.address.model.quiz.person.Answer;
import seedu.address.model.quiz.person.Category;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Question;
import seedu.address.model.quiz.person.Type;
import seedu.address.model.quiz.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER,
                        PREFIX_CATEGORY, PREFIX_TYPE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_CATEGORY,
                PREFIX_TYPE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Type type = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (name.fullName.length() < 3) {
            throw new ParseException(String.format(MESSAGE_TOO_SHORT));
        }

        if (name.fullName.length() > 200 || answer.value.length() > 125 || category.value.length() > 50) {
            throw new ParseException(String.format(MESSAGE_STRING_LIMIT_EXCEEDED));
        }

        Question question = new Question(name, null, answer, category, type, tagList);

        return new AddCommand(question);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
