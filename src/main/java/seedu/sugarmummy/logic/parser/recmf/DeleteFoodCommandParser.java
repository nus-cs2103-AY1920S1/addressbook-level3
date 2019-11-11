package seedu.sugarmummy.logic.parser.recmf;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_NAME;

import seedu.sugarmummy.logic.commands.recmf.DeleteFoodCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.recmf.FoodName;

/**
 * Parses input arguments and creates a new DeleteFoodCommand object.
 */
public class DeleteFoodCommandParser implements Parser<DeleteFoodCommand> {

    @Override
    public DeleteFoodCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FOOD_NAME);

        if (Parser.arePrefixesPresent(argMultimap, PREFIX_FOOD_NAME)
                && argMultimap.getPreamble().isEmpty()) {

            FoodName foodName = RecmFoodParserUtil.parseFoodName(argMultimap.getValue(PREFIX_FOOD_NAME).get().trim());

            return new DeleteFoodCommand(foodName);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFoodCommand.MESSAGE_USAGE));
        }


    }
}
