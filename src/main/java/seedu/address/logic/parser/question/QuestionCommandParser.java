package seedu.address.logic.parser.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.question.QuestionAddCommand;
import seedu.address.logic.commands.question.QuestionCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class QuestionCommandParser implements Parser<QuestionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreationQuestionCommand
     * and returns an CreateQuestionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public QuestionCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
            .tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TYPE);

        boolean isEdit = false;
        Index index = Index.fromZeroBased(0);
        try {
            String preamble = argMultimap.getPreamble();

            if(!preamble.isBlank()) {
                index = ParserUtil.parseIndex(preamble);
                isEdit = true;
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        // Checking if command is referring to edit
        if(isEdit){

            // Add parameters to be edited. Note: the fields are optional
            HashMap<String, String> fields = new HashMap<>();
            fields.put("question", argMultimap.getValue(PREFIX_QUESTION).orElse(""));
            fields.put("answer", argMultimap.getValue(PREFIX_ANSWER).orElse(""));
            fields.put("type", argMultimap.getValue(PREFIX_TYPE).orElse(""));

            return new QuestionEditCommand(index, fields);
        }
        else{
            if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuestionAddCommand.MESSAGE_USAGE));
            }

            String question = argMultimap.getValue(PREFIX_QUESTION).orElse("");
            String answer = argMultimap.getValue(PREFIX_ANSWER).orElse("");
            String typeName = argMultimap.getValue(PREFIX_TYPE).orElse("");

            return new QuestionAddCommand(question, answer, typeName);
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
        Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
