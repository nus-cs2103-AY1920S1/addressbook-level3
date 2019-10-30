package seedu.address.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.InSessionCommandException;
import seedu.address.model.Data;
import seedu.address.model.Model;

/**
 * Clears the the participation, person and competition data in the system.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) throws InSessionCommandException {
        requireNonNull(model);

        if (model.hasOngoingSession()) {
            throw new InSessionCommandException();
        }

        model.setParticipations(new Data());
        model.setPersons(new Data());
        model.setCompetitions(new Data());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

