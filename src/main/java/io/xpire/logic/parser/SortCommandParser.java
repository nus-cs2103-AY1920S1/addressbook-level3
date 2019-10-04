package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import io.xpire.logic.commands.SortCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.sort.MethodOfSorting;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        String[] nameKeywords = trimmedArgs.split("\\|");
        MethodOfSorting method = ParserUtil.parseMethodOfSorting(nameKeywords[0]);
        return new SortCommand(method);
    }
}
