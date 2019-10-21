package seedu.revision.logic.parser;

import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.ListCommand;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.category.Category;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;

public class ListCommandParser implements Parser {
    @Override
    public Command parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DIFFICULTY);


        CategoryPredicate categoryPredicate = null;
        DifficultyPredicate difficultyPredicate = null;

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            Category categoryToFilter = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
            categoryPredicate = new CategoryPredicate(categoryToFilter);
        }

        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            Difficulty difficultyToFilter = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
            difficultyPredicate = new DifficultyPredicate(difficultyToFilter);
        }

        return new ListCommand(categoryPredicate, difficultyPredicate);
    }
}
