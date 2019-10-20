package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
import seedu.address.model.contact.Contact;
import seedu.address.ui.UiManager;

/**
 * Checks the individual claim/contact identified through index
 */
public class CheckCommand extends Command {

    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_SUCCESS_CONTACT = "Contact Shown";

    public static final String MESSAGE_SUCCESS_CLAIM = "Claim Shown";

    public static final String MESSAGE_FAILURE = "Command not available in this View";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Goes to the view identified in the parameter typed by user\n"
            + "Parameters: 'index'\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    /**
     * Constructs a checkCommand
     * @param index that is from user input
     */
    public CheckCommand(Index index) {
        this.index = index;
    }

    /**
     * Returns a checkcommand only if the current view is either claims or contacts
     * @param model {@code Model} which the command should operate on.
     * @return a command result with the specific claim and showclaim boolean to be true
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //if the current state is not claims or contacts, the check command will be invalid
        if (UiManager.getState().equals("claims")) {
            List<Claim> lastShownList = model.getFilteredClaimList();
            Claim claimToShow = lastShownList.get(index.getZeroBased());
            return new CommandResult(MESSAGE_SUCCESS_CLAIM, false, false, true, claimToShow);
        } else if (UiManager.getState().equals("contacts")) {
            List<Contact> contactList = model.getFilteredContactList();
            Contact contactToShow = contactList.get(index.getZeroBased());
            return new CommandResult(MESSAGE_SUCCESS_CONTACT, false, false, false, true, contactToShow);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
