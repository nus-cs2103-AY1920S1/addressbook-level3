package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.IncidentManager;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Incident Manager has been cleared!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getLoggedInPerson() == null || Person.isNotAdmin(model.getLoggedInPerson())) {
            throw new CommandException(Messages.MESSAGE_ACCESS_ADMIN);
        }
        model.setIncidentManager(new IncidentManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
