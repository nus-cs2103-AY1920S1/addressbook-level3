package seedu.revision.logic.parser.main;

import seedu.revision.logic.commands.main.ListCommand;
import seedu.revision.logic.commands.main.StartQuizCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;
import seedu.revision.model.category.Category;
import seedu.revision.model.quiz.Mode;

import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;

/**
 * Parses input arguments and creates a new StartQuizCommand object
 */
public class StartQuizCommandParser implements Parser<StartQuizCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StartQuizCommand
     * and returns a StartQuizCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StartQuizCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DIFFICULTY, PREFIX_MODE);


        CategoryPredicate categoryPredicate = null;
        DifficultyPredicate difficultyPredicate = null;
        Mode chosenMode = null;

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            Category categoryToFilter = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
            categoryPredicate = new CategoryPredicate(categoryToFilter);
        }

        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            Difficulty difficultyToFilter = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
            difficultyPredicate = new DifficultyPredicate(difficultyToFilter);
        }

        if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            chosenMode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());
        }

        return new StartQuizCommand(categoryPredicate, difficultyPredicate, chosenMode);
    }
}
