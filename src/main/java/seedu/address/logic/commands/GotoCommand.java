package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.View;

/**
 * Lists all persons in the address book to the user.
 */
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_SUCCESS = "DONE";

    private View gotoView;

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
        if (gotoView.getIndex() == 1) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            //message = "contacts listed";
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (gotoView.getIndex() == 2) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            //message = "claims listed";
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            //message = "nothing";
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
