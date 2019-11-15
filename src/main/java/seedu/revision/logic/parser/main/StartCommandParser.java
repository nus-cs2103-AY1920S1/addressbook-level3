package seedu.revision.logic.parser.main;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_TIMER;
import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import java.util.function.Predicate;

import seedu.revision.logic.commands.main.StartCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;
import seedu.revision.model.category.Category;
import seedu.revision.model.quiz.ArcadeMode;
import seedu.revision.model.quiz.CustomMode;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.NormalMode;

/**
 * Parses input arguments and creates a new StartCommand object
 */
public class StartCommandParser implements Parser<StartCommand> {

    private Predicate<Answerable> combinedPredicate = PREDICATE_SHOW_ALL_ANSWERABLE;

    /**
     * Parses the given {@code String} of arguments in the context of the StartCommand
     * and returns a StartCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StartCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODE, PREFIX_TIMER, PREFIX_DIFFICULTY, PREFIX_CATEGORY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE));
        }

        boolean optionalTimerPrefixIsPresent = argMultimap.getValue(PREFIX_TIMER).isPresent();
        boolean optionalDifficultyPrefixIsPresent = argMultimap.getValue(PREFIX_DIFFICULTY).isPresent();
        boolean optionalCategoryPrefixIsPresent = argMultimap.getValue(PREFIX_CATEGORY).isPresent();
        boolean optionalPrefixesArePresent = optionalTimerPrefixIsPresent || optionalCategoryPrefixIsPresent
                || optionalDifficultyPrefixIsPresent;

        Mode mode;
        int time;

        if (argMultimap.getValue(PREFIX_MODE).isPresent()) {
            mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE));
        }

        switch (mode.toString().toLowerCase()) {
        case "normal":
            if (optionalPrefixesArePresent) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE));
            } else {
                mode = new NormalMode();
                return new StartCommand(mode);
            }
        case "arcade":
            if (optionalPrefixesArePresent) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE));
            } else {
                mode = new ArcadeMode();
                return new StartCommand(mode);
            }
        case "custom":

            if (optionalCategoryPrefixIsPresent) {
                Category categoryToFilter = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
                CategoryPredicate categoryPredicate = new CategoryPredicate(categoryToFilter);
                combinedPredicate = combinedPredicate.and(categoryPredicate);
            }

            if (optionalDifficultyPrefixIsPresent) {
                Difficulty difficultyToFilter = ParserUtil.parseDifficulty(argMultimap
                        .getValue(PREFIX_DIFFICULTY).get());
                DifficultyPredicate difficultyPredicate = new DifficultyPredicate(difficultyToFilter);
                combinedPredicate = combinedPredicate.and(difficultyPredicate);
            }

            mode = ((CustomMode) mode).withCombinedPredicate(combinedPredicate);

            if (optionalTimerPrefixIsPresent) {
                time = ParserUtil.parseTimer(argMultimap.getValue(PREFIX_TIMER).get());
                mode = ((CustomMode) mode).withTime(time);
            }
            mode = ((CustomMode) mode).build();
            return new StartCommand(mode);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartCommand.MESSAGE_USAGE));
        }


    }
}
