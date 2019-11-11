package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
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
        requireNonNull(index);
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
        Claim claimToShow = null;
        //if the current state is not claims or contacts, the check command will be invalid
        if (UiManager.getState().equals("claims")) {
            List<Claim> lastShownList = model.getFilteredClaimList();

            for (Claim claim : lastShownList) {
                if (Integer.parseInt(claim.getId().toString()) == index.getOneBased()) {
                    claimToShow = claim;
                }
            }
            if (claimToShow == null) { //throw error if index not valid
                throw new CommandException(Messages.MESSAGE_INVALID_CLAIM_DISPLAYED_INDEX);
            }
            return new CommandResult(MESSAGE_SUCCESS_CLAIM, false, false, true, claimToShow);
        } else if (UiManager.getState().equals("contacts")) {
            List<Contact> contactList = model.getFilteredContactList();

            //throw error if index not valid
            if (index.getZeroBased() >= contactList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            } else if (index.getZeroBased() < 0) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Contact contactToShow = contactList.get(index.getZeroBased());
            return new CommandResult(MESSAGE_SUCCESS_CONTACT, false, false, false, true, contactToShow);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    /**
     * Checks if 2 checkcommands are equal
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckCommand // instanceof handles nulls
                && index.equals(((CheckCommand) other).index)); // state check
    }
}
