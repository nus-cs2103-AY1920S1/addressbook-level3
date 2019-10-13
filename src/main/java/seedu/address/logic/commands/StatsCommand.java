package seedu.address.logic.commands;

import java.util.Date;

import seedu.address.commons.util.StatsPayload;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Utility class to pass  on parameters to the method call in mainWindow
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "generate-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": generates statistic for ";

    private final Date startingDate;

    private final Date endingDate;

    private final StatisticType statisticType;


    public StatsCommand(Date startingDate, Date endingDate, StatisticType statisticType) {
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
