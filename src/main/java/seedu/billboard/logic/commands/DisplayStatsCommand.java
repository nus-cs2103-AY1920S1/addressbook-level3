package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.billboard.logic.parser.Prefix;
import seedu.billboard.model.Model;
import seedu.billboard.model.statistics.formats.StatisticsFormat;
import seedu.billboard.model.statistics.formats.StatisticsFormatOptions;

/**
 * Changes the displayed statistic of the billboard.
 */
public class DisplayStatsCommand extends Command {

    public static final String COMMAND_WORD = "display-stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the type of statistic being displayed, with the specified options.\n"
            + "Parameters: FORMAT [interval/DATE-INTERVAL]\n"
            + "Supported formats:\n"
            + Arrays.stream(StatisticsFormat.values())
            .map(format -> format.getName()
                    + ": (optional prefixes: " + getPrefixesString(format.getOptionsPrefixes()) + ")")
            .collect(Collectors.joining("\n", "", "\n"))
            + "Example: " + COMMAND_WORD + " timeline interval/month";

    public static final String MESSAGE_DISPLAYED_STATS_CHANGED_SUCCESS = "Displayed statistics changed to %s!";

    private final StatisticsFormat newFormat;
    private final StatisticsFormatOptions newOptions;

    /**
     * @param newFormat  New statistics format to be displayed.
     * @param newOptions New options to be applied to the statistics display.
     */
    public DisplayStatsCommand(StatisticsFormat newFormat, StatisticsFormatOptions newOptions) {
        this.newFormat = newFormat;
        this.newOptions = newOptions;
    }

    private static String getPrefixesString(Prefix[] prefixes) {
        return Arrays.stream(prefixes)
                .map(Prefix::getPrefix)
                .collect(Collectors.joining(" | ", "[", "]"));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setStatisticsFormat(newFormat);
        model.setStatisticsFormatOptions(newOptions);

        return new CommandResult(MESSAGE_DISPLAYED_STATS_CHANGED_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof DisplayStatsCommand) {
            DisplayStatsCommand other = (DisplayStatsCommand) obj;
            return other.newFormat == this.newFormat
                    && other.newOptions.equals(this.newOptions);
        }
        return false;
    }
}
