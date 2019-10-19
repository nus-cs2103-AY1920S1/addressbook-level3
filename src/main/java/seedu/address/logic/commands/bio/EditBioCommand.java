package seedu.address.logic.commands.bio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_SUBARGUMENT_INDEX_OUT_OF_BOUNDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OTHER_BIO_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROFILE_DESC;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_USERS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bio.Address;
import seedu.address.model.bio.DateOfBirth;
import seedu.address.model.bio.Gender;
import seedu.address.model.bio.Goal;
import seedu.address.model.bio.ListableField;
import seedu.address.model.bio.MedicalCondition;
import seedu.address.model.bio.Name;
import seedu.address.model.bio.Nric;
import seedu.address.model.bio.OtherBioInfo;
import seedu.address.model.bio.Phone;
import seedu.address.model.bio.ProfileDesc;
import seedu.address.model.bio.User;
import seedu.address.ui.DisplayPaneType;

/**
 * Edits the details of an existing user in the address book.
 */
public class EditBioCommand extends Command {

    public static final String COMMAND_WORD = "editbio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the user identified "
            + "by the index number used in the displayed user list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PROFILE_DESC + "PROFILE DESCRIPTION] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH] "
            + "[" + PREFIX_CONTACT_NUMBER + "[INDEX/]CONTACT NUMBER]... "
            + "[" + PREFIX_EMERGENCY_CONTACT + "[INDEX/]EMERGENCY CONTACT]... "
            + "[" + PREFIX_MEDICAL_CONDITION + "[INDEX/]MEDICAL CONDITION]... "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_GOALS + "[INDEX/]GOALS]... "
            + "[" + PREFIX_OTHER_BIO_INFO + "OTHER INFO]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT_NUMBER + "91234567 "
            + PREFIX_MEDICAL_CONDITION + "Type I diabetes";

    public static final String MESSAGE_EDIT_USER_SUCCESS = "Edited User: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_BIOGRAPHY_DOES_NOT_EXIST = "Oops! Biography does not exist!"
            + " Try using the [" + AddBioCommand.COMMAND_WORD + "] command to add a new biography.";
    public static final String MESSAGE_DUPLICATE_USER = "his user already exists in the address book.";

    private final EditUserDescriptor editUserDescriptor;

    /**
     * @param editUserDescriptor details to edit the user with
     */
    public EditBioCommand(EditUserDescriptor editUserDescriptor) {
        requireNonNull(editUserDescriptor);
        this.editUserDescriptor = new EditUserDescriptor(editUserDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<User> lastShownList = model.getFilteredUserList();

        try {
            User userToEdit = lastShownList.get(0);
            User editedUser = createEditedUser(userToEdit, editUserDescriptor);

            if (!userToEdit.isSameUser(editedUser) && model.hasUser(editedUser)) {
                throw new CommandException(MESSAGE_DUPLICATE_USER);
            }

            model.setUser(userToEdit, editedUser);
            model.updateFilteredUserList(PREDICATE_SHOW_ALL_USERS);
            return new CommandResult(String.format(MESSAGE_EDIT_USER_SUCCESS, editedUser));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_BIOGRAPHY_DOES_NOT_EXIST);
        }
    }

    /**
     * Creates and returns a {@code User} with the details of {@code userToEdit}
     * edited with {@code editUserDescriptor}.
     */
    private static User createEditedUser(User userToEdit, EditUserDescriptor editUserDescriptor)
            throws CommandException {
        assert userToEdit != null;

        Name updatedName = editUserDescriptor.getName().orElse(userToEdit.getName());
        ProfileDesc updatedProfileDesc = editUserDescriptor.getProfileDesc().orElse(userToEdit.getProfileDesc());
        Nric updatedNric = editUserDescriptor.getNric().orElse(userToEdit.getNric());
        Gender updatedGender = editUserDescriptor.getGender().orElse(userToEdit.getGender());
        DateOfBirth updatedDateOfBirth = editUserDescriptor.getDateOfBirth().orElse(userToEdit.getDateOfBirth());


        List<Phone> updatedContactNumbers = updateListableFields(userToEdit.getContactNumbers(),
                editUserDescriptor.getContactNumbers(), editUserDescriptor.getIndividualContactNumbersMap()).stream()
                .map(Phone.class::cast).collect(Collectors.toList());

        List<Phone> updatedEmergencyContacts = updateListableFields(userToEdit.getEmergencyContacts(),
                editUserDescriptor.getEmergencyContacts(),
                editUserDescriptor.getIndividualEmergencyContactsMap()).stream()
                .map(Phone.class::cast).collect(Collectors.toList());

        List<Goal> updatedGoals = updateListableFields(userToEdit.getGoals(),
                editUserDescriptor.getGoals(), editUserDescriptor.getIndividualGoalsMap()).stream()
                .map(Goal.class::cast).collect(Collectors.toList());

        Address updatedAddress = editUserDescriptor.getAddress().orElse(userToEdit.getAddress());

        List<MedicalCondition> updatedMedicalConditions = updateListableFields(userToEdit.getMedicalConditions(),
                editUserDescriptor.getMedicalConditions(), editUserDescriptor.getIndividualMedicalConditionsMap())
                .stream().map(MedicalCondition.class::cast).collect(Collectors.toList());

        OtherBioInfo updatedOtherBioInfo = editUserDescriptor.getOtherBioInfo().orElse(userToEdit.getOtherBioInfo());

        return new User(updatedName, updatedProfileDesc, updatedNric, updatedGender, updatedDateOfBirth,
                updatedContactNumbers, updatedEmergencyContacts, updatedMedicalConditions, updatedAddress,
                updatedGoals, updatedOtherBioInfo);
    }

    /**
     * Returns a List of ListableFields or it's subclasses to be updated if applicable, or otherwise return null.
     * @param userToEditListableFields List of ListableFields of the user to be edited.
     * @param listableFieldsOptional Optional ListableFields of an editUserDescriptor object.
     * @param individualListableFieldsMapOptional Optional individualListableFieldsMap of an editUserDescriptor
     *                                            object that maps indices to ListableFields.
     * @return A List of ListableFields or it's subclasses to be updated if applicable, or otherwise return null.
     * @throws CommandException If the given one-based index / indices is / are out of bounds.
     */
    private static List<? extends ListableField> updateListableFields(
            List<? extends ListableField> userToEditListableFields,
            Optional<? extends List<? extends ListableField>> listableFieldsOptional,
            Optional<? extends List<? extends HashMap<Index, ? extends ListableField>>>
                    individualListableFieldsMapOptional) throws CommandException {
        List<? extends ListableField> updatedListableFields = listableFieldsOptional.orElse(null);
        if (updatedListableFields == null) {
            if (updatedListableFields == null) {
                if (individualListableFieldsMapOptional.isPresent()) {
                    List<? extends HashMap<Index, ? extends ListableField>> individualListableFieldsMap =
                            individualListableFieldsMapOptional.get();
                    List<ListableField> listableFieldsCopy = new ArrayList<>(userToEditListableFields);
                    for (HashMap<Index, ? extends ListableField> map : individualListableFieldsMap) {
                        Index index = map.keySet().iterator().next();
                        try {
                            listableFieldsCopy.set(index.getZeroBased(), map.get(index));
                        } catch (IndexOutOfBoundsException e) {
                            throw new CommandException(MESSAGE_SUBARGUMENT_INDEX_OUT_OF_BOUNDS);
                        }
                    }
                    updatedListableFields = listableFieldsCopy;
                } else {
                    updatedListableFields = userToEditListableFields;
                }
            }
        }
        return updatedListableFields;
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBioCommand)) {
            return false;
        }

        // state check
        EditBioCommand e = (EditBioCommand) other;
        return editUserDescriptor.equals(e.editUserDescriptor);
    }

    /**
     * Stores the details to edit the user with. Each non-empty field value will replace the
     * corresponding field value of the user.
     */
    public static class EditUserDescriptor {
        private Name name;
        private ProfileDesc profileDesc;
        private Nric nric;
        private Gender gender;
        private DateOfBirth dateOfBirth;
        private List<Phone> contactNumbers;
        private List<HashMap<Index, Phone>> individualContactNumbersMap;
        private List<Phone> emergencyContacts;
        private List<HashMap<Index, Phone>> individualEmergencyContactsMap;
        private List<MedicalCondition> medicalConditions;
        private List<HashMap<Index, MedicalCondition>> individualMedicalConditionsMap;
        private Address address;
        private List<Goal> goals;
        private List<HashMap<Index, Goal>> individualGoalsMap;
        private OtherBioInfo otherBioInfo;

        public EditUserDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditUserDescriptor(EditUserDescriptor toCopy) {
            setName(toCopy.name);
            setProfileDesc(toCopy.profileDesc);
            setNric(toCopy.nric);
            setGender(toCopy.gender);
            setDateOfBirth(toCopy.dateOfBirth);
            setContactNumbers(toCopy.contactNumbers);
            setIndividualContactNumbersMap(toCopy.individualContactNumbersMap);
            setEmergencyContacts(toCopy.emergencyContacts);
            setIndividualEmergencyContactsMap(toCopy.individualEmergencyContactsMap);
            setMedicalConditions(toCopy.medicalConditions);
            setIndividualMedicalConditionsMap(toCopy.individualMedicalConditionsMap);
            setAddress(toCopy.address);
            setGoals(toCopy.goals);
            setIndividualGoalsMap(toCopy.individualGoalsMap);
            setOtherBioInfo(toCopy.otherBioInfo);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, profileDesc, nric, gender, dateOfBirth,
                    contactNumbers, individualContactNumbersMap,
                    emergencyContacts, individualEmergencyContactsMap,
                    medicalConditions, individualMedicalConditionsMap,
                    address, goals, individualGoalsMap, otherBioInfo);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setProfileDesc(ProfileDesc profileDesc) {
            this.profileDesc = profileDesc;
        }

        public Optional<ProfileDesc> getProfileDesc() {
            return Optional.ofNullable(profileDesc);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        /**
         * Adds {@code hashMap} to the this object's {@code mapIndividualContactNumbers}.
         */
        public void addToIndividualContactNumberEdit(HashMap<Index, Phone> hashMap) {
            if (this.individualContactNumbersMap == null) {
                this.individualContactNumbersMap = new ArrayList<>();
            }
            this.individualContactNumbersMap.add(hashMap);
        }

        /**
         * Sets {@code contactNumbers} to this object's {@code contactNumbers}.
         * A defensive copy of {@code contactNumbers} is used internally.
         */
        public void setContactNumbers(List<Phone> contactNumbers) {
            this.contactNumbers = (contactNumbers != null) ? new ArrayList<>(contactNumbers) : null;
        }

        /**
         * Sets {@code individualContactNumbersMaps} to this object's {@code individualContactNumbersMaps}.
         * A defensive copy of {@code individualContactNumbersMaps} is used internally.
         */
        public void setIndividualContactNumbersMap(List<HashMap<Index, Phone>> individualContactNumbersMap) {
            this.individualContactNumbersMap = (individualContactNumbersMap != null)
                    ? new ArrayList<>(individualContactNumbersMap)
                    : null;
        }

        /**
         * Returns an unmodifiable contactNumber set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code contactNumbers} is null.
         */
        public Optional<List<Phone>> getContactNumbers() {
            return (contactNumbers != null)
                    ? Optional.of(Collections.unmodifiableList(contactNumbers))
                    : Optional.empty();
        }

        /**
         * Returns an unmodifiable individualContactNumbersMap set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code individualContactNumbersMap} is null.
         */
        public Optional<List<HashMap<Index, Phone>>> getIndividualContactNumbersMap() {
            return (individualContactNumbersMap != null)
                    ? Optional.of(individualContactNumbersMap)
                    : Optional.empty();
        }

        /**
         * Adds {@code hashMap} to the this object's {@code mapIndividualEmergencyContacts}.
         */
        public void addToIndividualEmergencyContactsEdit(HashMap<Index, Phone> hashMap) {
            if (this.individualEmergencyContactsMap == null) {
                this.individualEmergencyContactsMap = new ArrayList<>();
            }
            this.individualEmergencyContactsMap.add(hashMap);
        }

        /**
         * Sets {@code emergencyContacts} to this object's {@code emergencyContacts}.
         * A defensive copy of {@code emergencyContacts} is used internally.
         */
        public void setEmergencyContacts(List<Phone> emergencyContacts) {
            this.emergencyContacts = (emergencyContacts != null)
                    ? new ArrayList<>(emergencyContacts)
                    : null;
        }

        /**
         * Sets {@code individualEmergencyContactsMaps} to this object's {@code individualEmergencyContactsMaps}.
         * A defensive copy of {@code individualEmergencyContactsMaps} is used internally.
         */
        public void setIndividualEmergencyContactsMap(List<HashMap<Index, Phone>> individualEmergencyContactsMap) {
            this.individualEmergencyContactsMap = (individualEmergencyContactsMap != null)
                    ? new ArrayList<>(individualEmergencyContactsMap) : null;
        }

        /**
         * Returns an unmodifiable emergencyContact set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code emergencyContacts} is null.
         */
        public Optional<List<Phone>> getEmergencyContacts() {
            return (emergencyContacts != null)
                    ? Optional.of(Collections.unmodifiableList(emergencyContacts))
                    : Optional.empty();
        }

        /**
         * Returns an unmodifiable individualEmergencyContactsMap set, which throws
         * {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code individualEmergencyContactsMap} is null.
         */
        public Optional<List<HashMap<Index, Phone>>> getIndividualEmergencyContactsMap() {
            return (individualEmergencyContactsMap != null)
                    ? Optional.of(individualEmergencyContactsMap) : Optional.empty();
        }

        /**
         * Adds {@code hashMap} to the this object's {@code individualMedicalConditionsMap}.
         */
        public void addToIndividualMedicalConditionsEdit(HashMap<Index, MedicalCondition> hashMap) {
            if (this.individualMedicalConditionsMap == null) {
                this.individualMedicalConditionsMap = new ArrayList<>();
            }
            this.individualMedicalConditionsMap.add(hashMap);
        }

        /**
         * Sets {@code medicalConditions} to this object's {@code medicalConditions}.
         * A defensive copy of {@code medicalConditions} is used internally.
         */
        public void setMedicalConditions(List<MedicalCondition> medicalConditions) {
            this.medicalConditions = (medicalConditions != null)
                    ? new ArrayList<>(medicalConditions)
                    : null;
        }

        /**
         * Sets {@code individualGoalsMaps} to this object's {@code individualGoalsMaps}.
         * A defensive copy of {@code individualGoalsMaps} is used internally.
         */
        public void setIndividualGoalsMap(List<HashMap<Index, Goal>> individualGoalsMap) {
            this.individualGoalsMap = (individualGoalsMap != null)
                    ? new ArrayList<>(individualGoalsMap)
                    : null;
        }

        /**
         * Returns an unmodifiable medicalCondition set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medicalConditions} is null.
         */
        public Optional<List<MedicalCondition>> getMedicalConditions() {
            return (medicalConditions != null)
                    ? Optional.of(Collections.unmodifiableList(medicalConditions))
                    : Optional.empty();
        }

        /**
         * Returns an unmodifiable individualMedicalConditionsMap set, which throws
         * {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code individualMedicalConditionsMap} is null.
         */
        public Optional<List<HashMap<Index, MedicalCondition>>> getIndividualMedicalConditionsMap() {
            return (individualMedicalConditionsMap != null)
                    ? Optional.of(individualMedicalConditionsMap)
                    : Optional.empty();
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Adds {@code hashMap} to the this object's {@code individualGoalsMap}.
         */
        public void addToIndividualGoalsEdit(HashMap<Index, Goal> hashMap) {
            if (this.individualGoalsMap == null) {
                this.individualGoalsMap = new ArrayList<>();
            }
            this.individualGoalsMap.add(hashMap);
        }

        /**
         * Sets {@code goals} to this object's {@code goals}.
         * A defensive copy of {@code goals} is used internally.
         */
        public void setGoals(List<Goal> goals) {
            this.goals = (goals != null) ? new ArrayList<>(goals) : null;
        }

        /**
         * Sets {@code individualMedicalConditionsMaps} to this object's {@code individualMedicalConditionsMaps}.
         * A defensive copy of {@code individualMedicalConditionsMaps} is used internally.
         */
        public void setIndividualMedicalConditionsMap(List<HashMap<Index, MedicalCondition>>
                                                              individualMedicalConditionsMap) {
            this.individualMedicalConditionsMap = (individualMedicalConditionsMap != null)
                    ? new ArrayList<>(individualMedicalConditionsMap)
                    : null;
        }

        /**
         * Returns an unmodifiable goal set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code goals} is null.
         */
        public Optional<List<Goal>> getGoals() {
            return (goals != null) ? Optional.of(Collections.unmodifiableList(goals)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable individualGoalsMap set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code individualGoalsMap} is null.
         */
        public Optional<List<HashMap<Index, Goal>>> getIndividualGoalsMap() {
            return (individualGoalsMap != null)
                    ? Optional.of(individualGoalsMap)
                    : Optional.empty();
        }

        public void setOtherBioInfo(OtherBioInfo otherBioInfo) {
            this.otherBioInfo = otherBioInfo;
        }

        public Optional<OtherBioInfo> getOtherBioInfo() {
            return Optional.ofNullable(otherBioInfo);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditUserDescriptor)) {
                return false;
            }

            // state check
            EditUserDescriptor e = (EditUserDescriptor) other;

            return getName().equals(e.getName())
                    && getNric().equals(e.getNric())
                    && getProfileDesc().equals(e.getProfileDesc())
                    && getGender().equals(e.getGender())
                    && getDateOfBirth().equals(e.getDateOfBirth())
                    && getContactNumbers().equals(e.getContactNumbers())
                    && getEmergencyContacts().equals(e.getEmergencyContacts())
                    && getMedicalConditions().equals(e.getMedicalConditions())
                    && getAddress().equals(e.getAddress())
                    && getGoals().equals(e.getGoals())
                    && getOtherBioInfo().equals(e.getOtherBioInfo());
        }
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.BIO;
    }

    @Override
    public boolean getNewPaneToBeCreated() {
        return true;
    }
}
