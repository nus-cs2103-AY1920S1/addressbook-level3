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
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.Prefix;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.QuestionType;
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.answerable.TrueFalse;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.category.Category;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private Question question;
    private ArrayList<Answer> correctAnswerList;
    private ArrayList<Answer> wrongAnswerList;
    private Difficulty difficulty;
    private Set<Category> categories;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION_TYPE, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_WRONG,
                PREFIX_DIFFICULTY, PREFIX_CATEGORY);

        QuestionType questionType;
        if (arePrefixesPresent(argMultimap, PREFIX_QUESTION_TYPE)) {
            questionType = ParserUtil.parseType(argMultimap.getValue(PREFIX_QUESTION_TYPE).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        validateQuestionType(questionType, argMultimap);

        this.question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        this.difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
        this.categories = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));

        Answerable answerable;

        switch (questionType.getType()) {
        case "mcq":
            answerable = new Mcq(question, correctAnswerList, wrongAnswerList, difficulty, categories);
            break;
        case "tf":
            answerable = new TrueFalse(question, correctAnswerList, difficulty, categories);
            break;
        case "saq":
            answerable = new Saq(question, correctAnswerList, difficulty, categories);
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        requireNonNull(answerable);
        validateAnswerableToAdd(answerable);
        return new AddCommand(answerable);
    }

    /**
     * Validates that the question to be added is either an MCQ, TrueFalse or SAQ.
     * @param questionType type of question to be added.
     * @param argMultimap add multi-map.
     * @return true or false
     * @throws ParseException if question is in the wrong format.
     */
    private boolean validateQuestionType(QuestionType questionType, ArgumentMultimap argMultimap) throws
            ParseException {
        String type = questionType.getType();
        int numCorrect = argMultimap.getAllValues(PREFIX_CORRECT).size();
        int numWrong = argMultimap.getAllValues(PREFIX_WRONG).size();

        if (arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_CATEGORY,
                PREFIX_DIFFICULTY) && argMultimap.getPreamble().isEmpty()) {
            this.correctAnswerList = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_CORRECT));

            switch (type.toLowerCase()) {
            case "mcq":
                if (numCorrect == 1 && numWrong == 3 && arePrefixesPresent(argMultimap, PREFIX_WRONG)) {
                    this.wrongAnswerList = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_WRONG));
                    return true;
                } else {
                    throw new ParseException(Mcq.MESSAGE_CONSTRAINTS);
                }
            case "tf":
                if (numCorrect == 1 && numWrong <= 1) {
                    this.correctAnswerList = new ArrayList<>(Arrays.asList(
                            new Answer(this.correctAnswerList.get(0).answer.toLowerCase())));
                    return true;
                } else {
                    throw new ParseException(TrueFalse.MESSAGE_CONSTRAINTS);
                }
            case "saq":
                return true;
            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
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

        return true;
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
