package seedu.address.logic.commands;

/**
 * Adds a contacts to the address book.
 */
public abstract class UnscheduleCommand extends Command {

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
    public UnscheduleCommand(Contact contact) {
        requireNonNull(contact);
        contactToAdd = contact;
        accommodationToAdd = null;
        daysToAdd = null;
    }

    /**
     * Creates an AddCommand to add the specified {@code Accommodation}
     */
    public UnscheduleCommand(Accommodation accommodation) {
        requireNonNull(accommodation);
        contactToAdd = null;
        accommodationToAdd = accommodation;
        daysToAdd = null;
    }

    /**
     * Creates an AddCommand to add the specified {@code Day}
     */
    public UnscheduleCommand(List<Day> days) {
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
                    || (other instanceof UnscheduleCommand // instanceof handles nulls
                    && (contactToAdd.equals(((UnscheduleCommand) other).contactToAdd)));
        } else if (contactToAdd == null && accommodationToAdd != null && daysToAdd == null) {
            return other == this // short circuit if same object
                    || (other instanceof UnscheduleCommand // instanceof handles nulls
                    && (accommodationToAdd.equals(((UnscheduleCommand) other).accommodationToAdd)));
        } else if (contactToAdd == null && accommodationToAdd == null && daysToAdd != null) {
            return other == this // short circuit if same object
                    || (other instanceof UnscheduleCommand // instanceof handles nulls
                    && (daysToAdd.equals(((UnscheduleCommand) other).daysToAdd)));
        } else {
            return false;
        }
    }
}
