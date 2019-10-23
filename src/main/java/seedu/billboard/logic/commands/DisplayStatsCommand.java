package seedu.billboard.logic.commands;

import seedu.billboard.model.Model;
import seedu.billboard.model.statistics.formats.StatisticsFormat;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class DisplayStatsCommand extends Command {

    public static final String COMMAND_WORD = "display-stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the displayed statistic to the selected format.\n"
            + "Parameters: STATISTIC_FORMAT\n"
            + "Supported formats:\n"
            + Arrays.stream(StatisticsFormat.values())
            .map(StatisticsFormat::getName)
            .collect(Collectors.joining("\n", "", "\n"))
            + "Example: " + COMMAND_WORD + " timeline";

    public static final String MESSAGE_DISPLAYED_STATS_CHANGED_SUCCESS = "Displayed statistics changed!";

    private final StatisticsFormat format;

    public DisplayStatsCommand(StatisticsFormat format) {
        this.format = format;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setStatisticsFormat(format);
        return new CommandResult(MESSAGE_DISPLAYED_STATS_CHANGED_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayStatsCommand // instanceof handles nulls
                && format == ((DisplayStatsCommand) other).format);
    }
}
