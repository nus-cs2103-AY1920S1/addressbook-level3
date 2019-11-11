package organice.logic.commands;

import static java.util.Objects.requireNonNull;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import organice.commons.util.CollectionUtil;
import organice.logic.commands.exceptions.CommandException;
import organice.model.Model;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.Status;
import organice.model.person.TissueType;
import organice.model.person.Type;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Edits the details of an existing person in ORGANice.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by their NRIC in ORGANice. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NRIC (must be a valid NRIC in ORGANice) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "Example: " + COMMAND_WORD + " "
            + "S9912345A "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person with Nric %1$s cannot be found in ORGANice!";

    private final Nric nric;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param nric of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Nric nric, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(nric);
        requireNonNull(editPersonDescriptor);

        this.nric = nric;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            requireNonNull(model);
            List<Person> lastShownList = model.getFilteredPersonList();

            Person personToEdit = nricToPerson(nric, lastShownList);
            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

            if (personToEdit.getType().isPatient()) {
                DoctorInCharge updatedDoctorInCharge = editPersonDescriptor.getDoctorInCharge().orElse((
                        (Patient) personToEdit).getDoctorInCharge());
                if (!model.hasDoctor(new Nric(updatedDoctorInCharge.toString()))) {
                    throw new CommandException(String.format("Doctor with NRIC %s does not exist in ORGANice!",
                            updatedDoctorInCharge.toString()));
                }
            }

            if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
        } catch (PersonNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, nric));
        }
    }

    /**
     * Returns a person that has the same NRIC with the NRIC specified.
     *
     * @param nric NRIC of the person to be searched.
     * @param lastShownList List of persons.
     */
    private Person nricToPerson(Nric nric, List<Person> lastShownList) throws PersonNotFoundException {
        Iterator<Person> iterator = lastShownList.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getNric().equals(nric)) {
                return person;
            }
        }
        throw new PersonNotFoundException();
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {

        assert personToEdit != null;

        Type updatedType = editPersonDescriptor.getType().orElse(personToEdit.getType());
        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());

        assert Type.isValidType(updatedType.toString());
        if (updatedType.isDoctor()) {
            String errorMessage = getErrorMessage(personToEdit, editPersonDescriptor);
            if (!errorMessage.isBlank()) {
                throw new CommandException(errorMessage);
            }

            return new Doctor(updatedType, updatedNric, updatedName, updatedPhone);
        } else {
            if (updatedType.isPatient()) {
                Age updatedAge = editPersonDescriptor.getAge().orElse(((Patient) personToEdit).getAge());
                BloodType updatedBloodType =
                    editPersonDescriptor.getBloodType().orElse(((Patient) personToEdit).getBloodType());
                TissueType updatedTissueType =
                    editPersonDescriptor.getTissueType().orElse(((Patient) personToEdit).getTissueType());
                Organ updatedOrgan = editPersonDescriptor.getOrgan().orElse(((Patient) personToEdit).getOrgan());
                Status updatedStatus = ((Patient) personToEdit).getStatus();
                Priority updatedPriority =
                        editPersonDescriptor.getPriority().orElse(((Patient) personToEdit).getPriority());
                DoctorInCharge updatedDoctorInCharge =
                        editPersonDescriptor.getDoctorInCharge().orElse(((Patient) personToEdit).getDoctorInCharge());

                String errorMessage = getErrorMessage(personToEdit, editPersonDescriptor);
                if (!errorMessage.equals("")) {
                    throw new CommandException(errorMessage);
                }

                return new Patient(updatedType, updatedNric, updatedName, updatedPhone, updatedAge, updatedPriority,
                    updatedBloodType, updatedTissueType, updatedOrgan, updatedDoctorInCharge, updatedStatus);
            } else if (updatedType.isDonor()) {
                Age updatedAge = editPersonDescriptor.getAge().orElse(((Donor) personToEdit).getAge());
                BloodType updatedBloodType =
                    editPersonDescriptor.getBloodType().orElse(((Donor) personToEdit).getBloodType());
                TissueType updatedTissueType =
                    editPersonDescriptor.getTissueType().orElse(((Donor) personToEdit).getTissueType());
                Organ updatedOrgan = editPersonDescriptor.getOrgan().orElse(((Donor) personToEdit).getOrgan());
                Status updatedStatus = ((Donor) personToEdit).getStatus();
                OrganExpiryDate updatedOrganExpiryDate =
                    editPersonDescriptor.getOrganExpiryDate().orElse(((Donor) personToEdit).getOrganExpiryDate());

                String errorMessage = getErrorMessage(personToEdit, editPersonDescriptor);
                if (!errorMessage.equals("")) {
                    throw new CommandException(errorMessage);
                }

                return new Donor(updatedType, updatedNric, updatedName, updatedPhone, updatedAge,
                    updatedBloodType, updatedTissueType, updatedOrgan, updatedOrganExpiryDate, updatedStatus);
            }
        }
        throw new CommandException(EditCommand.MESSAGE_USAGE);
    }

    /**
     * Returns an error message if user tried to edit a person's attribute that does not exist.
     */
    private static String getErrorMessage(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Type updatedType = editPersonDescriptor.getType().orElse(personToEdit.getType());
        String errorMessage = "";
        if (updatedType.isDoctor()) {
            if (editPersonDescriptor.getAge().isPresent()) {
                errorMessage += "There is no age field in doctor\n";
            }
            if (editPersonDescriptor.getBloodType().isPresent()) {
                errorMessage += "There is no blood type field in doctor\n";
            }
            if (editPersonDescriptor.getTissueType().isPresent()) {
                errorMessage += "There is no tissue type field in doctor\n";
            }
            if (editPersonDescriptor.getOrganExpiryDate().isPresent()) {
                errorMessage += "There is no organ's expiry date field in doctor\n";
            }
            if (editPersonDescriptor.getOrgan().isPresent()) {
                errorMessage += "There is no organ field in doctor\n";
            }
            if (editPersonDescriptor.getDoctorInCharge().isPresent()) {
                errorMessage += "There is no doctor in charge field in doctor\n";
            }
            if (editPersonDescriptor.getPriority().isPresent()) {
                errorMessage += "There is no priority field in doctor\n";
            }
        } else if (updatedType.isPatient()) {
            if (editPersonDescriptor.getOrganExpiryDate().isPresent()) {
                errorMessage += "There is no organ's expiry date field in patient\n";
            }
        } else if (updatedType.isDonor()) {
            if (editPersonDescriptor.getPriority().isPresent()) {
                errorMessage += "There is no priority field in donor\n";
            }
            if (editPersonDescriptor.getDoctorInCharge().isPresent()) {
                errorMessage += "There is no doctor in charge field in donor\n";
            }
        }
        return errorMessage;
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
        return nric.equals(e.nric)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Nric nric;
        private Type type;
        private Age age;
        private Priority priority;
        private BloodType bloodType;
        private DoctorInCharge doctorInCharge;
        private OrganExpiryDate organExpiryDate;
        private Organ organ;
        private TissueType tissueType;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.type = toCopy.type;
            this.nric = toCopy.nric;
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.age = toCopy.age;
            this.priority = toCopy.priority;
            this.bloodType = toCopy.bloodType;
            this.tissueType = toCopy.tissueType;
            this.organ = toCopy.organ;
            this.doctorInCharge = toCopy.doctorInCharge;
            this.organExpiryDate = toCopy.organExpiryDate;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(type, nric, name, phone, age, priority,
                    bloodType, doctorInCharge, organExpiryDate, organ, tissueType);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
        }

        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public Optional<BloodType> getBloodType() {
            return Optional.ofNullable(bloodType);
        }

        public Optional<DoctorInCharge> getDoctorInCharge() {
            return Optional.ofNullable(doctorInCharge);
        }

        public Optional<OrganExpiryDate> getOrganExpiryDate() {
            return Optional.ofNullable(organExpiryDate);
        }

        public Optional<Organ> getOrgan() {
            return Optional.ofNullable(organ);
        }

        public Optional<TissueType> getTissueType() {
            return Optional.ofNullable(tissueType);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setAge(Age age) {
            this.age = age;
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public void setBloodType(BloodType bloodType) {
            this.bloodType = bloodType;
        }

        public void setDoctorInCharge(DoctorInCharge doctorInCharge) {
            this.doctorInCharge = doctorInCharge;
        }

        public void setOrganExpiryDate(OrganExpiryDate organExpiryDate) {
            this.organExpiryDate = organExpiryDate;
        }

        public void setOrgan(Organ organ) {
            this.organ = organ;
        }

        public void setTissueType(TissueType tissueType) {
            this.tissueType = tissueType;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getType().equals(e.getType())
                    && getNric().equals(e.getNric())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getAge().equals(e.getAge())
                    && getPriority().equals(e.getPriority())
                    && getBloodType().equals(e.getBloodType())
                    && getTissueType().equals(e.getTissueType())
                    && getOrgan().equals(e.getOrgan())
                    && getDoctorInCharge().equals(e.getDoctorInCharge())
                    && getOrganExpiryDate().equals(e.getOrganExpiryDate());
        }
    }
}
