package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_VIEW_OPTIONS;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.ViewCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.ListType;

//@@author febee99
/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser {
    private final ListType listType;

    ViewCommandParser(ListType listType) {
        this.listType = listType;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();

        switch (trimmedArgs) {
        case "":
            return new ViewCommand(this.listType);
        case XPIRE.toString():
            return new ViewCommand(XPIRE);
        case REPLENISH.toString():
            return new ViewCommand(REPLENISH);
        default:
            Set<String> allowedArgs = new HashSet<>(Arrays.asList(XPIRE.toString(), REPLENISH.toString()));
            String closeMatches = StringUtil.findSimilar(trimmedArgs, allowedArgs, 1);
            throw new ParseException(MESSAGE_VIEW_OPTIONS + closeMatches);
        }
    }
}
