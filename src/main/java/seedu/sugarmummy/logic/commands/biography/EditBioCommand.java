package seedu.sugarmummy.logic.commands.biography;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_SUBARGUMENT_INDEX_OUT_OF_BOUNDS;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CONTACT_NUMBER;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_DP_PATH;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_GOALS;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_MEDICAL_CONDITION;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_OTHER_BIO_INFO;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_PROFILE_DESC;
import static seedu.sugarmummy.model.Model.PREDICATE_SHOW_ALL_USERS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.sugarmummy.commons.core.index.Index;
import seedu.sugarmummy.commons.util.CollectionUtil;
import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.biography.Address;
import seedu.sugarmummy.model.biography.DateOfBirth;
import seedu.sugarmummy.model.biography.DisplayPicPath;
import seedu.sugarmummy.model.biography.Gender;
import seedu.sugarmummy.model.biography.Goal;
import seedu.sugarmummy.model.biography.ListableField;
import seedu.sugarmummy.model.biography.MedicalCondition;
import seedu.sugarmummy.model.biography.Name;
import seedu.sugarmummy.model.biography.Nric;
import seedu.sugarmummy.model.biography.OtherBioInfo;
import seedu.sugarmummy.model.biography.Phone;
import seedu.sugarmummy.model.biography.ProfileDesc;
import seedu.sugarmummy.model.biography.User;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Edits the biography of the user.
 */
public class EditBioCommand extends Command {

    public static final String COMMAND_WORD = "editbio";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_WORD + ": Edits the user's biography "
            + "either by overwriting all existing values, OR by specifying positive indexes for individual values for "
            + "fields that can hold multiple values. Fields that can hold multiple values are limited to "
            + "contact numbers, emergency contacts, medical conditions and goals.\n"
            + "Note that Name, contact number(s), emergency contact(s) and medical condition(s) cannot be empty.\n\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DP_PATH + "DP PATH] "
            + "[" + PREFIX_PROFILE_DESC + "PROFILE DESCRIPTION] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH] "
            + "[" + PREFIX_CONTACT_NUMBER + "[INDEX/]CONTACT NUMBER]... "
            + "[" + PREFIX_EMERGENCY_CONTACT + "[INDEX/]EMERGENCY CONTACT]... "
            + "[" + PREFIX_MEDICAL_CONDITION + "[INDEX/]MEDICAL CONDITION]... "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_GOALS + "[INDEX/]GOALS]... "
            + "[" + PREFIX_OTHER_BIO_INFO + "OTHER INFO]\n\n"
            + "Example: " + COMMAND_WORD + PREFIX_PROFILE_DESC + "The world has changed, just like my "
            + "profile description has. "
            + PREFIX_CONTACT_NUMBER + "91234567 " + PREFIX_CONTACT_NUMBER + "98765432 "
            + PREFIX_MEDICAL_CONDITION + "1/Type I diabetes " + PREFIX_MEDICAL_CONDITION + "2/High Blood Pressure";

    public static final String MESSAGE_EDIT_USER_SUCCESS = "I've successfully edited your bio! %1$s";
    public static final String MESSAGE_CHANGES_MADE = "The following changes were made.\n\n%1$s";
    public static final String MESSAGE_NO_CHANGE = "The information that you've keyed in are no different from "
            + "what already exists in your current biography! As such, there's nothing for me to update =)";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_BIOGRAPHY_DOES_NOT_EXIST = "Oops! Biography does not exist!"
            + " Try using the [" + AddBioCommand.COMMAND_WORD + "] command to add a new biography.";

    private final EditUserDescriptor editUserDescriptor;

    /**
     * @param editUserDescriptor details to edit the user with
     */
    public EditBioCommand(EditUserDescriptor editUserDescriptor) {
        requireNonNull(editUserDescriptor);
        this.editUserDescriptor = new EditUserDescriptor(editUserDescriptor);
    }

    /**
     * Creates and returns a {@code User} with the details of {@code userToEdit} edited with {@code
     * editUserDescriptor}.
     */
    private static User createEditedUser(User userToEdit, EditUserDescriptor editUserDescriptor)
            throws CommandException {
        assert userToEdit != null;

        Name updatedName = editUserDescriptor.getName().orElse(userToEdit.getName());
        DisplayPicPath udpatedDpPath = editUserDescriptor.getDpPath().orElse(userToEdit.getDpPath());
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

        return new User(updatedName, udpatedDpPath, updatedProfileDesc, updatedNric, updatedGender, updatedDateOfBirth,
                updatedContactNumbers, updatedEmergencyContacts, updatedMedicalConditions, updatedAddress,
                updatedGoals, updatedOtherBioInfo);
    }

    /**
     * Returns a List of ListableFields or it's subclasses to be updated if applicable, or otherwise return null.
     *
     * @param userToEditListableFields            List of ListableFields of the user to be edited.
     * @param listableFieldsOptional              Optional ListableFields of an editUserDescriptor object.
     * @param individualListableFieldsMapOptional Optional individualListableFieldsMap of an editUserDescriptor object
     *                                            that maps indices to ListableFields.
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
        return updatedListableFields;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<User> lastShownList = model.getFilteredUserList();

        if (!lastShownList.isEmpty()) {
            User userToEdit = lastShownList.get(0);
            User editedUser = createEditedUser(userToEdit, editUserDescriptor);

            model.setUser(userToEdit, editedUser);
            model.updateFilteredUserList(PREDICATE_SHOW_ALL_USERS);

            StringBuilder editedFields = new StringBuilder();
            HashMap<String, List<String>> changedDifferences = userToEdit.getDifferencesTo(editedUser);

            changedDifferences.forEach((key, beforeAndAfter) -> {
                String before = beforeAndAfter.get(0);
                String after = beforeAndAfter.get(1);
                editedFields.append("- ");

                if (before.isEmpty()) {
                    editedFields.append("Added to ").append(key).append(": ").append(after);
                } else if (after.isEmpty() || after.equals("[]")) {
                    editedFields.append("Deleted from ").append(key).append(": ").append(before);
                } else {
                    editedFields.append("Modified ").append(key)
                            .append(": from ").append(before).append(" to ").append(after);
                }
                editedFields.append("\n");
            });

            assert changedDifferences.size() == 0 || !editedFields.toString().isEmpty()
                    : "Edited fields in user feedback cannot be empty.";

            return new CommandResult(changedDifferences.size() == 0
                    ? MESSAGE_NO_CHANGE
                    : String.format(MESSAGE_EDIT_USER_SUCCESS,
                            String.format(MESSAGE_CHANGES_MADE, editedFields.toString().trim())));
        } else {
            throw new CommandException(MESSAGE_BIOGRAPHY_DOES_NOT_EXIST);
        }

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

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.BIO;
    }

    @Override
    public boolean isToCreateNewPane() {
        return true;
    }

    /**
     * Stores the details to edit the user with. Each non-empty field value will replace the corresponding field value
     * of the user.
     */
    public static class EditUserDescriptor {
        private Name name;
        private DisplayPicPath dpPath;
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

        public EditUserDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditUserDescriptor(EditUserDescriptor toCopy) {
            setName(toCopy.name);
            setDpPath(toCopy.dpPath);
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
            return CollectionUtil.isAnyNonNull(name, dpPath, profileDesc, nric, gender, dateOfBirth,
                    contactNumbers, individualContactNumbersMap,
                    emergencyContacts, individualEmergencyContactsMap,
                    medicalConditions, individualMedicalConditionsMap,
                    address, goals, individualGoalsMap, otherBioInfo);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<DisplayPicPath> getDpPath() {
            return Optional.ofNullable(dpPath);
        }

        public void setDpPath(DisplayPicPath dpPath) {
            this.dpPath = dpPath;
        }

        public Optional<ProfileDesc> getProfileDesc() {
            return Optional.ofNullable(profileDesc);
        }

        public void setProfileDesc(ProfileDesc profileDesc) {
            this.profileDesc = profileDesc;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
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
         * Returns an unmodifiable contactNumber set, which throws {@code UnsupportedOperationException} if modification
         * is attempted. Returns {@code Optional#empty()} if {@code contactNumbers} is null.
         */
        public Optional<List<Phone>> getContactNumbers() {
            return (contactNumbers != null)
                    ? Optional.of(Collections.unmodifiableList(contactNumbers))
                    : Optional.empty();
        }

        /**
         * Sets {@code contactNumbers} to this object's {@code contactNumbers}. A defensive copy of {@code
         * contactNumbers} is used internally.
         */
        public void setContactNumbers(List<Phone> contactNumbers) {
            this.contactNumbers = (contactNumbers != null) ? new ArrayList<>(contactNumbers) : null;
        }

        /**
         * Returns an unmodifiable individualContactNumbersMap set, which throws {@code UnsupportedOperationException}
         * if modification is attempted. Returns {@code Optional#empty()} if {@code individualContactNumbersMap} is
         * null.
         */
        public Optional<List<HashMap<Index, Phone>>> getIndividualContactNumbersMap() {
            return (individualContactNumbersMap != null)
                    ? Optional.of(individualContactNumbersMap)
                    : Optional.empty();
        }

        /**
         * Sets {@code individualContactNumbersMaps} to this object's {@code individualContactNumbersMaps}. A defensive
         * copy of {@code individualContactNumbersMaps} is used internally.
         */
        public void setIndividualContactNumbersMap(List<HashMap<Index, Phone>> individualContactNumbersMap) {
            this.individualContactNumbersMap = (individualContactNumbersMap != null)
                    ? new ArrayList<>(individualContactNumbersMap)
                    : null;
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
         * Returns an unmodifiable emergencyContact set, which throws {@code UnsupportedOperationException} if
         * modification is attempted. Returns {@code Optional#empty()} if {@code emergencyContacts} is null.
         */
        public Optional<List<Phone>> getEmergencyContacts() {
            return (emergencyContacts != null)
                    ? Optional.of(Collections.unmodifiableList(emergencyContacts))
                    : Optional.empty();
        }

        /**
         * Sets {@code emergencyContacts} to this object's {@code emergencyContacts}. A defensive copy of {@code
         * emergencyContacts} is used internally.
         */
        public void setEmergencyContacts(List<Phone> emergencyContacts) {
            this.emergencyContacts = (emergencyContacts != null)
                    ? new ArrayList<>(emergencyContacts)
                    : null;
        }

        /**
         * Returns an unmodifiable individualEmergencyContactsMap set, which throws {@code
         * UnsupportedOperationException} if modification is attempted. Returns {@code Optional#empty()} if {@code
         * individualEmergencyContactsMap} is null.
         */
        public Optional<List<HashMap<Index, Phone>>> getIndividualEmergencyContactsMap() {
            return (individualEmergencyContactsMap != null)
                    ? Optional.of(individualEmergencyContactsMap) : Optional.empty();
        }

        /**
         * Sets {@code individualEmergencyContactsMaps} to this object's {@code individualEmergencyContactsMaps}. A
         * defensive copy of {@code individualEmergencyContactsMaps} is used internally.
         */
        public void setIndividualEmergencyContactsMap(List<HashMap<Index, Phone>> individualEmergencyContactsMap) {
            this.individualEmergencyContactsMap = (individualEmergencyContactsMap != null)
                    ? new ArrayList<>(individualEmergencyContactsMap) : null;
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
         * Returns an unmodifiable medicalCondition set, which throws {@code UnsupportedOperationException} if
         * modification is attempted. Returns {@code Optional#empty()} if {@code medicalConditions} is null.
         */
        public Optional<List<MedicalCondition>> getMedicalConditions() {
            return (medicalConditions != null)
                    ? Optional.of(Collections.unmodifiableList(medicalConditions))
                    : Optional.empty();
        }

        /**
         * Sets {@code medicalConditions} to this object's {@code medicalConditions}. A defensive copy of {@code
         * medicalConditions} is used internally.
         */
        public void setMedicalConditions(List<MedicalCondition> medicalConditions) {
            this.medicalConditions = (medicalConditions != null)
                    ? new ArrayList<>(medicalConditions)
                    : null;
        }

        /**
         * Returns an unmodifiable individualMedicalConditionsMap set, which throws {@code
         * UnsupportedOperationException} if modification is attempted. Returns {@code Optional#empty()} if {@code
         * individualMedicalConditionsMap} is null.
         */
        public Optional<List<HashMap<Index, MedicalCondition>>> getIndividualMedicalConditionsMap() {
            return (individualMedicalConditionsMap != null)
                    ? Optional.of(individualMedicalConditionsMap)
                    : Optional.empty();
        }

        /**
         * Sets {@code individualMedicalConditionsMaps} to this object's {@code individualMedicalConditionsMaps}. A
         * defensive copy of {@code individualMedicalConditionsMaps} is used internally.
         */
        public void setIndividualMedicalConditionsMap(List<HashMap<Index, MedicalCondition>>
                                                              individualMedicalConditionsMap) {
            this.individualMedicalConditionsMap = (individualMedicalConditionsMap != null)
                    ? new ArrayList<>(individualMedicalConditionsMap)
                    : null;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
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
         * Returns an unmodifiable goal set, which throws {@code UnsupportedOperationException} if modification is
         * attempted. Returns {@code Optional#empty()} if {@code goals} is null.
         */
        public Optional<List<Goal>> getGoals() {
            return (goals != null) ? Optional.of(Collections.unmodifiableList(goals)) : Optional.empty();
        }

        /**
         * Sets {@code goals} to this object's {@code goals}. A defensive copy of {@code goals} is used internally.
         */
        public void setGoals(List<Goal> goals) {
            this.goals = (goals != null) ? new ArrayList<>(goals) : null;
        }

        /**
         * Returns an unmodifiable individualGoalsMap set, which throws {@code UnsupportedOperationException} if
         * modification is attempted. Returns {@code Optional#empty()} if {@code individualGoalsMap} is null.
         */
        public Optional<List<HashMap<Index, Goal>>> getIndividualGoalsMap() {
            return (individualGoalsMap != null)
                    ? Optional.of(individualGoalsMap)
                    : Optional.empty();
        }

        /**
         * Sets {@code individualGoalsMaps} to this object's {@code individualGoalsMaps}. A defensive copy of {@code
         * individualGoalsMaps} is used internally.
         */
        public void setIndividualGoalsMap(List<HashMap<Index, Goal>> individualGoalsMap) {
            this.individualGoalsMap = (individualGoalsMap != null)
                    ? new ArrayList<>(individualGoalsMap)
                    : null;
        }

        public Optional<OtherBioInfo> getOtherBioInfo() {
            return Optional.ofNullable(otherBioInfo);
        }

        public void setOtherBioInfo(OtherBioInfo otherBioInfo) {
            this.otherBioInfo = otherBioInfo;
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
                    && getDpPath().equals(e.getDpPath())
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

}
