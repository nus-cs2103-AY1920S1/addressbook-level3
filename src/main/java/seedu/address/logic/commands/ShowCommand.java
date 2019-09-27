package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.ui.UiViewManager;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

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

        uiViewManager.changeUiDetailsView(person.get());
        return new CommandResult(String.format(MESSAGE_SUCCESS, person.get()));
    }
}
