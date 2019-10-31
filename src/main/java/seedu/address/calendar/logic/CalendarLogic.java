package seedu.address.calendar.logic;

import seedu.address.calendar.commands.AlternativeCommand;
import seedu.address.calendar.commands.AlternativeCommandUtil;
import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.date.ViewOnlyMonth;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.parser.AlternativeCalendarParser;
import seedu.address.calendar.parser.CalendarParser;
import seedu.address.calendar.parser.Option;
import seedu.address.calendar.storage.CalendarStorage;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.IOException;
import java.util.List;

public class CalendarLogic {
    private CalendarStorage calendarStorage;
    private Calendar calendar;
    private SuggestionManager suggestionManager;

    public CalendarLogic(Calendar calendar, CalendarStorage calendarStorage) {
        this.calendarStorage = calendarStorage;
        this.calendar = calendar;
        this.suggestionManager = new SuggestionManager();
    }

    public CommandResult executeCommand(String commandText) throws CommandException, ParseException, IOException {
        if (suggestionManager.hasSuggestedCommand() && AlternativeCalendarParser.isAlternativeCommand(commandText)) {
            return executeAlternativeCommand(commandText);
        }

        suggestionManager.forgetSuggestion();

        try {
            Command<Calendar> command = new CalendarParser().parseCommand(commandText);
            CommandResult commandResult = command.execute(calendar);
            calendarStorage.saveCalendar(calendar.getCalendar());
            return commandResult;
        } catch (ClashException e) {
            Command<Calendar> suggestedCommand = new AlternativeCalendarParser().parseCommand(commandText);
            suggestionManager.add(suggestedCommand);
            throw new CommandException(e.getMessage());
        }
    }

    private CommandResult executeAlternativeCommand(String commandText) throws CommandException, IOException{
        Option option = new AlternativeCalendarParser().parseOptionCommand(commandText);
        List<AlternativeCommand> commands = suggestionManager.getCommands();
        CommandResult commandResult = AlternativeCommandUtil.execute(calendar, option, commands);
        calendarStorage.saveCalendar(calendar.getCalendar());
        return commandResult;
    }

    public boolean hasVisibleUpdates() {
        return calendar.hasVisibleUpdates();
    }

    public ViewOnlyMonth getVisibleMonth() {
        return calendar.getMonth();
    }

    public void completeVisibleUpdates() {
        calendar.completeVisibleUpdates();
    }

}
