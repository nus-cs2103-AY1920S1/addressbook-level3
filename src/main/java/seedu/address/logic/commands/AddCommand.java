package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.Day;


/**
 * Adds a contacts to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contacts to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New contacts added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contacts already exists in the address book";

    private final Contact contactToAdd;
    private final Accommodation accommodationToAdd;
    private final List<Day> daysToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Contact}
     */
    public AddCommand(Contact contact) {
        requireNonNull(contact);
        contactToAdd = contact;
        accommodationToAdd = null;
        daysToAdd = null;
    }

    /**
     * Creates an AddCommand to add the specified {@code Accommodation}
     */
    public AddCommand(Accommodation accommodation) {
        requireNonNull(accommodation);
        contactToAdd = null;
        accommodationToAdd = accommodation;
        daysToAdd = null;
    }

    /**
     * Creates an AddCommand to add the specified {@code Day}
     */
    public AddCommand(List<Day> days) {
        requireNonNull(days);
        contactToAdd = null;
        accommodationToAdd = null;
        daysToAdd = days;
    }

    //model has yet to be updated, execution of add day and activity would be implemented in another commit
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasContact(contactToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.addContact(contactToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, contactToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (contactToAdd != null && accommodationToAdd == null && daysToAdd == null) {
            return other == this // short circuit if same object
                    || (other instanceof AddCommand // instanceof handles nulls
                    && (contactToAdd.equals(((AddCommand) other).contactToAdd)));
        } else if (contactToAdd == null && accommodationToAdd != null && daysToAdd == null) {
            return other == this // short circuit if same object
                    || (other instanceof AddCommand // instanceof handles nulls
                    && (accommodationToAdd.equals(((AddCommand) other).accommodationToAdd)));
        } else if (contactToAdd == null && accommodationToAdd == null && daysToAdd != null) {
            return other == this // short circuit if same object
                    || (other instanceof AddCommand // instanceof handles nulls
                    && (daysToAdd.equals(((AddCommand) other).daysToAdd)));
        } else {
            return false;
        }
    }
}
