package seedu.system.logic.commands.outofsession;

import static java.util.Objects.requireNonNull;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.CommandType;
import seedu.system.logic.commands.exceptions.InSessionCommandException;
import seedu.system.model.Data;
import seedu.system.model.Model;

/**
 * Clears the the participation, person and competition data in the system.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final CommandType COMMAND_TYPE = CommandType.GENERAL;

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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof ClearCommand; // instanceof handles nulls
    }
}

