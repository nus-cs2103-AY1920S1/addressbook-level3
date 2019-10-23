package com.typee.logic.parser;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.SortCommand;
import com.typee.logic.parser.exceptions.ParseException;

public class SortCommandParser implements Parser<SortCommand> {
    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(ParserUtil.parseComparator(trimmedArgs));
    }
}
