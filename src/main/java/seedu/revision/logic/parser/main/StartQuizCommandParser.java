package seedu.revision.logic.parser.main;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_TIMER;

import seedu.revision.logic.commands.main.StartQuizCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.category.Category;
import seedu.revision.model.quiz.ArcadeMode;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.NormalMode;

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
                ArgumentTokenizer.tokenize(args, PREFIX_MODE, PREFIX_TIMER, PREFIX_DIFFICULTY, PREFIX_CATEGORY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartQuizCommand.MESSAGE_USAGE));
        }

        boolean optionalTimerPrefixIsPresent = argMultimap.getValue(PREFIX_TIMER).isPresent();
        boolean optionalDifficultyPrefixIsPresent = argMultimap.getValue(PREFIX_DIFFICULTY).isPresent();
        boolean optionalCategoryPrefixIsPresent = argMultimap.getValue(PREFIX_CATEGORY).isPresent();
        boolean optionalPrefixesArePresent = optionalTimerPrefixIsPresent || optionalCategoryPrefixIsPresent
                    || optionalDifficultyPrefixIsPresent;

        Mode mode;
        Difficulty difficulty;
        Category category;
        int time;

        if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());
        } else {
            throw new ParseException(StartQuizCommand.MESSAGE_USAGE);
        }

        switch (mode.toString().toLowerCase()) {
        case "normal":
            if (optionalPrefixesArePresent) {
                throw new ParseException(StartQuizCommand.MESSAGE_USAGE);
            } else {
                mode = new NormalMode();
                return new StartQuizCommand(mode);
            }
        case "arcade":
            if (optionalPrefixesArePresent) {
                throw new ParseException(StartQuizCommand.MESSAGE_USAGE);
            } else {
                mode = new ArcadeMode();
                return new StartQuizCommand(mode);
            }
        //case "custom":
        //    Predicate<Answerable> combinedPredicate = PREDICATE_SHOW_ALL_ANSWERABLE;

        //    if (optionalCategoryPrefixIsPresent) {
        //        Category categoryToFilter = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        //        CategoryPredicate categoryPredicate = new CategoryPredicate(categoryToFilter);
        //        combinedPredicate = combinedPredicate.and(categoryPredicate);
        //    }

        //    if (optionalDifficultyPrefixIsPresent) {
        //        Difficulty difficultyToFilter = ParserUtil.parseDifficulty(argMultimap.getValue(
        //            PREFIX_DIFFICULTY).get());
        //        DifficultyPredicate difficultyPredicate = new DifficultyPredicate(difficultyToFilter);
        //        combinedPredicate = combinedPredicate.and(difficultyPredicate);
        //    }

        //    if (optionalTimerPrefixIsPresent) {
        //        time =  ParserUtil.parseTimer(argMultimap.getValue(PREFIX_TIMER).get());
        //    }
        //    mode = new CustomMode().with
        //    break;
        default:
            throw new ParseException(StartQuizCommand.MESSAGE_USAGE);
        }
    }
}
