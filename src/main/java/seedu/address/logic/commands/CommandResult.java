package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.visual.DisplayFormat;
import seedu.address.model.visual.DisplayIndicator;

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
     * Application should display list of policies
     */
    private boolean listPolicy;

    /**
     * Application should display list of people
     */
    private boolean listPeople;

    /**
     * Application should display list of BinItems
     */
    private boolean listBin;

    /** Application should expand the person on the right panel */
    private boolean expandPerson;

    /** Application should expand the policy on the right panel */
    private boolean expandPolicy;

    private Person personToExpand;

    private Policy policyToExpand;

    /** Application should display history of commands */
    private boolean listHistory;

    /**
     * Application should display report
     */
    private boolean report;

    /**
     * Application should display specified indicator
     */
    private boolean display;

    private DisplayIndicator displayIndicator;

    private DisplayFormat displayFormat;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser,
                         boolean showHelp,
                         boolean exit,
                         boolean listPolicy,
                         boolean listPeople,
                         boolean report,
                         boolean display,
                         boolean expandPerson,
                         boolean expandPolicy,
                         boolean listHistory,
                         boolean listBin) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.listPolicy = listPolicy;
        this.listPeople = listPeople;
        this.listBin = listBin;
        this.report = report;
        this.display = display;
        this.listHistory = listHistory;
        this.expandPerson = expandPerson;
        this.expandPolicy = expandPolicy;
    }

    public CommandResult(String feedbackToUser, Person personToExpand) {
        this(feedbackToUser, false, false, false, false, false,
            false, true, false, false, false);
        this.personToExpand = personToExpand;
    }

    public CommandResult(String feedbackToUser, Policy policyToExpand) {
        this(feedbackToUser, false, false, false, false, false,
            false, false, true, false, false);
        this.policyToExpand = policyToExpand;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false,
            false, false, false, false, false);
    }

    public CommandResult(String feedbackToUser, DisplayIndicator displayIndicator, DisplayFormat displayFormat) {
        this(feedbackToUser, false, false, false, false, false,
            true, false, false, false, false);
        this.displayIndicator = displayIndicator;
        this.displayFormat = displayFormat;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isListPolicy() {
        return listPolicy;
    }

    public boolean isListPeople() {
        return listPeople;
    }

    public boolean isListBin() {
        return listBin;
    }

    public boolean isReport() {
        return report;
    }

    public boolean isDisplay() {
        return display;
    }

    public boolean isExpandPerson() {
        return expandPerson;
    }

    public boolean isExpandPolicy() {
        return expandPolicy;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public DisplayIndicator getDisplayIndicator() {
        return displayIndicator;
    }

    public DisplayFormat getDisplayFormat() {
        return displayFormat;
    }

    public Person getPersonToExpand() {
        return personToExpand;
    }

    public Policy getPolicyToExpand() {
        return policyToExpand;
    }

    public boolean isListHistory() {
        return listHistory;
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
            && listPolicy == otherCommandResult.listPolicy
            && listPeople == otherCommandResult.listPeople
            && report == otherCommandResult.report
            && display == otherCommandResult.display
            && expandPerson == otherCommandResult.expandPerson
            && expandPolicy == otherCommandResult.expandPolicy
            && listHistory == otherCommandResult.listHistory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, listPolicy, listPeople, report,
            display, expandPerson, expandPolicy, listHistory);
    }

}
