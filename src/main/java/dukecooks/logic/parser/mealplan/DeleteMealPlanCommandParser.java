package dukecooks.logic.parser.mealplan;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.mealplan.DeleteMealPlanCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMealPlanCommand object
 */
public class DeleteMealPlanCommandParser implements Parser<DeleteMealPlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMealPlanCommand
     * and returns a DeleteMealPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMealPlanCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMealPlanCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMealPlanCommand.MESSAGE_USAGE), pe);
        }
    }

}
