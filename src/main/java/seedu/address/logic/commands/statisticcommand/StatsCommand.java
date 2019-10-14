package seedu.address.logic.commands.statisticcommand;

import java.util.Calendar;

import seedu.address.commons.util.StatsPayload;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Utility class to pass  on parameters to the method call in mainWindow
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "generate-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": generates statistic for PROFIT/COST/REVENUE"
            + " Parameters: s/{Type of stat} d1/{starting date (YYYY.MM.DD)} "
            + "d2/{ending date}\n"
            + "Example: generate-s s/REVENUE d1/2018.12.13 d2/2019.11.13";

    private final Calendar startingDate;

    private final Calendar endingDate;

    private final StatisticType statisticType;


    public StatsCommand(Calendar startingDate, Calendar endingDate, StatisticType statisticType) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.statisticType = statisticType;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        StatsPayload payload = new StatsPayload(startingDate, endingDate, statisticType);
        return new CommandResult(MESSAGE_USAGE, payload, UiChange.STATS);
    }
}
