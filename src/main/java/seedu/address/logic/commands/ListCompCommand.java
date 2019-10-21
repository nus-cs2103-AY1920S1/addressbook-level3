package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COMPETITIONS;

import seedu.address.model.Model;

/**
 * Lists all competitions in the address book to the user.
 */
public class ListCompCommand extends Command {

    public static final String COMMAND_WORD = "listComp";

    public static final String MESSAGE_SUCCESS = "Listed all competitions";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCompetitionList(PREDICATE_SHOW_ALL_COMPETITIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
