package seedu.address.logic.parser.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIOND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.question.QuestionAddCommand;
import seedu.address.logic.commands.question.QuestionCommand;
import seedu.address.logic.commands.question.QuestionDeleteCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.commands.question.QuestionListCommand;
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
            .tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TYPE, PREFIX_LIST,
                PREFIX_OPTIONA, PREFIX_OPTIONB, PREFIX_OPTIONC, PREFIX_OPTIOND,
                PREFIX_DELETE);

        boolean isEdit = false;
        Index index = Index.fromZeroBased(0);
        try {
            String preamble = argMultimap.getPreamble();

            if (!preamble.isBlank()) {
                index = ParserUtil.parseIndex(preamble);
                isEdit = true;
            }
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuestionEditCommand.MESSAGE_USAGE),
                pe);
        }

        if (argMultimap.getValue(PREFIX_LIST).isPresent()) { // List command
            return new QuestionListCommand();
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Delete command
            return deleteCommand(index, argMultimap);
        } else if (isEdit) { // Edit command
            return editCommand(index, argMultimap);
        } else { // Create command
            return addCommand(argMultimap);
        }

    }

    /**
     * Performs validation and return the QuestionAddCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return QuestionAddCommand object.
     * @throws ParseException
     */
    private QuestionAddCommand addCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TYPE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, QuestionAddCommand.MESSAGE_USAGE));
        }

        String question = argMultimap.getValue(PREFIX_QUESTION).orElse("");
        String answer = argMultimap.getValue(PREFIX_ANSWER).orElse("");
        String typeName = argMultimap.getValue(PREFIX_TYPE).orElse("");

        // Only for mcq
        // TODO: Check missing options and throw exception if necessary
        String optionA = argMultimap.getValue(PREFIX_OPTIONA).orElse("");
        String optionB = argMultimap.getValue(PREFIX_OPTIONB).orElse("");
        String optionC = argMultimap.getValue(PREFIX_OPTIONC).orElse("");
        String optionD = argMultimap.getValue(PREFIX_OPTIOND).orElse("");

        if (typeName.equals("mcq")) {
            return new QuestionAddCommand(question, answer, typeName, optionA, optionB, optionC,
                optionD);
        }

        return new QuestionAddCommand(question, answer, typeName);
    }

    /**
     * Performs validation and return the QuestionEditCommand object.
     *
     * @param index       of question in the list.
     * @param argMultimap for tokenized input.
     * @return QuestionEditCommand object.
     * @throws ParseException
     */
    private QuestionEditCommand editCommand(Index index, ArgumentMultimap argMultimap) {
        // Add parameters to be edited. Note: the fields are optional
        // options is compulsory for mcq
        HashMap<String, String> fields = new HashMap<>();
        HashMap<String, String> options = new HashMap<>();

        String question = argMultimap.getValue(PREFIX_QUESTION).orElse("");
        String answer = argMultimap.getValue(PREFIX_ANSWER).orElse("");
        String typeName = argMultimap.getValue(PREFIX_TYPE).orElse("");

        fields.put("question", question);
        fields.put("answer", answer);
        fields.put("type", typeName);

        options.put("optionA", argMultimap.getValue(PREFIX_OPTIONA).orElse(""));
        options.put("optionB", argMultimap.getValue(PREFIX_OPTIONB).orElse(""));
        options.put("optionC", argMultimap.getValue(PREFIX_OPTIONC).orElse(""));
        options.put("optionD", argMultimap.getValue(PREFIX_OPTIOND).orElse(""));

        return new QuestionEditCommand(index, fields, options);
    }

    /**
     * Performs validation and return the QuestionDeleteCommand object.
     *
     * @param index       of question in the list.
     * @param argMultimap for tokenized input.
     * @return QuestionDeleteCommand object.
     * @throws ParseException
     */
    private QuestionDeleteCommand deleteCommand(Index index, ArgumentMultimap argMultimap)
        throws ParseException {
        try {
            int indexToDelete = Integer
                .parseInt(argMultimap.getValue(PREFIX_DELETE).orElse("0"));

            if (indexToDelete <= 0) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        QuestionDeleteCommand.MESSAGE_USAGE));
            }
            index.fromOneBased(indexToDelete);
        } catch (NumberFormatException e) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    QuestionDeleteCommand.MESSAGE_USAGE));
        }

        return new QuestionDeleteCommand(index);
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
