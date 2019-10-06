package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.ui.UiViewManager;

/**
 * Shows details of a person or a group.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SUCCESS = "Showing person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in the address book!";
    public static final String MESSAGE_USAGE = "Show command takes in a person's name as argument!";

    private final Name name;
    private final UiViewManager uiViewManager;

    public ShowCommand(Name name) {
        requireNonNull(name);
        this.name = name;
        this.uiViewManager = new UiViewManager();
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

        return new CommandResult(String.format(MESSAGE_SUCCESS, person.get()), false,
                false, "show", person.get());
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
