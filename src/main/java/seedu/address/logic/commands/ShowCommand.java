package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Command to show the details of a person.
 */
public class ShowCommand<T> extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SUCCESS = "Showing: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in the address book!";
    public static final String MESSAGE_GROUP_NOT_FOUND = "This group does not exists in the address book!";
    public static final String MESSAGE_USAGE = "Show command takes in a person's or group's name as argument!";

    private final T name;

    public ShowCommand(T name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (name instanceof Name) {
            ObservableList<Person> personList = model.getFilteredPersonList();
            Optional<Person> person = Optional.empty();
            for (Person p : personList) {
                if (p.getName().equals((Name) name)) {
                    person = Optional.of(p);
                    break;
                }
            }

            if (person.isEmpty()) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            }

            model.updateDetailWindowDisplay((Name) name, LocalDateTime.now(), DetailWindowDisplayType.PERSON);
            return new CommandResult(String.format(MESSAGE_SUCCESS, person.get()), false,
                    false, COMMAND_WORD);
        } else {
            ObservableList<Group> groupList = model.getObservableGroupList();
            Optional<Group> group = Optional.empty();
            for (Group g : groupList) {
                if (g.getGroupName().equals((GroupName) name)) {
                    group = Optional.of(g);
                    break;
                }
            }

            if (group.isEmpty()) {
                throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
            }

            model.updateDetailWindowDisplay((GroupName) name, LocalDateTime.now(), DetailWindowDisplayType.GROUP);
            return new CommandResult(String.format(MESSAGE_SUCCESS, group.get()), false,
                    false, COMMAND_WORD);
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
