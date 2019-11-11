package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.ui.ListResourceType;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private boolean showHelp;

    /**
     * The application should exit.
     */
    private boolean exit;

    /**
     * Show the resolve window to user due to scheduling conflict
     */
    private boolean showResolve;

    /**
     * Show the custom properties window
     */
    private boolean showCustomProperties;

    /**
     * The type of resource to be shown in the GUI.
     */
    private ListResourceType showListResourceType;

    /**
     * The index of the resource to be selected
     */
    private Optional<Index> index;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp,
                         boolean isExit, boolean showResolve, boolean showCustomProperties,
                         ListResourceType listResourceType, Optional<Index> index) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = isExit;
        this.showResolve = showResolve;
        this.showCustomProperties = showCustomProperties;
        this.showListResourceType = listResourceType;
        this.index = index;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean showResolve,
                         boolean showCustomProperties) {
        this(feedbackToUser, showHelp, exit, showResolve, showCustomProperties, ListResourceType.NULL,
            Optional.empty());
    }


    public CommandResult(String feedbackToUser, ListResourceType listResourceType) {
        this(feedbackToUser);
        this.showListResourceType = listResourceType;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code index} and other
     * fields set to their default value.
     */
    public CommandResult(String feedbackToUser, ListResourceType listResourceType, Optional<Index> index) {
        this(feedbackToUser);
        this.showListResourceType = listResourceType;
        this.index = index;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false,
            ListResourceType.NULL, Optional.empty());
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

    public boolean isShowResolve() {
        return showResolve;
    }

    public boolean isShowCustomProperties() {
        return showCustomProperties;
    }

    public boolean isSelectResource() {
        return index.isPresent();
    }

    public ListResourceType getShowListResourceType() {
        return showListResourceType;
    }

    public Optional<Index> getSelectedIndex() {
        return index;
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
            && exit == otherCommandResult.exit
            && showResolve == otherCommandResult.showResolve
            && showCustomProperties == otherCommandResult.showCustomProperties
            && showListResourceType.equals(otherCommandResult.showListResourceType)
            && index.equals(otherCommandResult.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showResolve,
            showCustomProperties, showListResourceType, index);
    }

}
