package cs.f10.t1.nursetraverse.logic.commands;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_EMAIL;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_NAME;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_PATIENT_VISIT_TODO;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_PHONE;
import static cs.f10.t1.nursetraverse.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.patient.Address;
import cs.f10.t1.nursetraverse.model.patient.Email;
import cs.f10.t1.nursetraverse.model.patient.Name;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.Phone;
import cs.f10.t1.nursetraverse.model.patient.exceptions.DuplicatePatientException;
import cs.f10.t1.nursetraverse.model.tag.Tag;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;


/**
 * Edits the details of an existing patient in the patient book.
 */
public class EditCommand extends MutatorCommand {

    public static final String COMMAND_WORD = "pat-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the patient identified "
            + "by the index number used in the displayed patient list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "[" + PREFIX_PATIENT_VISIT_TODO + "VISIT_TODO]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PATIENT_SUCCESS = "Edited Patient: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PATIENT = "This patient already exists in the patient book.";

    private final Index index;
    private final EditPatientDescriptor editPatientDescriptor;

    /**
     * @param index of the patient in the filtered patient list to edit
     * @param editPatientDescriptor details to edit the patient with
     */
    public EditCommand(Index index, EditPatientDescriptor editPatientDescriptor) {
        requireNonNull(index);
        requireNonNull(editPatientDescriptor);

        this.index = index;
        this.editPatientDescriptor = new EditPatientDescriptor(editPatientDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getStagedPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());
        Patient editedPatient = createEditedPatient(patientToEdit, editPatientDescriptor);

        try {
            model.setPatient(patientToEdit, editedPatient);
        } catch (DuplicatePatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT, e);
        }
        model.updateFilteredPatientList(Model.PREDICATE_SHOW_ALL_PATIENTS);
        model.setAppointments(patientToEdit, editedPatient);
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);

        return new CommandResult(String.format(MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient));
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code editPatientDescriptor}.
     */
    private static Patient createEditedPatient(Patient patientToEdit, EditPatientDescriptor editPatientDescriptor) {
        assert patientToEdit != null;

        Name updatedName = editPatientDescriptor.getName().orElse(patientToEdit.getName());
        Phone updatedPhone = editPatientDescriptor.getPhone().orElse(patientToEdit.getPhone());
        Email updatedEmail = editPatientDescriptor.getEmail().orElse(patientToEdit.getEmail());
        Address updatedAddress = editPatientDescriptor.getAddress().orElse(patientToEdit.getAddress());
        Set<Tag> updatedTags = editPatientDescriptor.getTags().orElse(patientToEdit.getTags());
        Collection<VisitTodo> updatedVisitTodos = editPatientDescriptor.getVisitTodos()
                .orElse(patientToEdit.getVisitTodos());
        //Editing visits with this command is not supported
        List<Visit> updatedVisits = patientToEdit.getVisits();

        return new Patient(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedVisitTodos, updatedVisits);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPatientDescriptor.equals(e.editPatientDescriptor);
    }

    /**
     * Stores the details to edit the patient with. Each non-empty field value will replace the
     * corresponding field value of the patient.
     */
    public static class EditPatientDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Collection<VisitTodo> visitTodos;

        public EditPatientDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPatientDescriptor(EditPatientDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setVisitTodos(toCopy.visitTodos);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, visitTodos);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code visitTodos} to this object's {@code visitTodos}.
         * A defensive copy of {@code visitTodos} is used internally.
         */
        public void setVisitTodos(Collection<VisitTodo> visitTodos) {
            this.visitTodos = (visitTodos != null) ? new LinkedHashSet<>(visitTodos) : null;
        }

        /**
         * Returns an unmodifiable visitTodo collection, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code visitTodos} is null.
         */
        public Optional<Collection<VisitTodo>> getVisitTodos() {
            return (visitTodos != null) ? Optional
                    .of(Collections.unmodifiableCollection(visitTodos)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPatientDescriptor)) {
                return false;
            }

            // state check
            EditPatientDescriptor e = (EditPatientDescriptor) other;

            if (getVisitTodos().isEmpty() == e.getVisitTodos().isEmpty()) {
                //If both collections populated, ensure equal data
                if (getVisitTodos().isPresent()) {
                    //Verify visit todos separately because .equals doesn't work with Collection<>
                    if (!CollectionUtil.checkEqual(getVisitTodos().get(), e.getVisitTodos().get())) {
                        return false;
                    }
                }
            } else {
                //One empty, one populated, different
                return false;
            }

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
