package seedu.revision.logic.parser.main;

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

import seedu.revision.logic.commands.main.AddCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.Prefix;
import seedu.revision.logic.parser.QuestionType;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.Saq;
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

        //assert validateQuestionType(questionType, argMultimap) : "question not valid according to type";
        validateQuestionType(questionType, argMultimap);

        this.question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        this.correctAnswerList = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_CORRECT));
        this.difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
        this.categories = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));

        Answerable answerable;

        switch (questionType.getType()) {
        case "mcq":
            answerable = new Mcq(question, correctAnswerList, wrongAnswerList, difficulty, categories);
            return new AddCommand(answerable);
        case "saq":
            answerable = new Saq(question, correctAnswerList, difficulty, categories);
            return new AddCommand(answerable);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Validates that the question to be added is either an MCQ or SAQ.
     * @param questionType type of question to be added.
     * @param argMultimap
     * @return true or false
     * @throws ParseException if question is in the wrong format.
     */
    private boolean validateQuestionType(QuestionType questionType, ArgumentMultimap argMultimap)
            throws ParseException {

        switch (questionType.getType()) {
        case "mcq":
            if (arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_WRONG, PREFIX_CATEGORY,
                    PREFIX_DIFFICULTY) && argMultimap.getPreamble().isEmpty()) {

                int numCorrect = argMultimap.getAllValues(PREFIX_CORRECT).size();
                int numWrong = argMultimap.getAllValues(PREFIX_WRONG).size();

                if (numCorrect == 1 && numWrong == 3) {
                    this.wrongAnswerList = ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_WRONG));
                    return true;
                } else {
                    throw new ParseException(Mcq.MESSAGE_CONSTRAINTS);
                }
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        case "saq":
            if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_CATEGORY,
                    PREFIX_DIFFICULTY) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            return true;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
