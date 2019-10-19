package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDICATOR;

import seedu.address.model.Model;
import seedu.address.model.visual.DisplayIndicator;

/**
 * Displays indicator according to specified format.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": Displays indicator according to specified format.\n"
        + "Parameters: "
        + PREFIX_INDICATOR + "INDICATOR\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_INDICATOR + "contact-list-growth-rate\n"
        + DisplayIndicator.MESSAGE_CONSTRAINTS;

    // TODO: Change text to state which displayIndicator is displayed
    public static final String MESSAGE_SUCCESS = "Displayed";

    private final DisplayIndicator displayIndicator;

    /**
     * Creates a DisplayCommand.
     */
    public DisplayCommand(DisplayIndicator displayIndicator) {
        requireNonNull(displayIndicator);
        this.displayIndicator = displayIndicator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, this.displayIndicator);
    }
}
