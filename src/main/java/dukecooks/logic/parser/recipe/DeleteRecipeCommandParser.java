package dukecooks.logic.parser.recipe;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.recipe.DeleteRecipeCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRecipeCommand object
 */
public class DeleteRecipeCommandParser implements Parser<DeleteRecipeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRecipeCommand
     * and returns a DeleteRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRecipeCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteRecipeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecipeCommand.MESSAGE_USAGE), pe);
        }
    }

}
