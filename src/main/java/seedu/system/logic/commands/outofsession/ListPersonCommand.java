package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;
import static seedu.system.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPersonCommand extends Command {

    public static final String COMMAND_WORD = "listPerson";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
