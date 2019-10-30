package io.xpire.logic.parser;

import io.xpire.logic.commands.ViewCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ListToView;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ViewCommand();
        }
        ListToView list = ParserUtil.parseListToView(trimmedArgs);
        return new ViewCommand(list);
    }
}
