package seedu.address.logic.commands.wastelist;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.waste.WasteMonth;
import seedu.address.model.waste.WasteReport;
import seedu.address.model.waste.WasteStatistic;

/**
 * Generates a report of user's food waste for a specified time frame.
 */
public class ReportWasteCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Provides you with a report of your past food waste.\n"
            + "Example: wlist " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "placeholder";
    // In v1.3, will incorporate start and end date.

    private WasteMonth startWasteMonth;
    private WasteMonth endWasteMonth;

    public ReportWasteCommand(WasteMonth startWm, WasteMonth endWm) {
        requireNonNull(startWm);
        requireNonNull(endWm);
        this.startWasteMonth = startWm;
        this.endWasteMonth = endWm;
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
        WasteMonth earliestExistingWasteMonth = model.getEarliestWasteMonth();
        WasteMonth latestExistingWasteMonth = model.getLatestWasteMonth();
        requireNonNull(earliestExistingWasteMonth);
        requireNonNull(latestExistingWasteMonth);

        WasteMonth actualStartMonth = WasteMonth.later(this.startWasteMonth, earliestExistingWasteMonth);
        WasteMonth actualEndMonth = WasteMonth
                .earlier(this.endWasteMonth, latestExistingWasteMonth).addWasteMonth(1);

        WasteMonth wasteMonth = actualStartMonth;
        Map<WasteMonth, WasteStatistic> historicalData = new HashMap<>();
        while (wasteMonth.isBefore(actualEndMonth)) {
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


        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setWasteReportCommand();

        return commandResult;
    }

}
