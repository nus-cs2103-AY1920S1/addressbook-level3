package organice.logic.commands;

import static java.util.Objects.requireNonNull;

import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import organice.logic.commands.exceptions.CommandException;
import organice.model.Model;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.Type;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to ORGANice. "
            + "\nTo add a patient, the command format is as follows:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_AGE + "AGE "
            + PREFIX_PRIORITY + "PRIORITY "
            + PREFIX_BLOOD_TYPE + "BLOOD TYPE "
            + PREFIX_TISSUE_TYPE + "TISSUE TYPE "
            + PREFIX_ORGAN + "ORGAN "
            + PREFIX_DOCTOR_IN_CHARGE + "DOCTOR IN CHARGE "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "patient "
            + PREFIX_NRIC + "S1234568R "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_AGE + "21 "
            + PREFIX_PRIORITY + "high "
            + PREFIX_BLOOD_TYPE + "A "
            + PREFIX_TISSUE_TYPE + "1,2,3,4,5,6 "
            + PREFIX_ORGAN + "kidney "
            + PREFIX_DOCTOR_IN_CHARGE + "S1111111A\n"
            + "\nTo add a donor, the command format is as follows:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_AGE + "AGE "
            + PREFIX_BLOOD_TYPE + "BLOOD TYPE "
            + PREFIX_TISSUE_TYPE + "TISSUE TYPE "
            + PREFIX_ORGAN + "ORGAN "
            + PREFIX_ORGAN_EXPIRY_DATE + "ORGAN EXPIRY DATE"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "donor "
            + PREFIX_NRIC + "S1234568R "
            + PREFIX_NAME + "John Doe Donor "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_AGE + "21 "
            + PREFIX_PRIORITY + "high "
            + PREFIX_BLOOD_TYPE + "A "
            + PREFIX_TISSUE_TYPE + "1,2,3,4,5,6 "
            + PREFIX_ORGAN + "kidney "
            + PREFIX_ORGAN_EXPIRY_DATE + "23-Oct-2019\n"
            + "\nTo add a doctor, the command format is as follows:\n"
            + PREFIX_TYPE + "TYPE "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_AGE + "AGE "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "doctor "
            + PREFIX_NRIC + "S1234568R "
            + PREFIX_NAME + "John Doe Doctor "
            + PREFIX_PHONE + "98765432\n"
            + "\nTo add a person in a form mode, the command is as follows:\n"
            + PREFIX_TYPE + "TYPE "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "doctor ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in ORGANice";
    public static final String MESSAGE_DOCTOR_NOT_FOUND = "The doctor in charge specified is not found in ORGANice";

    private final Person toAdd;
    private final Type formType;

    /**
     * Creates an AddCommand to add the specified {@code person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
        formType = null;
    }

    public AddCommand(Type formType) {
        toAdd = null;
        this.formType = formType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (toAdd == null) {
            return new CommandResult(String.format("Add doctor"), true, formType);
        }

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
