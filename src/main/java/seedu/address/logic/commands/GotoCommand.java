package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.*;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.View;

/**
 * Lists all persons in the address book to the user.
 */
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_SUCCESS = "DONE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Goes to the view identified in the parameter typed by user\n"
            + "Parameters: ' contacts', ' claims', ' income'\n"
            + "Example: " + COMMAND_WORD + " contacts";

    private View gotoView;

    /**
     * Constructor that constructs the command based on the view that is placed in the params
     * @param view 3 different types of view possible
     * @throws ParseException if the view is not properly typed by user
     */
    public GotoCommand(View view) throws ParseException {
        try {
            if (view == null) {
                throw new ParseException("error");
            }
            gotoView = view;
        } catch (ParseException e) {
            e.getMessage();
        }
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (gotoView.getIndex() == 1) { //case of contacts
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            //message = "contacts listed";
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (gotoView.getIndex() == 2) { //case of claims
            model.updateFilteredClaimList(PREDICATE_SHOW_ALL_CLAIMS);
            //message = "claims listed";
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (gotoView.getIndex() == 3) { //case of income
            model.updateFilteredIncomeList(PREDICATE_SHOW_ALL_INCOMES);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            //message = "nothing";
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
