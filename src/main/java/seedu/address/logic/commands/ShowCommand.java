package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Command to show the details of a person.
 */
public class ShowCommand<T> extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SUCCESS = "Showing: %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in TimeBook!";
    public static final String MESSAGE_GROUP_NOT_FOUND = "This group does not exists in the TimeBook!";
    public static final String MESSAGE_USAGE = "Show command takes in a person's or group's name as argument!";

    private final T name;

    public ShowCommand(T name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (name instanceof Name) {
            ObservableList<Person> personList = model.getObservablePersonList();

            try {
                model.updateScheduleWithPerson((Name) name, LocalDateTime.now(), ScheduleState.PERSON);
            } catch (PersonNotFoundException e) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            }

            return new CommandResultBuilder(
                    String.format(MESSAGE_SUCCESS, name.toString())).build();

        } else if (name instanceof GroupName) {
            ObservableList<Group> groupList = model.getObservableGroupList();

            try {
                model.updateScheduleWithGroup((GroupName) name, LocalDateTime.now(), ScheduleState.GROUP);
            } catch (GroupNotFoundException e) {
                throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
            }

            return new CommandResultBuilder(
                    String.format(MESSAGE_SUCCESS, name.toString())).build();
        } else {

            model.updateScheduleWithUser(LocalDateTime.now(), ScheduleState.PERSON);
            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, "Your schedule")).build();
        }
    }

    @Override
    public boolean equals(Command command) {
        return this == command //short circuit if same command
                || (command instanceof ShowCommand)
                && (name.equals(((ShowCommand) command).name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && name.equals(((ShowCommand) other).name));
    }

}
