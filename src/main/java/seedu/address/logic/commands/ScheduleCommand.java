package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Gives the schedule for a list of persons.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_SUCCESS = "Schedule created!";
    public static final String MESSAGE_FAILURE = "Unable to generate schedule: %s not found";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "NAME ...";

    private ArrayList<Name> names;

    public ScheduleCommand(ArrayList<Name> names) {
        this.names = names;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ArrayList<Person> persons = new ArrayList<>();
        persons.add(model.getUser());
        for (Name name: names) {
            try {
                persons.add(model.findPerson(name));
            } catch (PersonNotFoundException e) {
                return new CommandResultBuilder(String.format(MESSAGE_FAILURE, name.toString())).build();
            }
        }

        // update main window
        model.updateScheduleWithPersons(persons, LocalDateTime.now(), ScheduleState.GROUP);

        return new CommandResultBuilder(MESSAGE_SUCCESS).build();
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof ScheduleCommand)) {
            return false;
        } else if (((ScheduleCommand) command).names.equals(this.names)) {
            return true;
        } else {
            return false;
        }
    }
}
