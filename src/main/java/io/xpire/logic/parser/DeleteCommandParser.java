package io.xpire.logic.parser;

import java.util.Set;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.DeleteCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.ListType;
import io.xpire.model.item.Quantity;
import io.xpire.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    public static final String MESSAGE_DELETE_QUANTITY_INVALID_USAGE =
            "Items in the replenish list do not have quantities to delete from.\n"
            + "Please use this command in the main list instead.";
    private final ListType listType;
    public DeleteCommandParser(ListType listType) {
        this.listType = listType;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand parse(String args) throws ParseException {
        String[] splitArgs = args.split("\\|", 2);
        Index index = ParserUtil.parseIndex(splitArgs[0]);
        if (containsTag(splitArgs)) {
            return deleteTagsCommand(index, splitArgs[1]);
        } else if (containsQuantity(splitArgs)) {
            switch (listType) {
            case XPIRE:
                return deleteQuantityCommand(index, splitArgs[1]);
            case REPLENISH:
                throw new ParseException(MESSAGE_DELETE_QUANTITY_INVALID_USAGE);
            default:
                throw new ParseException("Invalid list type.");
            }
        } else if (splitArgs.length > 1) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        } else {
            return deleteItemCommand(index);
        }
    }

    private boolean containsTag (String[] args) {
        return args.length > 1 && args[1].contains("#");
    }

    private boolean containsQuantity(String[] args) {
        return (args.length == 2) && StringUtil.isNumeric(args[1]);
    }

    private DeleteCommand deleteItemCommand(Index index) {
        return new DeleteCommand(listType, index);
    }

    private DeleteCommand deleteTagsCommand(Index index, String arg) throws ParseException {
        Set<Tag> set = ParserUtil.parseTagsFromInput(arg);
        return new DeleteCommand(listType, index, set);
    }

    private DeleteCommand deleteQuantityCommand(Index index, String arg) throws ParseException {
        Quantity newQuantity = ParserUtil.parseQuantity(arg);
        return new DeleteCommand(listType, index, newQuantity);
    }

}
