package calofit.logic.parser;

import static calofit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import calofit.commons.core.index.Index;
import calofit.logic.commands.DeleteCommand;
import calofit.logic.parser.exceptions.ParseException;

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
        try {
            args.trim();
            String[] argsArr = args.split(" ");
            Set<Integer> setOfArgs = new HashSet<Integer>();
            for (int i = 1; i < argsArr.length; i++) {
                Index index = ParserUtil.parseIndex(argsArr[i]);
                setOfArgs.add(index.getZeroBased());
            }
            return new DeleteCommand(new ArrayList<>(setOfArgs));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
