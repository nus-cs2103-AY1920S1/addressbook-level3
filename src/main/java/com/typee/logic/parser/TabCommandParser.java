package com.typee.logic.parser;

import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.commons.core.Messages;
import com.typee.logic.commands.TabCommand;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.ui.AppointmentWindow;
import com.typee.ui.GameWindow;
import com.typee.ui.Tab;
import com.typee.ui.calendar.CalendarWindow;
import com.typee.ui.ReportWindow;

/**
 * Parses the given {@code String} of arguments in the context of the TabCommand
 * and returns an Tab Command object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class TabCommandParser implements Parser<TabCommand> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public TabCommand parse(String userInput) throws ParseException {
        String tabName = userInput.trim();
        if (tabName.equals("") || userInput == null) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
        } else {
            switch(tabName.toLowerCase()) {
            //Feel free to modify your tab name and respective Controller class together with your fxml file.
            case "typinggame":
                return new TabCommand(new Tab("Typing Game", GameWindow.FXML, new GameWindow()));
            case "appointment":
                return new TabCommand(new Tab("Add Engagement", AppointmentWindow.FXML, new AppointmentWindow()));
            case "report":
                return new TabCommand(new Tab("Generate Report", ReportWindow.FXML, new ReportWindow()));
            case "calendar":
                return new TabCommand(new Tab("Calendar View", CalendarWindow.FXML, new CalendarWindow()));
            default:
                throw new ParseException(
                        String.format("Invalid tab name. Please specify a valid tab menu."));
            }
        }
    }
}
