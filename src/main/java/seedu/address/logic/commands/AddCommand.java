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
import seedu.address.model.activity.Activity;
import seedu.address.model.day.Day;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
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

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person personToAdd;
    private final Activity activityToAdd;
    private final List<Day> daysToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        personToAdd = person;
        activityToAdd = null;
        daysToAdd = null;
    }

    /**
     * Creates an AddCommand to add the specified {@code Activity}
     */
    public AddCommand(Activity activity) {
        requireNonNull(activity);
        personToAdd = null;
        activityToAdd = activity;
        daysToAdd = null;
    }

    /**
     * Creates an AddCommand to add the specified {@code Day}
     */
    public AddCommand(List<Day> days) {
        requireNonNull(days);
        personToAdd = null;
        activityToAdd = null;
        daysToAdd = days;
    }

    //model has yet to be updated, execution of add day and activity would be implemented in another commit
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(personToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(personToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (personToAdd != null && activityToAdd == null && daysToAdd == null) {
            return other == this // short circuit if same object
                    || (other instanceof AddCommand // instanceof handles nulls
                    && (personToAdd.equals(((AddCommand) other).personToAdd)));
        } else if (personToAdd == null && activityToAdd != null && daysToAdd == null) {
            return other == this // short circuit if same object
                    || (other instanceof AddCommand // instanceof handles nulls
                    && (activityToAdd.equals(((AddCommand) other).activityToAdd)));
        } else if (personToAdd == null && activityToAdd == null && daysToAdd != null) {
            return other == this // short circuit if same object
                    || (other instanceof AddCommand // instanceof handles nulls
                    && (daysToAdd.equals(((AddCommand) other).daysToAdd)));
        } else {
            return false;
        }
    }
}
