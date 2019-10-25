package seedu.ifridge.logic.commands.wastelist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_START_MONTH;

import java.util.HashMap;
import java.util.Map;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteReport;
import seedu.ifridge.model.waste.WasteStatistic;

/**
 * Generates a report of user's food waste for a specified time frame.
 */
public class ReportWasteCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Provides you with a report of your past food waste.\n"
            + "Parameters: "
            + "[" + PREFIX_START_MONTH + "STARTING MONTH] "
            + "[" + PREFIX_END_MONTH + "ENDING MONTH]\n"
            + "Example: wlist " + COMMAND_WORD + " "
            + PREFIX_START_MONTH + "May 2019 "
            + PREFIX_END_MONTH + "Oct 2019";

    public static final String MESSAGE_SUCCESS = "Generated your food waste report from %1$s to %2$s.";

    public static final String MESSAGE_INVALID_START_END_ORDER = "The start month must be "
            + "before or equal to the end month.";

    public static final String MESSAGE_DIFFERENT_START_MONTH = "The earliest record found in your waste archive "
            + "is %1$s, which will be used as the starting month instead of %2$s.\n";

    public static final String MESSAGE_DIFFERENT_END_MONTH = "You have specified an end month later than this "
            + "current month. Our report will only show your waste statistics up to the current month.\n";

    public static final String MESSAGE_NO_EXISTING_RECORDS = "We have no records of your food waste from "
            + "%1$s to %2$s. The earliest record we can find is dated on %3$s.";

    public static final String MESSAGE_START_MONTH_AFTER_CURRENT_MONTH = "Please ensure the start month is before or "
            + "equal to the current month of %1$s.";


    private WasteMonth startWasteMonth;
    private WasteMonth endWasteMonth;
    private boolean startMonthGivenByUser;
    private boolean endMonthGivenByUser;

    public ReportWasteCommand(WasteMonth startWm, WasteMonth endWm,
                              boolean startMonthGivenByUser, boolean endMonthGivenByUser) {
        requireNonNull(startWm);
        requireNonNull(endWm);
        requireNonNull(startMonthGivenByUser);
        requireNonNull(endMonthGivenByUser);
        this.startWasteMonth = startWm;
        this.endWasteMonth = endWm;
        this.startMonthGivenByUser = startMonthGivenByUser;
        this.endMonthGivenByUser = endMonthGivenByUser;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        if (this.startWasteMonth.isAfter(this.endWasteMonth)) {
            throw new CommandException(MESSAGE_INVALID_START_END_ORDER);
        }
        WasteMonth earliestExistingWasteMonth = model.getEarliestWasteMonth();
        WasteMonth latestExistingWasteMonth = model.getLatestWasteMonth();
        requireNonNull(earliestExistingWasteMonth);
        requireNonNull(latestExistingWasteMonth);

        boolean endingMonthBeforeEarliestRecord = this.endWasteMonth.isBefore(earliestExistingWasteMonth);
        if (endingMonthBeforeEarliestRecord) {
            throw new CommandException(String.format(MESSAGE_NO_EXISTING_RECORDS,
                    this.startWasteMonth, this.endWasteMonth, earliestExistingWasteMonth));
        }
        boolean startingMonthAfterLatestRecord = this.startWasteMonth.isAfter(latestExistingWasteMonth);
        if (startingMonthAfterLatestRecord) {
            throw new CommandException(String.format(MESSAGE_START_MONTH_AFTER_CURRENT_MONTH,
                    latestExistingWasteMonth));
        }

        WasteMonth actualStartMonth = WasteMonth.later(this.startWasteMonth, earliestExistingWasteMonth);
        WasteMonth actualEndMonth = WasteMonth.earlier(this.endWasteMonth, latestExistingWasteMonth);

        WasteMonth endingMonth = actualEndMonth.addWasteMonth(1);
        WasteMonth wasteMonth = actualStartMonth;
        Map<WasteMonth, WasteStatistic> historicalData = new HashMap<>();
        while (wasteMonth.isBefore(endingMonth)) {
            WasteStatistic statistic;
            if (model.hasWasteMonth(wasteMonth)) {
                statistic = model.getWasteListByMonth(wasteMonth).getWasteStatistic();
            } else {
                statistic = new WasteStatistic(0, 0, 0);
            }
            historicalData.put(wasteMonth, statistic);
            wasteMonth = wasteMonth.nextWasteMonth();
        }
        WasteReport wasteReport = new WasteReport(historicalData);
        model.setWasteReport(wasteReport);

        boolean startMonthDifferentFromUserSpecified =
                !actualStartMonth.equals(this.startWasteMonth) && startMonthGivenByUser;
        boolean endMonthDifferentFromUserSpecified =
                !actualEndMonth.equals(this.endWasteMonth) && endMonthGivenByUser;
        String differentStartMonth = startMonthDifferentFromUserSpecified
                ? String.format(MESSAGE_DIFFERENT_START_MONTH, actualStartMonth, this.startWasteMonth)
                : "";
        String differentEndMonth = endMonthDifferentFromUserSpecified
                ? MESSAGE_DIFFERENT_END_MONTH
                : "";

        CommandResult commandResult = new CommandResult(
                differentStartMonth + differentEndMonth
                + String.format(MESSAGE_SUCCESS, actualStartMonth, actualEndMonth));
        commandResult.setWasteReportCommand();

        return commandResult;
    }

}
