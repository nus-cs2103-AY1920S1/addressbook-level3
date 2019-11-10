package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.model.claim.Claim;
import seedu.address.model.contact.Contact;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The claim pop-up should appear*/
    private boolean toShowClaim;

    /** The contact pop-up should appear*/
    private boolean toShowContact;

    /** User wants to create a shortcut **/
    private boolean createShortCut;

    /** Check if user wants to clear the FinSec */
    private boolean toClear;

    /** Claim object */
    private Claim claim;

    /** Contact object */
    private Contact contact;


    //@@author{lawncegoh}
    /**
     * Constructs a {@code CommandResult} with the specified fields that includes shortcut
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean createShortCut,
                         boolean toShowClaim, boolean toShowContact) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.createShortCut = createShortCut;
        this.toShowClaim = toShowClaim;
        this.toShowContact = toShowContact;
    }

    //@@author{lawncegoh}
    /**
     * Constructs a (@code CommandResult} with the clear boolean
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean createShortCut,
                         boolean toShowClaim, boolean toShowContact, boolean toClear) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.createShortCut = createShortCut;
        this.toShowClaim = toShowClaim;
        this.toShowContact = toShowContact;
        this.toClear = toClear;
    }

    //@@author{lawncegoh}
    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean toShowClaim, boolean toShowContact) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.toShowClaim = toShowClaim;
        this.toShowContact = toShowContact;
    }

    //@@author{lawncegoh}
    /**
     * Constructs a {@code CommandResult} with the specified fields that includes a specific Claim instance
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean toShowClaim, Claim claim) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.toShowClaim = toShowClaim;
        this.claim = claim;
    }

    //@@author{lawncegoh}
    /**
     * Constructs a {@code CommandResult} with the specified fields that includes a specific Contact instance
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean toShowClaim,
                         boolean toShowContact, Contact contact) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.toShowClaim = toShowClaim;
        this.toShowContact = toShowContact;
        this.contact = contact;
    }


    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false,
                false, false);
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

    public boolean hasClaim() {
        return toShowClaim;
    }

    public boolean hasContact() {
        return toShowContact;
    }

    public boolean isCreateShortCut() {
        return createShortCut;
    }

    public boolean isToClear() {
        return toClear;
    }

    /**
     * Returns the claim in the constructor
     * @return
     */
    public Claim giveClaim() {
        return claim;
    }

    /**
     * Returns the claim in the constructor
     * @return
     */
    public Contact giveContact() {
        return contact;
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
