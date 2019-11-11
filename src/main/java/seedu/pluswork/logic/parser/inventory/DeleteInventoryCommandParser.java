package seedu.pluswork.logic.parser.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_INDEX;

import java.util.stream.Stream;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.inventory.DeleteInventoryCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteInventoryCommand object
 */
public class DeleteInventoryCommandParser implements Parser<DeleteInventoryCommand> {
    public static final String MESSAGE_NO_ID = "Please enter the inventory ID of the inventory you want to delete.";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteInventoryCommand
     * and returns a DeleteInventoryCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteInventoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INVENTORY_INDEX);

        Index invIndex;
        if (!arePrefixesPresent(argMultimap, PREFIX_INVENTORY_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInventoryCommand.MESSAGE_USAGE));
        }

        try {
            invIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INVENTORY_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInventoryCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteInventoryCommand(invIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
