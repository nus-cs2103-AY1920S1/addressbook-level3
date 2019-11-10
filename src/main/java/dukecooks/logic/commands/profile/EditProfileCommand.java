package dukecooks.logic.commands.profile;

import static dukecooks.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DOB;
import static dukecooks.logic.parser.CliSyntax.PREFIX_GENDER;
import static dukecooks.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_MEDICALHISTORY;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.BloodType;
import dukecooks.model.profile.person.DoB;
import dukecooks.model.profile.person.Gender;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Person;
import dukecooks.model.profile.person.Weight;

/**
 * Edits the details of an existing person in Duke Cooks.
 */
public class EditProfileCommand extends EditCommand {

    public static final String VARIANT_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the user profile "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DOB + "DOB] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_BLOODTYPE + "BLOODTYPE] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "[" + PREFIX_HEIGHT + "HEIGHT] "
            + "[" + PREFIX_MEDICALHISTORY + "MEDICAL HISTORY]...\n"
            + "Example: " + COMMAND_WORD + " ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Profile: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_PROFILE_DOES_NOT_EXIST =
            "Profile does not exist! Start editing after you have created one!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the Duke Cooks.";

    private final EditPersonDescriptor editPersonDescriptor;

    private final boolean isWeightEdited;
    private final boolean isHeightEdited;

    /**
     * @param editPersonDescriptor details to edit the person with
     */
    public EditProfileCommand(EditPersonDescriptor editPersonDescriptor, boolean isWeightEdited,
                              boolean isHeightEdited) {
        requireNonNull(editPersonDescriptor);

        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.isWeightEdited = isWeightEdited;
        this.isHeightEdited = isHeightEdited;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_PROFILE_DOES_NOT_EXIST);
        }

        Person personToEdit = lastShownList.get(0);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        if (isWeightEdited || isHeightEdited) {
            LinkHealth.updateHealth(model, editedPerson, isWeightEdited, isHeightEdited);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Updates profile with the latest weight/height found in health records.
     * This method is only called to ensure profile's data is in sync with health records
     */
    public void updateProfile(Model model) {
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEdit = lastShownList.get(0);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        model.setPerson(personToEdit, editedPerson);
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
        Set<MedicalHistory> updatedMedicalHistories;
        if (editPersonDescriptor.getMedicalHistoriesToAdd().isPresent()
                || editPersonDescriptor.getMedicalHistoriesToRemove().isPresent()) {

            updatedMedicalHistories = new HashSet<>(personToEdit.getMedicalHistories());

            if (editPersonDescriptor.getMedicalHistoriesToAdd().isPresent()) {
                updatedMedicalHistories.addAll(editPersonDescriptor.getMedicalHistoriesToAdd().get());
            }

            if (editPersonDescriptor.getMedicalHistoriesToRemove().isPresent()) {
                updatedMedicalHistories.removeAll(editPersonDescriptor.getMedicalHistoriesToRemove().get());
            }

        } else {
            updatedMedicalHistories = personToEdit.getMedicalHistories();
        }

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
        private Set<MedicalHistory> medicalHistoriesToAdd;
        private Set<MedicalHistory> medicalHistoriesToRemove;

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
            setAddMedicalHistories(toCopy.medicalHistoriesToAdd);
            setRemoveMedicalHistories(toCopy.medicalHistoriesToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, dateOfBirth, bloodGroup, weight, height,
                    medicalHistoriesToAdd, medicalHistoriesToRemove);
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
         * Set {@code medicalHistories} to this object's {@code medicalHistoriesToAdd}.
         * A defensive copy of {@code medicalHistoriesToAdd} is used internally.
         */
        public void setAddMedicalHistories(Set<MedicalHistory> medicalHistories) {
            this.medicalHistoriesToAdd = (medicalHistories != null) ? new HashSet<>(medicalHistories) : null;
        }

        /**
         * Set {@code medicalHistories} to this object's {@code medicalHistoriesToRemove}.
         * A defensive copy of {@code medicalHistoriesToRemove} is used internally.
         */
        public void setRemoveMedicalHistories(Set<MedicalHistory> medicalHistories) {
            this.medicalHistoriesToRemove = (medicalHistories != null) ? new HashSet<>(medicalHistories) : null;
        }

        /**
         * Adds {@code medicalHistories} to this object's {@code medicalHistoriesToAdd}.
         * A defensive copy of {@code medicalHistories} is used internally.
         */
        public void addMedicalHistories(Set<MedicalHistory> medicalHistories) {
            this.medicalHistoriesToAdd = (medicalHistories != null) ? new HashSet<>(medicalHistories) : null;
        }

        /**
         * Removes {@code medicalHistories} to this object's {@code medicalHistories}.
         * A defensive copy of {@code medicalHistories} is used internally.
         */
        public void removeMedicalHistories(Set<MedicalHistory> medicalHistories) {
            this.medicalHistoriesToRemove = (medicalHistories != null) ? new HashSet<>(medicalHistories) : null;
        }

        /**
         * Returns an unmodifiable medicalHistory set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medicalHistories} is null.
         */
        public Optional<Set<MedicalHistory>> getMedicalHistoriesToAdd() {
            return (medicalHistoriesToAdd != null)
                    ? Optional.of(Collections.unmodifiableSet(medicalHistoriesToAdd))
                    : Optional.empty();
        }

        /**
         * Returns an unmodifiable medicalHistory set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medicalHistories} is null.
         */
        public Optional<Set<MedicalHistory>> getMedicalHistoriesToRemove() {
            return (medicalHistoriesToRemove != null)
                    ? Optional.of(Collections.unmodifiableSet(medicalHistoriesToRemove))
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
                    && getMedicalHistoriesToAdd().equals(e.getMedicalHistoriesToAdd())
                    && getMedicalHistoriesToRemove().equals(e.getMedicalHistoriesToRemove());
        }
    }
}
