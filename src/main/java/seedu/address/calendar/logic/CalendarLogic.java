package seedu.address.calendar.logic;

import seedu.address.calendar.logic.commands.AlternativeCommand;
import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.ReadOnlyCalendar;
import seedu.address.calendar.model.date.ViewOnlyMonth;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.util.CalendarStatistics;
import seedu.address.calendar.logic.parser.AlternativeCalendarParser;
import seedu.address.calendar.logic.parser.CalendarParser;
import seedu.address.calendar.logic.parser.Option;
import seedu.address.calendar.storage.CalendarStorage;
import seedu.address.calendar.storage.JsonCalendarStorage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

public class CalendarLogic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private CalendarStorage calendarStorage;
    private Calendar calendar;
    private SuggestionManager suggestionManager;

    public CalendarLogic() {
        this.calendarStorage = new JsonCalendarStorage(Paths.get("data" , "calendar.json"));;
        this.calendar = new Calendar();
        this.suggestionManager = new SuggestionManager();

        try {
            Optional<ReadOnlyCalendar> calendarOptional = calendarStorage.readCalendar();
            calendar.updateCalendar(calendarOptional);
        } catch (DataConversionException e) {
            logger.info("Data file not in the correct format. Will be starting with an empty Calendar");
        } catch (NoSuchFileException e) {
            logger.info("Data file cannot be found. Will be creating and starting with an empty Calendar");
        } catch (IOException e) {
            logger.info("Problem while reading from the file. Will be starting with an empty Calendar");
        }
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
        } catch (ClashException | NoSuchElementException e) {
            Command<Calendar> suggestedCommand = new AlternativeCalendarParser().parseCommand(commandText);
            suggestionManager.add(suggestedCommand);
            throw new CommandException(e.getMessage());
        }
    }

    private CommandResult executeAlternativeCommand(String commandText) throws CommandException, IOException{
        Option option = new AlternativeCalendarParser().parseOptionCommand(commandText);
        AlternativeCommand command = suggestionManager.getCommand();
        CommandResult commandResult = command.execute(calendar, option);
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

    /**
     * Gets statistics of calendar. In particular, the no. of days of vacations (school breaks and holidays),
     * no. of days of trips, no. of trips and the percentage of vacation that is spent on trips.
     *
     * @return Statistics of {@code this} calendar
     */
    public CalendarStatistics getStatistics() {
        return calendar.getStatistics();
    }
}
