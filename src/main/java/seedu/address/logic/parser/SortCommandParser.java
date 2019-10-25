package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Optional;

import seedu.address.commons.core.item.Item;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser to create a sort command with the right arguments
 */
public class SortCommandParser {
    public static final String DESCRIPTION = "desc";
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
        case DESCRIPTION:
            comparator = (item1, item2) -> item1.getItemDescription().getDescription()
                    .compareTo(item2.getItemDescription().getDescription());
            break;
        case PRIORITY:
            // waiting for priority to be moved out to the item level
        default:
            throw new ParseException("The flag given for sort command is incorrect. "
                    + "Example usage: \"sort desc\"");
        }
        return new SortCommand(Optional.of(comparator));
    }
}
