package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.profile.Model;
import seedu.address.profile.person.Person;

/**
 * Adds a person to Duke Cooks.
 */
public class AddProfileCommand extends Command {

    public static final String COMMAND_WORD = "addProfile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to Duke Cooks. ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";

    private final Person toAdd;

    /**
     * Creates an AddProfileCommand to add the specified {@code Person}
     */
    public AddProfileCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddProfileCommand // instanceof handles nulls
                && toAdd.equals(((AddProfileCommand) other).toAdd));
    }
}
