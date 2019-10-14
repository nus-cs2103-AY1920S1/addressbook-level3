package tagline.testutil;

import static java.util.Objects.requireNonNull;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;

/**
 * A utility class to build CommandResult objects.
 */
public class CommandResultBuilder {
    private String feedback;
    private boolean showHelp = false;
    private boolean exit = false;
    private ViewType viewType = ViewType.NONE;

    /**
     * Builds a {@code CommandResult} object.
     */
    public CommandResult build() {
        requireNonNull(feedback);
        return new CommandResult(feedback, showHelp, exit, viewType);
    }

    /**
     * Adds a new feedback string to the {@code CommandResult} we are building.
     */
    public CommandResultBuilder putName(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public CommandResultBuilder setExit() {
        this.exit = true;
        return this;
    }

    public CommandResultBuilder setShowHelp() {
        this.showHelp = true;
        return this;
    }

    /**
     * Sets the {@code CommandResult.ViewType} of the {@code CommandResult} we are building.
     */
    public CommandResultBuilder putViewType(ViewType viewType) {
        this.viewType = viewType;
        return this;
    }
}
