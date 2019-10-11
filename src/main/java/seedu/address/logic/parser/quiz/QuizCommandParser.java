package seedu.address.logic.parser.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE_AUTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE_MANUAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_QUESTIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUIZ;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUIZ_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUIZ_QUESTION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.logic.commands.quiz.QuizAddQuestionCommand;
import seedu.address.logic.commands.quiz.QuizCommand;
import seedu.address.logic.commands.quiz.QuizCreateAutomaticallyCommand;
import seedu.address.logic.commands.quiz.QuizCreateManuallyCommand;
import seedu.address.logic.commands.quiz.QuizGetQuestionsAndAnswersCommand;
import seedu.address.logic.commands.quiz.QuizRemoveQuestionCommand;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class QuizCommandParser implements Parser<QuizCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the QuizCommand
     * and returns an QuizCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public QuizCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_QUIZ, PREFIX_MODE_AUTO, PREFIX_MODE_MANUAL,
                        PREFIX_QUIZ_ID, PREFIX_NUM_QUESTIONS, PREFIX_QUESTION_NUMBER,
                        PREFIX_QUIZ_QUESTION_NUMBER, PREFIX_TYPE, PREFIX_LIST);

        if (argMultimap.getValue(PREFIX_MODE_AUTO).isPresent()) { // Create auto command
            if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID, PREFIX_NUM_QUESTIONS, PREFIX_TYPE)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String
                                .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCreateAutomaticallyCommand.MESSAGE_USAGE));
            }

            String quizID = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
            int numQuestions = Integer.parseInt(argMultimap.getValue(PREFIX_NUM_QUESTIONS).orElse(""));
            String typeName = argMultimap.getValue(PREFIX_TYPE).orElse("");

            return new QuizCreateAutomaticallyCommand(quizID, numQuestions, typeName);
        } else if (argMultimap.getValue(PREFIX_MODE_MANUAL).isPresent()) { // Create manual command
            if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID, PREFIX_QUESTION_NUMBER)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String
                                .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCreateManuallyCommand.MESSAGE_USAGE));
            }

            HashMap<String, String> fields = new HashMap<>();
            fields.put("quizID", argMultimap.getValue(PREFIX_QUIZ_ID).orElse(""));
            fields.put("questionNumbers", argMultimap.getValue(PREFIX_QUESTION_NUMBER).orElse(""));

            return new QuizCreateManuallyCommand(fields);
        } else if (argMultimap.getValue(PREFIX_QUESTION_NUMBER).isPresent()) { // Add command
            if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID, PREFIX_QUIZ_QUESTION_NUMBER)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String
                                .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizAddQuestionCommand.MESSAGE_USAGE));
            }

            String quizID = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
            int questionNumber = Integer.parseInt(argMultimap.getValue(PREFIX_QUESTION_NUMBER).orElse(""));
            int quizQuestionNumber = Integer.parseInt(argMultimap.getValue(PREFIX_QUIZ_QUESTION_NUMBER).orElse(""));

            return new QuizAddQuestionCommand(quizID, questionNumber, quizQuestionNumber);
        } else if (argMultimap.getValue(PREFIX_QUIZ_QUESTION_NUMBER).isPresent()) { // Remove command
            if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String
                                .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizRemoveQuestionCommand.MESSAGE_USAGE));
            }

            String quizID = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
            int quizQuestionNumber = Integer.parseInt(argMultimap.getValue(PREFIX_QUIZ_QUESTION_NUMBER).orElse(""));

            return new QuizRemoveQuestionCommand(quizID, quizQuestionNumber);
        } else { // List command
            if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String
                                .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizGetQuestionsAndAnswersCommand.MESSAGE_USAGE));
            }

            String quizID = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
            return new QuizGetQuestionsAndAnswersCommand(quizID);
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
