package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Find a person.
 */
public class FindPersonCommand extends Command {
    public static final String COMMAND_WORD = "findperson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME";

    public static final String MESSAGE_SUCCESS = "Found person: %s";
    public static final String MESSAGE_FAILURE = "Unable to find person: %s does not exist";

    public final Name name;

    public FindPersonCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            Person person = model.findPerson(name);

            // update main window
            model.updateScheduleWindowDisplay(person.getName(), LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);

            // update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);

            return new CommandResult(String.format(MESSAGE_SUCCESS, name.toString()));

        } catch (PersonNotFoundException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, name.toString()));
        }

    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof FindPersonCommand)) {
            return false;
        } else if (((FindPersonCommand) command).name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }
}
