//@@author SakuraBlossom
package seedu.address.logic.commands.patients;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.OmniPanelTab.PATIENTS_TAB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class RegisterPatientCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "newpatient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": registers a patient. "
            + "Parameters: "
            + PREFIX_ID + "REFERENCE_ID "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "E0000001A "
            + PREFIX_NAME + "Edmond Halley "
            + PREFIX_PHONE + "85732743 "
            + PREFIX_EMAIL + "halley@example.com "
            + PREFIX_ADDRESS + "12, Kent ridge Ave 3, #01-11 "
            + PREFIX_TAG + "AIDS";

    public static final String MESSAGE_SUCCESS = "New patient added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient has already been registered.";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public RegisterPatientCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setTabListing(PATIENTS_TAB);

        if (model.hasPatient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPatient(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RegisterPatientCommand // instanceof handles nulls
                && toAdd.equals(((RegisterPatientCommand) other).toAdd));
    }
}
