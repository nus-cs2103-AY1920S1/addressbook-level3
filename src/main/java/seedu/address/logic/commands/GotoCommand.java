package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLAIMS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INCOMES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.View;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
//@@author {lawncegoh}
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_SUCCESS_CONTACTS = "All Contacts Listed";

    public static final String MESSAGE_SUCCESS_CLAIMS = "All Claims Listed";

    public static final String MESSAGE_SUCCESS_INCOMES = "All Incomes Listed";

    public static final String MESSAGE_FAILURE = "Error in displaying View";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Goes to the view identified in the parameter typed by user\n"
            + "Parameters: ' contacts', ' claims', ' incomes'\n"
            + "Example: " + COMMAND_WORD + " contacts";

    private View gotoView;

    /**
     * Constructs the command based on the view that is placed in the params
     * @param view 3 different types of view possible
     * @throws ParseException if the view is not properly typed by user
     */
    public GotoCommand(View view) {
        requireNonNull(view);
        gotoView = view;
    }

    /**
     * Gives an empty constructor for tests
     */
    public GotoCommand() {

    }

    /**
     * Executes method to determine the right view
     * @param model {@code Model} which the command should operate on.
     * @return a CommandResult after updating the model
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (gotoView.getIndex() == 1) { //case of contacts
            model.updateFilteredContactList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_CONTACTS);
        } else if (gotoView.getIndex() == 2) { //case of claims
            model.updateFilteredClaimList(PREDICATE_SHOW_ALL_CLAIMS);
            return new CommandResult(MESSAGE_SUCCESS_CLAIMS);
        } else if (gotoView.getIndex() == 3) { //case of income
            model.updateFilteredIncomeList(PREDICATE_SHOW_ALL_INCOMES);
            return new CommandResult(MESSAGE_SUCCESS_INCOMES);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    /**
     * Checks if 2 gotocommands are equal
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GotoCommand // instanceof handles nulls
                && gotoView.equals(((GotoCommand) other).gotoView)); // state check
    }
}
