package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.View;

/**
 * Lists all persons in the address book to the user.
 */
public class GotoContactsCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    private String message;

    private View gotoView;

    public GotoContactsCommand(View view) throws ParseException {
        gotoView = view;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (gotoView.getIndex() == 1) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult("contacts listed");
        } else if (gotoView.getIndex() == 2) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult("claims listed");
        } else {
            return new CommandResult("nothing");
        }
    }
}
