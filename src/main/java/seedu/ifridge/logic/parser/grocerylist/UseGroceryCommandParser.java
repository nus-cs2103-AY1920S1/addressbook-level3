package seedu.ifridge.logic.parser.grocerylist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.grocerylist.UseGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.UseGroceryCommand.UseGroceryItemDescriptor;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Parser;
import seedu.ifridge.logic.parser.ParserUtil;
import seedu.ifridge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class UseGroceryCommandParser implements Parser<UseGroceryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UseGroceryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UseGroceryCommand.MESSAGE_USAGE), pe);
        }

        UseGroceryItemDescriptor useGroceryItemDescriptor = new UseGroceryItemDescriptor();
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            useGroceryItemDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        if (!useGroceryItemDescriptor.isAmountUsed()) {
            throw new ParseException(UseGroceryCommand.MESSAGE_NOT_USED);
        }

        return new UseGroceryCommand(index, useGroceryItemDescriptor);
    }
}
