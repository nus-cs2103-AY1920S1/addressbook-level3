package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.VisitReport;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private int index;

    private int reportIdx;

    private String date;

    private ObservableList<VisitReport> reports;

    private VisitReport oldReport;

    private Person profilePerson;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should addVisit. */
    private final boolean addVisit;

    /** The application should showVisitList. */
    private final boolean showVisitList;

    /** The application should editVisit. */
    private final boolean editVisit;

    /** The application should show the Profile */
    private final boolean profile;

    /** The application should exit. */
    private final boolean exit;

    /** Display reminders and follow-up motd */
    private final boolean showMotd;

    /** Display list of existing user-defined aliases*/
    private final boolean showAliasList;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean addVisit, boolean showVisitList,
                         boolean editVisit, boolean profile, boolean exit, boolean showMotd, boolean showAliasList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.addVisit = addVisit;
        this.showVisitList = showVisitList;
        this.editVisit = editVisit;
        this.profile = profile;
        this.exit = exit;
        this.showMotd = showMotd;
        this.showAliasList = showAliasList;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean addVisit, boolean showVisitList,
                         boolean editVisit, boolean profile, boolean exit, boolean showMotd) {
        this(feedbackToUser, showHelp, addVisit, showVisitList, editVisit, profile, exit, showMotd, false);
    }

    public CommandResult(String feedbackToUser, int idx) {
        this(feedbackToUser, false, false, false, false, false, false, false);
        this.index = idx;
    }

    public CommandResult(String feedbackToUser, Person profilePerson, ObservableList<VisitReport> profileReportList) {
        this(feedbackToUser, false, false, false, false, true, false, false);
        this.profilePerson = profilePerson;
        this.reports = profileReportList;
    }

    public CommandResult(String feedbackToUser, int idx, String date) {
        this(feedbackToUser, false, true, false, false,
                false, false, false);
        this.index = idx;
        this.date = date;
    }

    public CommandResult(String feedbackToUser, ObservableList<VisitReport> lst) {
        this(feedbackToUser, false, false, true, false,
                false, false, false);
        this.reports = lst;
    }

    public CommandResult(String feedbackToUser, ObservableList<VisitReport> lst,
                         int idx, int reportIdx, VisitReport report) {
        this(feedbackToUser, false, false, false, true,
                false, false, false);
        this.reports = lst;
        this.index = idx;
        this.reportIdx = reportIdx;
        this.oldReport = report;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false, false, false);
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

    public int getReportIdx() {
        return reportIdx;
    }

    public ObservableList<VisitReport> getObservableVisitList() {
        return this.reports;
    }

    public VisitReport getOldReport() {
        return this.oldReport;
    }
    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isShowMotd() {
        return showMotd;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isAddVisit() {
        return addVisit;
    }

    public boolean isShowVisitList() {
        return showVisitList;
    }

    public boolean isEditVisit() {
        return editVisit;
    }

    public boolean isShowProfile() {
        return profile;
    }

    public boolean isShowAliasList() {
        return showAliasList;
    }

    public Person getProfilePerson() {
        return profilePerson;
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
                && editVisit == otherCommandResult.editVisit
                && showVisitList == otherCommandResult.showVisitList
                && profile == otherCommandResult.profile
                && exit == otherCommandResult.exit
                && showMotd == otherCommandResult.showMotd
                && showAliasList == otherCommandResult.showAliasList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, addVisit, profile, exit);
    }



}
