package seedu.address.logic.parser.quiz;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE_AUTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE_MANUAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_QUESTIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUIZ;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUIZ_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUIZ_QUESTION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHOW_ANSWERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHOW_QUESTIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.logic.commands.quiz.QuizAddQuestionCommand;
import seedu.address.logic.commands.quiz.QuizCommand;
import seedu.address.logic.commands.quiz.QuizCreateAutomaticallyCommand;
import seedu.address.logic.commands.quiz.QuizCreateManuallyCommand;
import seedu.address.logic.commands.quiz.QuizDeleteQuestionCommand;
import seedu.address.logic.commands.quiz.QuizExportCommand;
import seedu.address.logic.commands.quiz.QuizListQuestionsAndAnswersCommand;
import seedu.address.logic.commands.quiz.QuizShowAnswersCommand;
import seedu.address.logic.commands.quiz.QuizShowQuestionsCommand;

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
                .tokenize(args, PREFIX_ADD, PREFIX_DELETE, PREFIX_QUIZ, PREFIX_MODE_AUTO, PREFIX_MODE_MANUAL,
                        PREFIX_QUIZ_ID, PREFIX_NUM_QUESTIONS, PREFIX_QUESTION_NUMBER, PREFIX_SHOW_QUESTIONS,
                        PREFIX_SHOW_ANSWERS, PREFIX_EXPORT, PREFIX_QUIZ_QUESTION_NUMBER, PREFIX_TYPE, PREFIX_LIST);

        if (argMultimap.getValue(PREFIX_MODE_AUTO).isPresent()) { // Create auto command
            return createAutomaticallyCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_MODE_MANUAL).isPresent()) { // Create manual command
            return createManuallyCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_ADD).isPresent()) { // Add command
            return addQuestionCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Remove command
            return removeQuestionCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_EXPORT).isPresent()) { // Export command
            return exportCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_SHOW_QUESTIONS).isPresent()) { // List questions command
            return listQuestionsCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_SHOW_ANSWERS).isPresent()) { // List answers command
            return listAnswersCommand(argMultimap);
        } else { // List questions and answers command
            return listQuestionsAndAnswersCommand(argMultimap);
        }
    }

    /**
     * Creates a quiz by number of questions and quiz ID.
     * @param argMultimap Arguments Multimap.
     * @return Quiz create automatically command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private QuizCreateAutomaticallyCommand createAutomaticallyCommand(ArgumentMultimap argMultimap)
            throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID, PREFIX_NUM_QUESTIONS, PREFIX_TYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizCreateAutomaticallyCommand.MESSAGE_USAGE));
        }

        String quizId = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
        String numQuestions = argMultimap.getValue(PREFIX_NUM_QUESTIONS).orElse("");
        int intNumQuestions = -1;
        if (!numQuestions.equals("")) {
            if (isNumeric(numQuestions)) {
                intNumQuestions = Integer.parseInt(numQuestions);
            }
        }
        String typeName = argMultimap.getValue(PREFIX_TYPE).orElse("");

        return new QuizCreateAutomaticallyCommand(quizId, intNumQuestions, typeName);
    }

    /**
     * Creates a quiz by specifying individual questions and quiz ID.
     * @param argMultimap Arguments Multimap.
     * @return Quiz create manually command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private QuizCreateManuallyCommand createManuallyCommand(ArgumentMultimap argMultimap)
            throws ParseException {
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
    }

    /**
     * Adds a question to an existing quiz.
     * @param argMultimap Arguments Multimap.
     * @return Quiz add question command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private QuizAddQuestionCommand addQuestionCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID, PREFIX_QUESTION_NUMBER, PREFIX_QUIZ_QUESTION_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizAddQuestionCommand.MESSAGE_USAGE));
        }

        String quizId = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
        String questionNumber = argMultimap.getValue(PREFIX_QUESTION_NUMBER).orElse("");
        String quizQuestionNumber = argMultimap.getValue(PREFIX_QUIZ_QUESTION_NUMBER).orElse("");
        int intQuestionNumber = -1;
        if (!questionNumber.equals("")) {
            if (isNumeric(questionNumber)) {
                intQuestionNumber = Integer.parseInt(questionNumber);
            }
        }
        int intQuizQuestionNumber = -1;
        if (!quizQuestionNumber.equals("")) {
            if (isNumeric(quizQuestionNumber)) {
                intQuizQuestionNumber = Integer.parseInt(quizQuestionNumber);
            }
        }

        if (intQuestionNumber == 0) {
            intQuestionNumber = -1;
        }
        if (intQuizQuestionNumber == 0) {
            intQuizQuestionNumber = -1;
        }

        return new QuizAddQuestionCommand(quizId, intQuestionNumber, intQuizQuestionNumber);
    }

    /**
     * Removes a question from an existing quiz.
     * @param argMultimap Arguments Multimap.
     * @return Quiz remove question command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private QuizDeleteQuestionCommand removeQuestionCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID, PREFIX_QUIZ_QUESTION_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizDeleteQuestionCommand.MESSAGE_USAGE));
        }

        String quizId = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
        String quizQuestionNumber = argMultimap.getValue(PREFIX_QUIZ_QUESTION_NUMBER).orElse("");
        int intQuizQuestionNumber = -1;
        if (!quizQuestionNumber.equals("")) {
            if (isNumeric(quizQuestionNumber)) {
                intQuizQuestionNumber = Integer.parseInt(quizQuestionNumber);
            }
        }

        if (intQuizQuestionNumber == 0) {
            intQuizQuestionNumber = -1;
        }

        return new QuizDeleteQuestionCommand(quizId, intQuizQuestionNumber);
    }

    /**
     * Exports an existing quiz.
     * @param argMultimap Arguments Multimap.
     * @return Quiz export command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private QuizExportCommand exportCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, QuizExportCommand.MESSAGE_USAGE));
        }

        String quizId = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
        return new QuizExportCommand(quizId);
    }

    /**
     * Shows the questions of an existing quiz.
     * @param argMultimap Arguments Multimap.
     * @return Quiz list questions command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private QuizShowQuestionsCommand listQuestionsCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    QuizShowQuestionsCommand.MESSAGE_USAGE));
        }

        String quizId = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
        return new QuizShowQuestionsCommand(quizId);
    }

    /**
     * Shows the answers of an existing quiz.
     * @param argMultimap Arguments Multimap.
     * @return Quiz list answers command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private QuizShowAnswersCommand listAnswersCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    QuizShowAnswersCommand.MESSAGE_USAGE));
        }

        String quizId = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
        return new QuizShowAnswersCommand(quizId);
    }

    /**
     * Shows the questions and answers of an existing quiz.
     * @param argMultimap Arguments Multimap.
     * @return Quiz list questions and answers command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private QuizListQuestionsAndAnswersCommand listQuestionsAndAnswersCommand(ArgumentMultimap argMultimap)
            throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_QUIZ_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    QuizListQuestionsAndAnswersCommand.MESSAGE_USAGE));
        }

        String quizId = argMultimap.getValue(PREFIX_QUIZ_ID).orElse("");
        return new QuizListQuestionsAndAnswersCommand(quizId);
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
