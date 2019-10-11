package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Command to show the details of a person.
 */
public class ShowPersonCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SUCCESS = "Showing person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in the address book!";
    public static final String MESSAGE_USAGE = "Show command takes in a person's or group's name as argument!";

    private final Name name;

    public ShowPersonCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> personList = model.getFilteredPersonList();

        Optional<Person> person = Optional.empty();
        for (Person p : personList) {
            if (p.getName().equals(name)) {
                person = Optional.of(p);
                break;
            }
        }

        if (person.isEmpty()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        model.updateDetailWindowDisplay(name, LocalDateTime.now(), DetailWindowDisplayType.PERSON);
        return new CommandResult(String.format(MESSAGE_SUCCESS, person.get()), false,
                false, COMMAND_WORD);
    }

    @Override
    public boolean equals(Command command) {
        return this == command //short circuit if same command
                || (command instanceof ShowPersonCommand)
                && (name.equals(((ShowPersonCommand) command).name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowPersonCommand // instanceof handles nulls
                && name.equals(((ShowPersonCommand) other).name));
    }

}
