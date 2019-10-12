package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the title of an existing studyPlan in the module planner.
 */
public class EditTitleCommand extends Command {

    // TODO: implement this entire class

    public static final String COMMAND_WORD = "title";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Edits the title of the study plan "
            + "Parameters: "
            + "PLAN_TITLE \n"
            + "Example: "
            + "title Algo and Graphics";
    /*
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the studyPlan identified "
            + "by the index number used in the displayed studyPlan list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_STUDYPLAN_SUCCESS = "Edited StudyPlan: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDYPLAN = "This studyPlan already exists in the module planner.";

    private final Index index;
    private final EditStudyPlanDescriptor editStudyPlanDescriptor;
     */

    /**
     * @param //index of the studyPlan in the filtered studyPlan list to edit
     * @param //editStudyPlanDescriptor details to edit the studyPlan with
     */
    /*
    public EditCommand(Index index, EditStudyPlanDescriptor editStudyPlanDescriptor) {
        requireNonNull(index);
        requireNonNull(editStudyPlanDescriptor);

        this.index = index;
        this.editStudyPlanDescriptor = new EditStudyPlanDescriptor(editStudyPlanDescriptor);
    }
    */

    @Override
    public CommandResult execute(Model model) throws CommandException {
        /*
        requireNonNull(model);
        List<StudyPlan> lastShownList = model.getFilteredStudyPlanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDYPLAN_DISPLAYED_INDEX);
        }

        StudyPlan studyPlanToEdit = lastShownList.get(index.getZeroBased());
        StudyPlan editedStudyPlan = createEditedStudyPlan(studyPlanToEdit, editStudyPlanDescriptor);

        if (!studyPlanToEdit.isSameStudyPlan(editedStudyPlan) && model.hasStudyPlan(editedStudyPlan)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDYPLAN);
        }

        model.setStudyPlan(studyPlanToEdit, editedStudyPlan);
        model.updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDYPLANS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDYPLAN_SUCCESS, editedStudyPlan));
         */
        return new CommandResult("this is just a temporary holder");
    }

    /**
     * Creates and returns a {@code StudyPlan} with the details of {@code studyPlanToEdit}
     * edited with {@code editStudyPlanDescriptor}.
     */
    /*
    private static StudyPlan createEditedStudyPlan(StudyPlan studyPlanToEdit,
                                                   EditStudyPlanDescriptor editStudyPlanDescriptor) {
        assert studyPlanToEdit != null;

        Name updatedName = editStudyPlanDescriptor.getName().orElse(studyPlanToEdit.getName());
        Phone updatedPhone = editStudyPlanDescriptor.getPhone().orElse(studyPlanToEdit.getPhone());
        Email updatedEmail = editStudyPlanDescriptor.getEmail().orElse(studyPlanToEdit.getEmail());
        Address updatedAddress = editStudyPlanDescriptor.getAddress().orElse(studyPlanToEdit.getAddress());
        Set<UserTag> updatedTags = editStudyPlanDescriptor.getTags().orElse(studyPlanToEdit.getTags());

        return new StudyPlan(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
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
                && editStudyPlanDescriptor.equals(e.editStudyPlanDescriptor);
    }
    */

    /**
     * Stores the details to edit the studyPlan with. Each non-empty field value will replace the
     * corresponding field value of the studyPlan.
     */
    public static class EditTitleStudyPlanDescriptor {
        // TODO: implement this entire class
        /*
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<UserTag> tags;
         */

        public EditTitleStudyPlanDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTitleStudyPlanDescriptor(EditTitleStudyPlanDescriptor toCopy) {
            /*
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            */
        }

        /**
         * Returns true if at least one field is edited.
         */
        /*
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
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
        */
        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        /*
        public void setTags(Set<UserTag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }
        */
        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        /*
        public Optional<Set<UserTag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStudyPlanDescriptor)) {
                return false;
            }

            // state check
            EditStudyPlanDescriptor e = (EditStudyPlanDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
         */
    }
}
