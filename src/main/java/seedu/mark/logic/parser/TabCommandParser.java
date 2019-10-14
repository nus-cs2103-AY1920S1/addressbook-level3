package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.mark.logic.commands.TabCommand;
import seedu.mark.logic.commands.TabCommand.Tab;
import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TabCommand object.
 */
public class TabCommandParser implements Parser<TabCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TabCommand
     * and returns a TabCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TabCommand parse(String args) throws ParseException {
        requireNonNull(args);

        return new TabCommand(parseTab(args));
    }


    /**
     * Parses a {@code String args} into a {@code Tab}.
     * {@code args} is valid if it is either 1, 2 or 3, or the keywords of tab.
     * @param args A valid argument of Tab
     * @return The corresponding tab
     * @throws ParseException if the given {@code arg} is invalid.
     */
    public static Tab parseTab(String args) throws ParseException {

        TabCommand.Tab tab;
        try {
            tab = parseTabIndex(args);
        } catch (ParseException pe_index) {

            try {
                tab = parseTabKeyword(args);
            } catch (ParseException pe_kw) {
                throw pe_index;
            }

        }

        return tab;
    }

    /**
     * Parses a {@code String arg} into a {@code Tab}.
     * Parsing will be successful only if {@code arg} is either "1", "2" or "3".
     *
     * @param arg The argument of a tab command
     * @return The corresponding tab
     * @throws ParseException if the given {@code arg} is invalid.
     */
    public static Tab parseTabIndex(String arg) throws ParseException {
        int index;
        try {
            index = Integer.parseInt(arg.strip());
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
        }

        return convertIndexToTabType(index);
    }

    /**
     * Converts an {@code Index} into a {@code Tab}.
     * @param index A one-based index
     * @return The corresponding tab
     * @throws ParseException if index does not represent 1, 2 or 3.
     */
    private static Tab convertIndexToTabType(int index) throws ParseException {

        Tab type = null;
        switch (index) {
        case 1:
            type = Tab.DASHBOARD;
            break;
        case 2:
            type = Tab.ONLINE;
            break;
        case 3:
            type = Tab.OFFLINE;
            break;
        default:
            throw new ParseException(TabCommand.MESSAGE_INVALID_INDEX);
        }

        return type;
    }

    /**
     * Parses a {@code String arg} into a {@code Tab}.
     * Parsing will be successful only if {@code arg} is either "dash", "on" or "off".
     *
     * @param arg The argument of a tab command
     * @return The corresponding tab
     * @throws ParseException if the given {@code arg} is invalid.
     */
    public static Tab parseTabKeyword(String arg) throws ParseException {
        Tab type = null;

        switch (arg.toLowerCase().strip()) {
        case "dash":
            type = Tab.DASHBOARD;
            break;
        case "on":
            type = Tab.ONLINE;
            break;
        case "off":
            type = Tab.OFFLINE;
            break;
        default:
            throw new ParseException(TabCommand.MESSAGE_INVALID_KEYWORD);
        }

        return type;
    }


}
