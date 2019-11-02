package seedu.address.itinerary.logic.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Event;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Greet thee user by giving the current date and time. Events for the day are also filtered and shown.
 */
public class GreetCommand extends Command<Model> {
    public static final String COMMAND_WORD = "greet";

    private String message = "Hello! Welcome to the itinerary page!\nThe time now is "
            + dateGenerate() + "\nHere are the events that you have for the day!";

    public String getMessage() {
        return message;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateFilteredEventList(this::filterCurrentDate);
        return new CommandResult(message, false, false);
    }

    /**
     * Get the current time and date before formatting it.
     * @return the date and time formatted in HH:mm:ss, dd/MM/yy.
     */
    private String dateGenerate() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss, dd/MM/yy");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * Check and filter out events that match current day.
     * @param event in the itinerary list to be check.
     * @return filtered list with the current event for the day.
     */
    private boolean filterCurrentDate(Event event) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        boolean verdict = event.getDate().toString().equals(df.format(calendar.getTime()));

        return verdict;
    }
}
