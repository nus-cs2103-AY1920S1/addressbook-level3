package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICALHISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.profile.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.profile.Model;
import seedu.address.profile.medical.MedicalHistory;
import seedu.address.profile.person.BloodType;
import seedu.address.profile.person.DoB;
import seedu.address.profile.person.Gender;
import seedu.address.profile.person.Height;
import seedu.address.profile.person.Name;
import seedu.address.profile.person.Person;
import seedu.address.profile.person.Weight;

/**
 * Edits the details of an existing person in Duke Cooks.
 */
public class EditProfileCommand extends Command {

    public static final String COMMAND_WORD = "editProfile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the user profile "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DOB + "DOB] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_BLOODTYPE + "BLOODTYPE] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "[" + PREFIX_HEIGHT + "HEIGHT] "
            + "[" + PREFIX_MEDICALHISTORY + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Profile: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the Duke Cooks.";

    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param editPersonDescriptor details to edit the person with
     */
    public EditProfileCommand(EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(editPersonDescriptor);

        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit = lastShownList.get(0);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        DoB updatedDateOfBirth = editPersonDescriptor.getDateOfBirth().orElse(personToEdit.getDateOfBirth());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        BloodType updatedBloodType = editPersonDescriptor.getBloodType().orElse(personToEdit.getBloodType());
        Weight updatedWeight = editPersonDescriptor.getWeight().orElse(personToEdit.getWeight());
        Height updatedHeight = editPersonDescriptor.getHeight().orElse(personToEdit.getHeight());
        Set<MedicalHistory> updatedMedicalHistories = editPersonDescriptor.getMedicalHistories()
                .orElse(personToEdit.getMedicalHistories());

        return new Person(updatedName, updatedDateOfBirth, updatedGender, updatedBloodType,
                updatedWeight, updatedHeight, updatedMedicalHistories);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditProfileCommand)) {
            return false;
        }

        // state check
        EditProfileCommand e = (EditProfileCommand) other;
        return editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private DoB dateOfBirth;
        private Gender gender;
        private BloodType bloodGroup;
        private Weight weight;
        private Height height;
        private Set<MedicalHistory> medicalHistories;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code medicalHistories} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setDateOfBirth(toCopy.dateOfBirth);
            setGender(toCopy.gender);
            setBloodType(toCopy.bloodGroup);
            setWeight(toCopy.weight);
            setHeight(toCopy.height);
            setMedicalHistories(toCopy.medicalHistories);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, dateOfBirth, bloodGroup, weight, height, medicalHistories);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDateOfBirth(DoB dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Optional<DoB> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setBloodType(BloodType bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public Optional<BloodType> getBloodType() {
            return Optional.ofNullable(bloodGroup);
        }

        public void setWeight(Weight weight) {
            this.weight = weight;
        }

        public Optional<Weight> getWeight() {
            return Optional.ofNullable(weight);
        }

        public void setHeight(Height height) {
            this.height = height;
        }

        public Optional<Height> getHeight() {
            return Optional.ofNullable(height);
        }

        /**
         * Sets {@code medicalHistories} to this object's {@code medicalHistories}.
         * A defensive copy of {@code medicalHistories} is used internally.
         */
        public void setMedicalHistories(Set<MedicalHistory> medicalHistories) {
            this.medicalHistories = (medicalHistories != null) ? new HashSet<>(medicalHistories) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medicalHistories} is null.
         */
        public Optional<Set<MedicalHistory>> getMedicalHistories() {
            return (medicalHistories != null) ? Optional.of(Collections.unmodifiableSet(medicalHistories))
                    : Optional.empty();
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

            return getName().equals(e.getName())
                    && getDateOfBirth().equals(e.getDateOfBirth())
                    && getGender().equals(e.getGender())
                    && getBloodType().equals(e.getBloodType())
                    && getWeight().equals(e.getWeight())
                    && getHeight().equals(e.getHeight())
                    && getMedicalHistories().equals(e.getMedicalHistories());
        }
    }
}
