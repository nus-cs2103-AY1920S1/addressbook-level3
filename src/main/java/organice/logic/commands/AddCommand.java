package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import organice.logic.commands.exceptions.CommandException;
import organice.model.Model;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to ORGANice. "
            + "Parameters: "
            + PREFIX_TYPE + "PERSON TYPE "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_AGE + "AGE "
            + PREFIX_PRIORITY + "PRIORITY "
            + PREFIX_BLOOD_TYPE + "BLOOD TYPE "
            + PREFIX_TISSUE_TYPE + "TISSUE TYPE "
            + PREFIX_ORGAN + "ORGAN "
            + PREFIX_DOCTOR_IN_CHARGE + "DOCTOR IN CHARGE "
        + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "patient "
            + PREFIX_NRIC + "S1234568R "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_AGE + "21 "
            + PREFIX_PRIORITY + "high "
            + PREFIX_BLOOD_TYPE + "A+ "
            + PREFIX_TISSUE_TYPE + "1,2,3,4,5,6 "
            + PREFIX_ORGAN + "kidney "
            + PREFIX_DOCTOR_IN_CHARGE + "S1111111A ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in ORGANice";
    public static final String MESSAGE_DOCTOR_NOT_FOUND = "The doctor in charge specified is not found in ORGANice";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        if (toAdd.getType().toString().equals("patient")) {
            Nric doctorInCharge = new Nric(((Patient) toAdd).getDoctorInCharge().toString());
            if (!model.hasDoctor(doctorInCharge)) {
                throw new CommandException(MESSAGE_DOCTOR_NOT_FOUND);
            }
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
