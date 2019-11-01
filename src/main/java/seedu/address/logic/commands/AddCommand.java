package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;



/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person or people to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME/S "
            + PREFIX_CLASSID + "CLASSID "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe,Ted,Tom "
            + PREFIX_CLASSID + "CS2030";

    public static final String MESSAGE_SUCCESS = "New people added!";
    public static final String MESSAGE_DUPLICATE_PERSON = "Trying to add a person that "
            + "already exists in the address book";

    private final ArrayList<Person> toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(ArrayList<Person> people) {
        requireNonNull(people);
        toAdd = people;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        for (Person student : toAdd) {
            if (model.hasPerson(student)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.addPerson(student);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
