package seedu.address.logic.parser.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.question.CreateQuestionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.QuestionType;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class CreateQuestionCommandParser implements Parser<CreateQuestionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreationQuestionCommand
     * and returns an CreateQuestionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateQuestionCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TYPE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateQuestionCommand.MESSAGE_USAGE));
        }

        String question = argMultimap.getValue(PREFIX_QUESTION).orElse("");
        String answer = argMultimap.getValue(PREFIX_ANSWER).orElse("");
        String typeName = argMultimap.getValue(PREFIX_TYPE).orElse("");
        QuestionType type;
        switch (typeName) {
        case "open": type = QuestionType.OPEN;
            break;
        case "mcq": type = QuestionType.MCQ;
            break;
        default: type = QuestionType.OPEN;
            break;
        }

        return new CreateQuestionCommand(question, answer, type);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}