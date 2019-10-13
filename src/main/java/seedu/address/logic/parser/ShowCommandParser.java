package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {
    public static final String TASK_VIEW_COMMAND = "T";
    public static final String EVENT_VIEW_COMMAND = "E";
    public static final String REMINDER_VIEW_COMMAND = "R";
    public static final String CALENDAR_VIEW_COMMAND = "C";

    /**
     * Parses the given {@code view} in the context of ShowCommand and returns a ShowCommand for execution
     * @param view of the view to show
     * @param empty an empty string
     * @return appropriate ShowCommand to execute
     * @throws ParseException if the format of view string is incorrect
     */
    public ShowCommand parse(String view, String empty) throws ParseException {
        requireNonNull(view);
        String pane = view.trim();

        if (pane.equalsIgnoreCase(TASK_VIEW_COMMAND)
                || pane.equalsIgnoreCase(EVENT_VIEW_COMMAND)
                || pane.equalsIgnoreCase(REMINDER_VIEW_COMMAND)
                || pane.equalsIgnoreCase(CALENDAR_VIEW_COMMAND)) {
            return new ShowCommand(pane);
        } else {
            throw new ParseException("The format given for show command is incorrect. "
                    + "Example usage: \"show T\"");
        }
    }
}
