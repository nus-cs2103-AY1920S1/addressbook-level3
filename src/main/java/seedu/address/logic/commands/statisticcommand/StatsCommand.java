package seedu.address.logic.commands.statisticcommand;

import java.util.Calendar;
import java.util.Optional;

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
            + "Example (Non-Default mode) : generate-s s/REVENUE d1/2018.12.13 d2/2019.11.13\n"
            + "Example (Default mode) : generate-s s/DEFAULT_REVENUE";


    public static final String NON_DEFAULT_MESSAGE_USAGE =
            "A set of starting and "
            + "ending dates have to be present\n"
            + "Example: d1/2017.04.12 d2/2018.06.23";

    private final Optional<Calendar> startingDate;

    private final Optional<Calendar> endingDate;

    private final StatisticType statisticType;

    /**
     * Constructor to return StatsCommand object
     */
    public StatsCommand(Optional<Calendar> startingDate, Optional<Calendar> endingDate, StatisticType statisticType) {
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.statisticType = statisticType;
    }

    /**
     * Constructor to return statsCommand Object without Starting and ending date
     */
    public StatsCommand(StatisticType statisticType) {
        this.startingDate = Optional.empty();
        this.endingDate = Optional.empty();
        this.statisticType = statisticType;
    }

    public boolean hasDate() {
        return this.startingDate.isPresent() && this.endingDate.isPresent();
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (this.hasDate()) {
            StatsPayload payload = new StatsPayload(startingDate.get(), endingDate.get(), statisticType);
            return new CommandResult(MESSAGE_USAGE, payload, UiChange.STATS);
        } else {
            StatsPayload payload = new StatsPayload(statisticType);
            return new CommandResult(MESSAGE_USAGE, payload, UiChange.STATS);
        }
    }
}
