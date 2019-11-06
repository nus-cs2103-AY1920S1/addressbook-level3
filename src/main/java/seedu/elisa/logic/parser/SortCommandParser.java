package seedu.elisa.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Optional;

import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.commands.SortCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;

/**
 * Parser to create a sort command with the right arguments
 */
public class SortCommandParser {
    public static final String DESC = "desc";
    public static final String DESCRIPTION = "description";
    public static final String PRI = "pri";
    public static final String PRIORITY = "priority";

    /**
     * Create a sort command base on the flag that was passed in.
     * @param args of the way in which
     * @param empty an empty string
     * @return appropriate SortCommand to execute
     * @throws ParseException if the argument string is incorrect
     */
    public SortCommand parse(String args, String empty) throws ParseException {
        requireNonNull(args);
        String flag = args.trim();

        if (flag.isEmpty()) {
            return new SortCommand(Optional.empty());
        }

        Comparator<Item> comparator;
        switch(flag.toLowerCase()) {
        case DESC:
        case DESCRIPTION:
            comparator = (item1, item2) -> item1.getItemDescription().getDescription()
                    .compareToIgnoreCase(item2.getItemDescription().getDescription());
            break;
        case PRI:
        case PRIORITY:
            comparator = (item1, item2) -> item1.getPriority().compareTo(item2.getPriority());
            break;
        default:
            throw new ParseException("The parameter given for sort command is incorrect. "
                    + "Example usage: \"sort desc\"");
        }
        return new SortCommand(Optional.of(comparator));
    }
}
