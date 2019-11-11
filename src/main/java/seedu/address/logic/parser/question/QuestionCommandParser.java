package seedu.address.logic.parser.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EMPTY_QUESTION_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_ANSWER;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_EDIT_FIELDS;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_QUESTION;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_QUESTION_OPTIONS;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_QUESTION_OPTIONS_VALUE;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_TEXT_SEARCH;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIONC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTIOND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SLIDESHOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.question.QuestionAddCommand;
import seedu.address.logic.commands.question.QuestionCommand;
import seedu.address.logic.commands.question.QuestionDeleteCommand;
import seedu.address.logic.commands.question.QuestionEditCommand;
import seedu.address.logic.commands.question.QuestionFindCommand;
import seedu.address.logic.commands.question.QuestionListCommand;
import seedu.address.logic.commands.question.QuestionSlideshowCommand;
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

    public static final String HELP_MESSAGE = "Question command has to include an action.\n"
        + "Refer to the help section to view the full list of commands.";

    private static final Logger logger = LogsCenter.getLogger(QuestionCommandParser.class);

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
                PREFIX_DELETE, PREFIX_FIND, PREFIX_SLIDESHOW);

        if (argMultimap.getValue(PREFIX_LIST).isPresent()) { // List command
            return listCommand();
        } else if (argMultimap.getValue(PREFIX_SLIDESHOW).isPresent()) { // Slideshow command
            return slideshowCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Delete command
            return deleteCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_FIND).isPresent()) { // Find command
            return findCommand(argMultimap);
        } else if (!argMultimap.getPreamble().isBlank()) { // Edit command
            return editCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_QUESTION).isPresent()) { // Create command
            return addCommand(argMultimap);
        } else { // No action defined after question command word
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HELP_MESSAGE));
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

        String question = getPrefix(argMultimap, PREFIX_QUESTION, MESSAGE_MISSING_QUESTION);
        String answer = getPrefix(argMultimap, PREFIX_ANSWER, MESSAGE_MISSING_ANSWER);
        String typeName = getPrefix(argMultimap, PREFIX_TYPE, MESSAGE_MISSING_TYPE);

        if (typeName.equals("mcq")) {
            String optionA = getPrefix(argMultimap, PREFIX_OPTIONA,
                String.format(MESSAGE_MISSING_QUESTION_OPTIONS_VALUE, "Option A"));
            String optionB = getPrefix(argMultimap, PREFIX_OPTIONB,
                String.format(MESSAGE_MISSING_QUESTION_OPTIONS_VALUE, "Option B"));
            String optionC = getPrefix(argMultimap, PREFIX_OPTIONC,
                String.format(MESSAGE_MISSING_QUESTION_OPTIONS_VALUE, "Option C"));
            String optionD = getPrefix(argMultimap, PREFIX_OPTIOND,
                String.format(MESSAGE_MISSING_QUESTION_OPTIONS_VALUE, "Option D"));

            if (optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
                logger.info("Missing question options during Add command.");
                throw new ParseException(
                    String
                        .format(MESSAGE_MISSING_QUESTION_OPTIONS));
            }

            return new QuestionAddCommand(question, answer, typeName, optionA, optionB, optionC,
                optionD);
        }

        return new QuestionAddCommand(question, answer, typeName);
    }

    /**
     * Performs validation and return the QuestionEditCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return QuestionEditCommand object.
     * @throws ParseException
     */
    private QuestionEditCommand editCommand(ArgumentMultimap argMultimap)
        throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        // Add parameters to be edited. Note: the fields are optional but need at least one field to change
        // options is compulsory for mcq
        if (noPrefixPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TYPE,
            PREFIX_OPTIONA, PREFIX_OPTIONB, PREFIX_OPTIONC, PREFIX_OPTIOND)) {
            throw new ParseException(MESSAGE_MISSING_EDIT_FIELDS);
        }
        HashMap<String, String> fields = new HashMap<>();
        HashMap<String, String> options = new HashMap<>();

        String question = getPrefix(argMultimap, PREFIX_QUESTION, MESSAGE_MISSING_QUESTION);
        String answer = getPrefix(argMultimap, PREFIX_ANSWER, MESSAGE_MISSING_ANSWER);
        String typeName = getPrefix(argMultimap, PREFIX_TYPE, MESSAGE_MISSING_TYPE);

        fields.put("question", question);
        fields.put("answer", answer);
        fields.put("type", typeName);

        options.put("optionA", getPrefix(argMultimap, PREFIX_OPTIONA,
            String.format(MESSAGE_MISSING_QUESTION_OPTIONS_VALUE, "Option A")));
        options.put("optionB", getPrefix(argMultimap, PREFIX_OPTIONB,
            String.format(MESSAGE_MISSING_QUESTION_OPTIONS_VALUE, "Option B")));
        options.put("optionC", getPrefix(argMultimap, PREFIX_OPTIONC,
            String.format(MESSAGE_MISSING_QUESTION_OPTIONS_VALUE, "Option C")));
        options.put("optionD", getPrefix(argMultimap, PREFIX_OPTIOND,
            String.format(MESSAGE_MISSING_QUESTION_OPTIONS_VALUE, "Option D")));

        return new QuestionEditCommand(index, fields, options);
    }

    /**
     * Performs validation and return the QuestionDeleteCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return QuestionDeleteCommand object.
     * @throws ParseException
     */
    private QuestionDeleteCommand deleteCommand(ArgumentMultimap argMultimap)
        throws ParseException {
        Index indexToDelete;

        try {
            String deletePrefix = argMultimap.getValue(PREFIX_DELETE).orElse("");

            if (deletePrefix.isEmpty()) {
                throw new ParseException(
                    String.format(MESSAGE_EMPTY_QUESTION_DISPLAYED_INDEX));
            } else if (!areInputIndexesValid(deletePrefix)) {
                logger.info("Invalid index entered by user during Delete command.");
                throw new ParseException(
                    String.format(MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX));
            }

            indexToDelete = Index.fromOneBased(Integer.parseInt(deletePrefix));

        } catch (NumberFormatException e) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    QuestionDeleteCommand.MESSAGE_USAGE));
        }

        return new QuestionDeleteCommand(indexToDelete);
    }

    /**
     * Performs validation and return the QuestionSlideshowCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return QuestionSlideshowCommand object.
     * @throws ParseException
     */
    private QuestionSlideshowCommand slideshowCommand(ArgumentMultimap argMultimap)
        throws ParseException {
        String slideshowPrefix = argMultimap.getValue(PREFIX_SLIDESHOW).orElse("");
        if (slideshowPrefix.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_EMPTY_QUESTION_DISPLAYED_INDEX));
        } else if (!areInputIndexesValid(slideshowPrefix)) {
            logger.info("Invalid index entered by user during Slideshow command.");
            throw new ParseException(
                String.format(MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX));
        }

        return new QuestionSlideshowCommand(slideshowPrefix);
    }

    /**
     * Performs validation and return the QuestionListCommand object.
     *
     * @return QuestionListCommand object.
     */
    private QuestionListCommand listCommand() {
        return new QuestionListCommand();
    }

    /**
     * Performs validation and return the QuestionFindCommand object.
     *
     * @param argMultimap for tokenized input.
     * @return QuestionFindCommand object.
     * @throws ParseException
     */
    private QuestionFindCommand findCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_FIND).orElse("").isEmpty()) {
            logger.info("Search string entered by user is empty during Find command.");
            throw new ParseException(MESSAGE_MISSING_TEXT_SEARCH);
        }
        return new QuestionFindCommand(argMultimap.getValue(PREFIX_FIND).orElse(""));
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

    /**
     * Returns true if one of the prefixes contains non empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean noPrefixPresent(ArgumentMultimap argumentMultimap,
        Prefix... prefixes) {
        return Stream.of(prefixes)
            .noneMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Gets the prefix value and throws an error if empty string detected. {@code
     * ArgumentMultimap}.
     *
     * @param prefix of the value.
     * @param error  message to throw.
     * @return prefix value.
     * @throws ParseException
     */
    private String getPrefix(ArgumentMultimap argumentMultimap, Prefix prefix, String error)
        throws ParseException {
        Optional<String> optional = argumentMultimap.getSingleValue(prefix);
        if (optional.isPresent()) { // check for empty string
            optional.filter(StringUtils::isNotEmpty)
                .orElseThrow(() -> new ParseException(error));
            return optional.get();
        } else {
            return "";
        }
    }

    /**
     * Checks and return if indexes input are above zero and contain only digits
     *
     * @param input user input.
     * @return whether input contains valid indexes.
     */
    private boolean areInputIndexesValid(String input) {
        if (Pattern.compile("[1-9][0-9]*|[\\s]+").matcher(input).lookingAt()) {
            // Additional check if remaining input is a number and index is greater than zero
            try {
                return !Arrays.stream(input.trim().replaceAll(" +", " ")
                    .split(" "))
                    .anyMatch((x) -> Integer.parseInt(x) <= 0);
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return false;
    }

}
