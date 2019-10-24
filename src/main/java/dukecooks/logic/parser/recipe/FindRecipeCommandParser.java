package dukecooks.logic.parser.recipe;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import dukecooks.logic.commands.recipe.FindRecipeCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.recipe.components.RecipeNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindRecipeCommand object
 */
public class FindRecipeCommandParser implements Parser<FindRecipeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindRecipeCommand
     * and returns a FindRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindRecipeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindRecipeCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindRecipeCommand(new RecipeNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
