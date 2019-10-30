package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.LineChartPanel;

//@@author dalisc

/**
 * Filters the line chart based on the date that the user specifies.
 * The filter can be the week, month, or year containing the date. If unspecified, it will show the last 10 days by
 * default.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the line chart by week, month, or year "
            + "depending on a specified date.\n"
            + "Parameters: KEYWORD [TIME_FRAME] DATE...\n"
            + "Example: " + COMMAND_WORD + " /month 1/10/2019";

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, d MMMM yyyy");
    private final SimpleDateFormat dateFormatterMonth = new SimpleDateFormat("MMMM 'of' yyyy");
    private final SimpleDateFormat dateFormatterYear = new SimpleDateFormat("yyyy");

    private final String timeFrame;
    private final Date date;

    public StatsCommand(Date date, String timeFrame) {
        this.timeFrame = timeFrame;
        this.date = date;
    }

    public StatsCommand() {
        timeFrame = "default";
        date = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (timeFrame.equals("default")) {
                LineChartPanel.changeFilterParameters(timeFrame, new Date());
                LineChartPanel.reinitialiseChart();
                return new CommandResult(Messages.MESSAGE_STATS_DEFAULT + "\n\n" + MESSAGE_USAGE);
            } else if (timeFrame.equals("week")) {
                LineChartPanel.changeFilterParameters(timeFrame, date);
                LineChartPanel.reinitialiseChart();
                return new CommandResult(
                        String.format(Messages.MESSAGE_STATS_WEEK, dateFormatter.format(date)));
            } else if (timeFrame.equals("month")) {
                LineChartPanel.changeFilterParameters(timeFrame, date);
                LineChartPanel.reinitialiseChart();
                return new CommandResult(
                        String.format(Messages.MESSAGE_STATS_MONTH, dateFormatterMonth.format(date)));
            } else if (timeFrame.equals("year")) {
                LineChartPanel.changeFilterParameters(timeFrame, date);
                LineChartPanel.reinitialiseChart();
                return new CommandResult(
                        String.format(Messages.MESSAGE_STATS_YEAR, dateFormatterYear.format(date)));
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        } catch (CommandException | ParseException e) {
            return new CommandResult(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatsCommand // instanceof handles nulls
                && date.equals(((StatsCommand) other).date)
                && timeFrame.equals(((StatsCommand) other).timeFrame)); // state check
    }
}
