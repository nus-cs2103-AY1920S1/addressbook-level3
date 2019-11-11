package seedu.pluswork.logic.parser.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_INDEX;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_PRICE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.stream.Stream;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.inventory.EditInventoryCommand;
import seedu.pluswork.logic.parser.ArgumentMultimap;
import seedu.pluswork.logic.parser.ArgumentTokenizer;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.Prefix;
import seedu.pluswork.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditInventoryCommand object
 */
public class EditInventoryCommandParser implements Parser<EditInventoryCommand> {
    public static final String MESSAGE_NO_ID = "Please enter the inventory ID of the inventory you want to edit.";

    /**
     * Parses the given {@code String} of arguments in the context of the EditInventoryCommand
     * and returns an EditInventoryCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditInventoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INVENTORY_INDEX, PREFIX_INVENTORY_NAME, PREFIX_INVENTORY_PRICE,
                        PREFIX_TASK_INDEX, PREFIX_MEMBER_ID);

        Index index;
        if (!arePrefixesPresent(argMultimap, PREFIX_INVENTORY_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditInventoryCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INVENTORY_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditInventoryCommand.MESSAGE_USAGE), pe);
        }

        EditInventoryCommand.EditInventoryDescriptor editInventoryDescriptor =
                new EditInventoryCommand.EditInventoryDescriptor();

        if (argMultimap.getValue(PREFIX_INVENTORY_NAME).isPresent()) {
            editInventoryDescriptor
                    .setName(ParserUtil.parseInvName(argMultimap.getValue(PREFIX_INVENTORY_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_INVENTORY_PRICE).isPresent()) {
            editInventoryDescriptor
                    .setPrice(ParserUtil.parsePrice((argMultimap.getValue(PREFIX_INVENTORY_PRICE).get())));
        }
        if (argMultimap.getValue(PREFIX_TASK_INDEX).isPresent()) {
            editInventoryDescriptor
                    .setTaskId(ParserUtil.parseIndex((argMultimap.getValue(PREFIX_TASK_INDEX).get())));
        }
        if (argMultimap.getValue(PREFIX_MEMBER_ID).isPresent()) {
            editInventoryDescriptor
                    .setMemId(ParserUtil.parseMemberId((argMultimap.getValue(PREFIX_MEMBER_ID).get())));
        }


        if (!editInventoryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditInventoryCommand.MESSAGE_NOT_EDITED);
        }

        return new EditInventoryCommand(index, editInventoryDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
