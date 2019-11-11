package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDICATOR;

import seedu.address.model.Model;
import seedu.address.model.visual.DisplayFormat;
import seedu.address.model.visual.DisplayIndicator;

/**
 * Displays indicator according to specified format.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": Displays indicator according to specified format.\n"
            + "Parameters: "
            + PREFIX_INDICATOR + "INDICATOR "
            + PREFIX_FORMAT + "FORMAT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDICATOR + DisplayIndicator.POLICY_POPULARITY_BREAKDOWN + " "
            + PREFIX_FORMAT + DisplayFormat.PIECHART + " \n"
            + DisplayIndicator.getMessageConstraints() + " \n"
            + DisplayFormat.getMessageConstraints();

    private final DisplayIndicator displayIndicator;

    private final DisplayFormat displayFormat;

    /**
     * Creates a DisplayCommand.
     */
    public DisplayCommand(DisplayIndicator displayIndicator, DisplayFormat displayFormat) {
        requireNonNull(displayIndicator);
        requireNonNull(displayFormat);
        this.displayIndicator = displayIndicator;
        this.displayFormat = displayFormat;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(getMessageSuccess(this.displayIndicator, this.displayFormat),
            this.displayIndicator, this.displayFormat);
    }

    public static String getMessageSuccess(DisplayIndicator displayIndicator, DisplayFormat displayFormat) {
        requireNonNull(displayIndicator);
        requireNonNull(displayFormat);
        String messageSuccess =
            "Displayed " + displayIndicator
                + " with " + displayFormat + " successfully.";
        return messageSuccess;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DisplayCommand // instanceof handles nulls
            && displayIndicator.equals(((DisplayCommand) other).displayIndicator)
            && displayFormat.equals(((DisplayCommand) other).displayFormat));
    }
}
