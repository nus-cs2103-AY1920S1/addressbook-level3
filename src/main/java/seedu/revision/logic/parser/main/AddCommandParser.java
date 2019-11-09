package seedu.revision.logic.parser.main;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CORRECT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION_TYPE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_WRONG;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.Prefix;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.QuestionType;
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.answerable.TrueFalse;
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

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION_TYPE, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_CATEGORY,
                PREFIX_DIFFICULTY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        QuestionType questionType = ParserUtil.parseType(argMultimap.getValue(PREFIX_QUESTION_TYPE).get());
        Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Difficulty difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
        Set<Category> categories = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));
        ArrayList<Answer> correctAnswerList = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_CORRECT));
        ArrayList<Answer> wrongAnswerList = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_WRONG));

        Answerable answerable = Answerable.create(questionType.getType(), question, correctAnswerList,
                wrongAnswerList, difficulty, categories);

        requireNonNull(answerable);
        validateAnswerableToAdd(answerable);
        return new AddCommand(answerable);
    }

    /**
     * Validates the answerable by its type.
     * @param answerableToAdd the answerable that is being added.
     * @return a boolean to determine if answerable is valid. True if valid. False otherwise.
     * @throws ParseException exception is thrown if answerable is not in the correct format.
     */
    private boolean validateAnswerableToAdd(Answerable answerableToAdd)
            throws ParseException {
        if (answerableToAdd instanceof Mcq) {
            if (!Mcq.isValidMcq((Mcq) answerableToAdd)) {
                throw new ParseException(Mcq.MESSAGE_CONSTRAINTS);
            }
        }
        if (answerableToAdd instanceof TrueFalse) {
            if (!TrueFalse.isValidTrueFalse((TrueFalse) answerableToAdd)) {
                throw new ParseException(TrueFalse.MESSAGE_CONSTRAINTS);
            }
        }
        if (answerableToAdd instanceof Saq) {
            if (!Saq.isValidSaq((Saq) answerableToAdd)) {
                throw new ParseException(Saq.MESSAGE_CONSTRAINTS);
            }
            areSaqAnswersValid(answerableToAdd.getCorrectAnswerList(), answerableToAdd.getQuestion());
        }

        return true;
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the answers is 'exit'
     * @param answerList the list of correct answers
     * @return a boolean to determine if saq answer is valid. True if valid. False otherwise.
     * @throws ParseException exception is thrown if answer is 'exit'.
     */
    private boolean areSaqAnswersValid(ArrayList<Answer> answerList, Question question) throws ParseException {
        for (Answer answer : answerList) {
            if (answer.getAnswer().toLowerCase().trim().equals("exit")) {
                throw new ParseException(Saq.MESSAGE_INVALID_ANSWER_EXIT);
            }

            if (FuzzySearch.tokenSetRatio(answer.getAnswer(), question.question) == 100) {
                throw new ParseException(Saq.MESSAGE_INVALID_ANSWER);
            }
        }
        return true;
    }

}
