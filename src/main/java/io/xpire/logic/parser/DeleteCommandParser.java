package io.xpire.logic.parser;

import java.util.Set;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.DeleteCommand;
import io.xpire.logic.parser.exceptions.ParseException;

import io.xpire.model.item.Quantity;
import io.xpire.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String[] splitArgs = args.split("\\|", 2);
        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
        if (containsTag(splitArgs)) {
            return deleteTagsCommand(index, splitArgs[1]);
        } else if (containsQuantity(splitArgs)) {
            return deleteQuantityCommand(index, splitArgs[1]);
        } else {
            return deleteItemCommand(index);
        }
    }

    private boolean containsTag (String[] args) {
        return args.length > 1 && args[1].contains("#");
    }

    private boolean containsQuantity(String[] args) {
        return (args.length == 2) ?
                    isIntegerType(args[1]) : false;
    }

    private boolean isIntegerType(String arg) {
        try {
            Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private DeleteCommand deleteItemCommand(Index index) {
        return new DeleteCommand(index);
    }

    private DeleteCommand deleteTagsCommand(Index index, String arg) throws ParseException {
        Set<Tag> set = ParserUtil.parseTagsFromInput(arg);
        return new DeleteCommand(index, set);
    }

    private DeleteCommand deleteQuantityCommand(Index index, String arg) throws ParseException {
        Quantity newQuantity = ParserUtil.parseQuantity(arg);
        return new DeleteCommand(index, newQuantity);
    }

}
