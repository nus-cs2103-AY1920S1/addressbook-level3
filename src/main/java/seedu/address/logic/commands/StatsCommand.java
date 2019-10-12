package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Utility class to pass  on parameters to the method call in mainWindow
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "generate-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": generates statistic for ";

    private final String startingDate;

    private final String endingDate;

    private final StatisticType statisticType;


    public StatsCommand(String startingDate,  String endingDate, StatisticType statisticType) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.statisticType = statisticType;

    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        CommandResult statsCommand = new CommandStatsResult(MESSAGE_USAGE,
                startingDate,
                endingDate,
                statisticType);
        return statsCommand;
    }
}
