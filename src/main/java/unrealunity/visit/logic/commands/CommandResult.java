package unrealunity.visit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.model.person.VisitReport;

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
    private final boolean isShowHelp;

    /** The application should AddVisit. */
    private final boolean isAddVisit;

    /** The application should ShowVisitList. */
    private final boolean isShowVisitList;

    /** The application should EditVisit. */
    private final boolean isEditVisit;

    /** The application should show the Profile */
    private final boolean isProfile;

    /** The application should Exit. */
    private final boolean exit;

    /** Display reminders and follow-up motd */
    private final boolean isShowMotd;

    /** Display list of existing user-defined aliases*/
    private final boolean isShowAliasList;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isShowHelp, boolean isAddVisit, boolean isShowVisitList,
                         boolean isEditVisit, boolean isProfile, boolean exit,
                         boolean isShowMotd, boolean isShowAliasList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = isShowHelp;
        this.isAddVisit = isAddVisit;
        this.isShowVisitList = isShowVisitList;
        this.isEditVisit = isEditVisit;
        this.isProfile = isProfile;
        this.exit = exit;
        this.isShowMotd = isShowMotd;
        this.isShowAliasList = isShowAliasList;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isShowHelp, boolean isAddVisit, boolean isShowVisitList,
                         boolean isEditVisit, boolean isProfile, boolean exit, boolean isShowMotd) {
        this(feedbackToUser, isShowHelp, isAddVisit, isShowVisitList, isEditVisit,
                isProfile, exit, isShowMotd, false);
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
        return isShowHelp;
    }

    public boolean isShowMotd() {
        return isShowMotd;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isAddVisit() {
        return isAddVisit;
    }

    public boolean isShowVisitList() {
        return isShowVisitList;
    }

    public boolean isEditVisit() {
        return isEditVisit;
    }

    public boolean isShowProfile() {
        return isProfile;
    }

    public boolean isShowAliasList() {
        return isShowAliasList;
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
                && isShowHelp == otherCommandResult.isShowHelp
                && isAddVisit == otherCommandResult.isAddVisit
                && isEditVisit == otherCommandResult.isEditVisit
                && isShowVisitList == otherCommandResult.isShowVisitList
                && isProfile == otherCommandResult.isProfile
                && exit == otherCommandResult.exit
                && isShowMotd == otherCommandResult.isShowMotd
                && isShowAliasList == otherCommandResult.isShowAliasList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowHelp, isAddVisit, isProfile, exit);
    }



}
