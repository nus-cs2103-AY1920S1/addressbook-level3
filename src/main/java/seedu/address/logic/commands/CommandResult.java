package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.model.person.VisitReport;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private int index;

    private String date;

    private ObservableList<VisitReport> reports;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should addVisit. */
    private final boolean addVisit;

    /** The application should addVisit. */
    private final boolean deleteVisit;

    /** The application should exit. */
    private final boolean exit;



    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean addVisit, boolean deleteVisit, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.addVisit = addVisit;
        this.deleteVisit = deleteVisit;
        this.exit = exit;

    }

    public CommandResult(String feedbackToUser, int idx, String date) {
        this(feedbackToUser, false, true, false, false);
        this.index = idx;
        this.date = date;
    }

    public CommandResult(String feedbackToUser, ObservableList<VisitReport> lst) {
        this(feedbackToUser, false, false, true, false);
        this.reports = lst;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getDate() {
        return date;
    }

    public int getIdx() {
        return index;
    }

    public ObservableList<VisitReport> getObservableVisitList() {
        return this.reports;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isAddVisit() {
        return addVisit;
    }

    public boolean isDeleteVisit() {
        return deleteVisit;
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
                && addVisit == otherCommandResult.addVisit
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, addVisit, exit);
    }



}
