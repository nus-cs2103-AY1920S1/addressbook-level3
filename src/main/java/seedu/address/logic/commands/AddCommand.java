package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;

/**
 * Adds a person to Mortago.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entity to Mortago.\n"
            + "Adding a worker:\n"
            + "Compulsory Fields: "
            + PREFIX_FLAG + "w "
            + PREFIX_NAME + " NAME "
            + PREFIX_SEX + " SEX "
            + PREFIX_DATE_JOINED + " DATE JOINED\n"
            + "Optional Fields: "
            + PREFIX_PHONE_NUMBER + " PHONE "
            + PREFIX_DATE_OF_BIRTH + " DATE OF BIRTH "
            + PREFIX_DESIGNATION + " DESIGNATION "
            + PREFIX_EMPLOYMENT_STATUS + " EMPLOYMENT STATUS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe"
            + PREFIX_PHONE_NUMBER + " 91234567 "
            + PREFIX_SEX + " Male "
            + PREFIX_DATE_OF_BIRTH + " 12/12/1997 "
            + PREFIX_DATE_JOINED + " 1/1/2019 "
            + PREFIX_DESIGNATION + " Technician "
            + PREFIX_EMPLOYMENT_STATUS + " Cleaning\n"
            + "Adding a body:\n"
            + "Compulsory Fields: "
            + PREFIX_FLAG + "b "
            + PREFIX_NAME + " NAME "
            + PREFIX_SEX + " SEX "
            + PREFIX_DATE_OF_DEATH + " DATE OF DEATH "
            + PREFIX_DATE_OF_ADMISSION + " DATE OF ADMISSION\n"
            + "Optional Fields: "
            + PREFIX_NRIC + " NRICE "
            + PREFIX_RELIGION + " RELIGION "
            + PREFIX_NAME_NOK + " NAME OF NEXT OF KIN "
            + PREFIX_RELATIONSHIP + " RELATIONSHIP OF NEXT OF KIN"
            + PREFIX_PHONE_NOK + " PHONE OF NEXT OF KIN "
            + PREFIX_ORGANS_FOR_DONATION + " ORGANS FOR DONATION"
            + PREFIX_STATUS + " BODY STATUS "
            + PREFIX_FRIDGE_ID + " FRIDGE ID\n"
            + "Adding a fridge: add -f";

    public static final String MESSAGE_SUCCESS = "New entity added: %1$s";
    public static final String MESSAGE_DUPLICATE_ENTITY = "This entity already exists in Mortago";

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
            throw new CommandException(MESSAGE_DUPLICATE_ENTITY);
        }

        model.addEntity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
