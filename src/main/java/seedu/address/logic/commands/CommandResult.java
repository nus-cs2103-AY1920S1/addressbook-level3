package seedu.address.logic.commands;

import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.ui.UiViewManager;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * The platform that manages between Logic and UI.
     */
    private final UiViewManager uiViewManager;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.uiViewManager = null;
    }

    /**
     * Constructs an alternative CommandResult that would affect the UI. (Showing person).
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String commandWord, Person p) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.uiViewManager = new UiViewManager();
        if (commandWord.equals("show")) {
            uiViewManager.changeUiDetailsView(p);
        }
        if (commandWord.equals("export")) {
            uiViewManager.exportVisual(p);
        }
    }

    /**
     * Constructs an alternative CommandResult that would affect the UI. (Showing groups).
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String commandWord, Group g) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.uiViewManager = new UiViewManager();
        if (commandWord.equals("show")) {
            uiViewManager.changeUiDetailsView(g);
        }
        if (commandWord.equals("export")) {
            uiViewManager.exportVisual(g);
        }
    }

    /**
     * Constructs an alternative CommandResult that would affect the UI. (Showing strings).
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, String commandWord, String msg) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.uiViewManager = new UiViewManager();
        if (commandWord.equals("show")) {
            uiViewManager.changeUiDetailsView(msg);
        }
    }


    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
