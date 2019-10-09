package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ShowCommandParser implements Parser<ShowCommand> {
    public static final String TASK_VIEW_COMMAND = "T";
    public static final String EVENT_VIEW_COMMAND = "E";
    public static final String REMINDER_VIEW_COMMAND = "R";
    public static final String CALENDAR_VIEW_COMMAND = "C";

    public ShowCommand parse(String view, String empty) throws ParseException {
        requireNonNull(view);
        String pane = view.trim();

        if (pane.equalsIgnoreCase(TASK_VIEW_COMMAND)
                || pane.equalsIgnoreCase(EVENT_VIEW_COMMAND)
                || pane.equalsIgnoreCase(REMINDER_VIEW_COMMAND)
                || pane.equalsIgnoreCase(CALENDAR_VIEW_COMMAND)) {
            return new ShowCommand(pane);
        } else {
            throw new ParseException("The format given for show command is incorrect. " +
                    "Example usage: \"show T\"");
        }
    }
}