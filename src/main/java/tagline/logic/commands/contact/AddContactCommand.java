package tagline.logic.commands.contact;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_ADDRESS;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_EMAIL;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_NAME;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_PHONE;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddContactCommand extends ContactCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddContactCommand to add the specified {@code Person}
     */
    public AddContactCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddContactCommand // instanceof handles nulls
                && toAdd.equals(((AddContactCommand) other).toAdd));
    }
}
