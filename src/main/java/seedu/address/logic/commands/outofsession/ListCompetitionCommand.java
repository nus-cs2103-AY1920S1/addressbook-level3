package seedu.address.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COMPETITIONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.InSessionCommandException;
import seedu.address.model.Model;

/**
 * Lists all competitions in the address book to the user.
 */
public class ListCompetitionCommand extends Command {

    public static final String COMMAND_WORD = "listCompetition";

    public static final String MESSAGE_SUCCESS = "Listed all competitions";

    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        model.updateFilteredCompetitionList(PREDICATE_SHOW_ALL_COMPETITIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
