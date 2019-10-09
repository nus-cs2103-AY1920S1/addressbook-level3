package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person.
 */
public class DeletePersonCommand extends Command {
    public static final String COMMAND_WORD = "deleteperson";
    public static final String MESSAGE_SUCCESS = "Delete person success";
    public static final String MESSAGE_FAILURE = "Unable to delete person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME";

    public final Name name;

    public DeletePersonCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person toDelete = model.findPerson(name);
        if (toDelete != null) {
            if (model.deletePerson(toDelete.getPersonId())) {

                // update main window display
                model.updateDetailWindowDisplay(new DetailWindowDisplay());

                // update side panel display
                model.updateSidePanelDisplay(SidePanelDisplayType.PERSONS);

                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
        return new CommandResult(MESSAGE_FAILURE);
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof DeletePersonCommand)) {
            return false;
        } else if (((DeletePersonCommand) command).name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }
}
