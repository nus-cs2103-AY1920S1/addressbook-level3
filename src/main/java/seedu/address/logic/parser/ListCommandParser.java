package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.predicates.CategoryPredicate;
import seedu.address.model.answerable.predicates.DifficultyPredicate;

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
