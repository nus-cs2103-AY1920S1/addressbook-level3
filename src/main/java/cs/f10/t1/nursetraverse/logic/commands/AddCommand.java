package cs.f10.t1.nursetraverse.logic.commands;

import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_EMAIL;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_NAME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_PATIENT_VISIT_TODO;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_PHONE;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.patient.Patient;

/**
 * Adds a patient to the patient book.
 */
public class AddCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "pat-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to the patient book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "[" + PREFIX_PATIENT_VISIT_TODO + "VISIT_TODO]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney"
            + PREFIX_PATIENT_VISIT_TODO + "Check blood pressure "
            + PREFIX_PATIENT_VISIT_TODO + "Check if patient has been drinking";

    public static final String MESSAGE_SUCCESS = "New patient added: %1$s";
    public static final String MESSAGE_DUPLICATE_PATIENT = "This patient already exists in the patient book";

    private final Patient toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Patient}
     */
    public AddCommand(Patient patient) {
        requireNonNull(patient);
        toAdd = patient;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPatient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.addPatient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
