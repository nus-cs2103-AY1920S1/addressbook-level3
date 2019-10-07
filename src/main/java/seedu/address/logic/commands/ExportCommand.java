package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/** Command to export visual representations */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Exporting person %1$s";
    public static final String MESSAGE_FAILURE = "Failed to export...";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This entity does not exists in the address book!";
    public static final String MESSAGE_USAGE = "Export command takes in a person's name as argument!";

    private Name name;

    public ExportCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //Does nothing to the model.
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, person.get()), false,
                false, COMMAND_WORD, person.get());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof ExportCommand) {
            return ((ExportCommand) o).name.equals(this.name);
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Command o) {
        if (o == this) {
            return true;
        } else if (o instanceof ExportCommand) {
            return ((ExportCommand) o).name.equals(this.name);
        } else {
            return false;
        }
    }
}
