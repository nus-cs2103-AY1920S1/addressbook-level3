//@@author SakuraBlossom
package seedu.address.logic.commands.patients;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditPatientDetailsCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "editpatient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the patient identified "
            + "by the index number used in the displayed patient list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_ENTRY + "INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ENTRY + "1 "
            + PREFIX_PHONE + "91200567 "
            + PREFIX_EMAIL + "edmond@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Patient: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient has already been registered.";
    public static final String MESSAGE_PATIENT_IN_QUEUE = "Cannot edit while patient is in queue.";
    public static final String MESSAGE_PATIENT_BEING_SERVED = "Cannot edit while patient is being served.";

    private final Person personToEdit;
    private final Person editedPerson;

    /**
     * @param personToEdit person to be edited details
     * @param editedPerson person with edited details
     */
    public EditPatientDetailsCommand(Person personToEdit, Person editedPerson) {
        requireAllNonNull(personToEdit, editedPerson);
        this.personToEdit = personToEdit;
        this.editedPerson = editedPerson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (personToEdit.equals(editedPerson)) {
            throw new CommandException(Messages.MESSAGE_NOT_EDITED);
        }

        if (personToEdit == null || editedPerson == null || !model.hasExactPatient(personToEdit)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (model.hasPatient(editedPerson) && !personToEdit.isSameAs(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        if (model.isPatientBeingServed(personToEdit.getReferenceId())) {
            throw new CommandException(MESSAGE_PATIENT_BEING_SERVED);
        } else if (model.isPatientInQueue(personToEdit.getReferenceId())) {
            throw new CommandException(MESSAGE_PATIENT_IN_QUEUE);
        }

        if (model.isPatientInQueue(personToEdit.getReferenceId())) {
            model.changePatientRefIdInQueue(personToEdit.getReferenceId(), editedPerson.getReferenceId());
        }

        model.setPatient(personToEdit, editedPerson);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPatientDetailsCommand)) {
            return false;
        }

        // state check
        EditPatientDetailsCommand e = (EditPatientDetailsCommand) other;
        return personToEdit.equals(e.personToEdit)
                && editedPerson.equals(e.editedPerson);
    }

}
