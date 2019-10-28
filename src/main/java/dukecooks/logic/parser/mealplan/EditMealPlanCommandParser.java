package dukecooks.logic.parser.mealplan;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand.EditMealPlanDescriptor;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditMealPlanCommand object
 */
public class EditMealPlanCommandParser implements Parser<EditMealPlanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditMealPlanCommand
     * and returns an EditMealPlanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditMealPlanCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditMealPlanCommand.MESSAGE_USAGE), pe);
        }

        EditMealPlanDescriptor editMealPlanDescriptor = new EditMealPlanDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editMealPlanDescriptor.setName(ParserUtil.parseMealPlanName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (!editMealPlanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditMealPlanCommand.MESSAGE_NOT_EDITED);
        }


        return new EditMealPlanCommand(index, editMealPlanDescriptor);
    }
}
