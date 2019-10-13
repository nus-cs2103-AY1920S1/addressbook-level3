package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_TYPE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.quiz.commands.AddCommand;
import seedu.address.logic.quiz.parser.exceptions.ParseException;
import seedu.address.model.quiz.person.Address;
import seedu.address.model.quiz.person.Email;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Phone;
import seedu.address.model.quiz.person.Question;
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

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_QUESTION).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_ANSWER).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_CATEGORY).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_TYPE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Question question = new Question(name, phone, email, address, tagList);

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
