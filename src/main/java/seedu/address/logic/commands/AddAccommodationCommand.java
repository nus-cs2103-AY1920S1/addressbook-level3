package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.accommodation.Accommodation;
import seedu.address.model.contact.Contact;
import seedu.address.model.field.ContactContainsNumberPredicate;

/**
 * Adds an accommodation to the itinerary.
 */
public class AddAccommodationCommand extends AddCommand {
    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
            + ": Adds an Accommodation to the itinerary."
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_PHONE + "NUMBER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: add " + COMMAND_WORD + " "
            + PREFIX_NAME + "Hotel 81 "
            + PREFIX_ADDRESS + "Geylang "
            + PREFIX_TAG + "cheap";

    public static final String MESSAGE_SUCCESS = "New accommodation added: %1s";
    public static final String MESSAGE_DUPLICATE_ACCOMMODATION = "This accommodation already exists in the itinerary.";

    private final Accommodation toAdd;

    /**
     * Creates an AddActivityCommand to add the specified {@Activity}
     */
    public AddAccommodationCommand(Accommodation accommodation) {
        requireNonNull(accommodation);
        toAdd = accommodation;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAccommodation(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOMMODATION);
        }

        if (toAdd.getContact().isPresent()) {
            model.updateFilteredContactList(new ContactContainsNumberPredicate(toAdd.getContact().get().getPhone()));
            List<Contact> sameNumberContacts = model.getFilteredContactList();
            if (!sameNumberContacts.isEmpty()) {
                model.addAccommodation(new Accommodation(toAdd.getName(), toAdd.getAddress(), sameNumberContacts.get(0),
                        toAdd.getTags()));
            } else {
                model.addAccommodation(toAdd);
            }
        } else {
            model.addAccommodation(toAdd);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAccommodationCommand
                && toAdd.equals(((AddAccommodationCommand) other).toAdd));
    }
}
