package seedu.ifridge.logic.parser.grocerylist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;

import java.util.stream.Stream;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.grocerylist.UseGroceryCommand;
import seedu.ifridge.logic.commands.grocerylist.UseGroceryCommand.UseGroceryItemDescriptor;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Parser;
import seedu.ifridge.logic.parser.ParserUtil;
import seedu.ifridge.logic.parser.Prefix;
import seedu.ifridge.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UseGroceryCommand object
 */
public class UseGroceryCommandParser implements Parser<UseGroceryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UseGroceryCommand
     * and returns an UseGroceryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UseGroceryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);

        Index index;

        // error if no prefix
        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UseGroceryCommand.MESSAGE_USAGE));
        }

        // error if invalid index
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

        // if amount used is empty (less than minimum supported)
        if (!useGroceryItemDescriptor.isAmountUsed()) {
            throw new ParseException(UseGroceryCommand.MESSAGE_NOT_USED);
        }

        return new UseGroceryCommand(index, useGroceryItemDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
