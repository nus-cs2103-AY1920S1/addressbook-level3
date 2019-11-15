package seedu.revision.logic.parser.main;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import java.util.function.Predicate;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.ListCommand;
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

/**
 * Adds a filter to the list command.
 * Can filter by category or difficulty of questions available.
 * Checks whether category or difficulty parsed in is available before executing list command to filter.
 */
public class ListCommandParser implements Parser {
    @Override
    public Command parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DIFFICULTY);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        Predicate<Answerable> combinedPredicate = PREDICATE_SHOW_ALL_ANSWERABLE;

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            Category categoryToFilter = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
            CategoryPredicate categoryPredicate = new CategoryPredicate(categoryToFilter);
            combinedPredicate = combinedPredicate.and(categoryPredicate);
        }

        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            Difficulty difficultyToFilter = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
            DifficultyPredicate difficultyPredicate = new DifficultyPredicate(difficultyToFilter);
            combinedPredicate = combinedPredicate.and(difficultyPredicate);
        }

        return new ListCommand(combinedPredicate);
    }
}
