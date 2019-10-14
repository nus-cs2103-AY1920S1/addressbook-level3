package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIRST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIDDLE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.body.Body;
import seedu.address.model.notif.Notif;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a worker to the address book. "
            + "Parameters: "
            + PREFIX_FLAG + "w "
            + PREFIX_FIRST_NAME + " FIRST NAME "
            + PREFIX_MIDDLE_NAME + " MIDDLE NAME "
            + PREFIX_LAST_NAME + " LAST NAME "
            + PREFIX_PHONE + " PHONE "
            + PREFIX_SEX + " SEX "
            + PREFIX_DATE_OF_BIRTH + " DATE OF BIRTH "
            + PREFIX_DATE_JOINED + " DATE JOINED "
            + PREFIX_DESIGNATION + " DESIGNATION "
            + PREFIX_EMPLOYMENT_STATUS + " EMPLOYMENT STATUS"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FIRST_NAME + " John "
            + PREFIX_MIDDLE_NAME + " Charlie "
            + PREFIX_LAST_NAME + " Doe "
            + PREFIX_PHONE + " 91234567 "
            + PREFIX_SEX + " Male "
            + PREFIX_DATE_OF_BIRTH + " 12/12/1997 "
            + PREFIX_DATE_JOINED + " 1/1/2019 "
            + PREFIX_DESIGNATION + " Technician "
            + PREFIX_EMPLOYMENT_STATUS + " Cleaning";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Entity toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Body}, {@code Worker} or {@code Fridge}.
     */
    public AddCommand(Entity entity) {
        requireNonNull(entity);
        toAdd = entity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEntity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addEntity(toAdd);
        if (toAdd instanceof Body) {
            NotifCommand notifCommand = new NotifCommand(new Notif((Body) toAdd));
            notifCommand.execute(model);
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
