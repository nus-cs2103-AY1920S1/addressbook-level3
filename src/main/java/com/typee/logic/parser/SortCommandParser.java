package com.typee.logic.parser;

import com.typee.commons.core.Messages;
import com.typee.logic.commands.SortCommand;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Parses a sort command and returns a {@code SortCommand} object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private String trimmedArgs;
    @Override
    public SortCommand parse(String args) throws ParseException {
        trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(InteractiveParserUtil.parseComparator(parseOrder()));
    }

    /**
     * Parses user command and returns a formulated ordering method
     * @return {@code String} of formulated ordering method
     * @throws ParseException if the input format is incorrect
     */
    private String parseOrder() throws ParseException {
        try {
            switch (Order.valueOf(trimmedArgs.toUpperCase().replace(" ", "_"))) {

            case START_ASCENDING:
                return "START_TIME";

            case START_DESCENDING:
                return "START_TIME_REVERSE";

            case END_ASCENDING:
                return "END_TIME";

            case END_DESCENDING:
                return "END_TIME_REVERSE";

            case DESCRIPTION_ASCENDING:
                return "ALPHABETICAL";

            case DESCRIPTION_DESCENDING:
                return "ALPHABETICAL_REVERSE";

            case PRIORITY_ASCENDING:
                return "PRIORITY";

            case PRIORITY_DESCENDING:
                return "PRIORITY_REVERSE";

            default:
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}

/**
 * Specifies expected user input format
 */
enum Order {
    START_ASCENDING, START_DESCENDING, END_ASCENDING, END_DESCENDING, DESCRIPTION_ASCENDING, DESCRIPTION_DESCENDING,
    PRIORITY_ASCENDING, PRIORITY_DESCENDING
}
