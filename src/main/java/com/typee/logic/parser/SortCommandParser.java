package com.typee.logic.parser;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.SortCommand;
import com.typee.logic.parser.exceptions.ParseException;

public class SortCommandParser implements Parser<SortCommand> {

    private String trimmedArgs;
    @Override
    public SortCommand parse(String args) throws ParseException {
        trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(ParserUtil.parseComparator(parseOrder()));
    }

    private String parseOrder() throws ParseException {
        switch (Order.valueOf(trimmedArgs.toUpperCase())) {

        case STARTA:
            return "START_TIME";

        case STARTD:
            return "START_TIME_REVERSE";

        case ENDA:
            return "END_TIME";

        case ENDD:
            return "END_TIME_REVERSE";

        case NAMEA:
            return "ALPHABETICAL";

        case NAMED:
            return "ALPHABETICAL_REVERSE";

        case PRIORITYA:
            return "PRIORITY";

        case PRIORITYD:
            return "PRIORITY_REVERSE";

        default:
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}

enum Order {
    STARTA, STARTD, ENDA, ENDD, NAMEA, NAMED, PRIORITYA, PRIORITYD
}
