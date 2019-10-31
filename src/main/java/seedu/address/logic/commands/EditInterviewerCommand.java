package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERVIEWERS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing interviewer in the Scheduler's interviewer list.
 */
public class EditInterviewerCommand extends EditCommand {

    private final Name toEdit;
    private final EditInterviewerDescriptor editInterviewerDescriptor;

    /**
     * @param toEdit the Name of the interviewer to edit.
     * @param editInterviewerDescriptor details to edit the Interviewer with.
     */
    public EditInterviewerCommand(Name toEdit, EditInterviewerDescriptor editInterviewerDescriptor) {
        requireAllNonNull(toEdit, editInterviewerDescriptor);
        this.toEdit = toEdit;
        this.editInterviewerDescriptor = new EditInterviewerDescriptor(editInterviewerDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interviewer> lastShownList = model.getFilteredInterviewerList();

        // ensure interviewer to edit exists
        if (!model.hasInterviewer(toEdit)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }

        Interviewer interviewerToEdit = model.getInterviewer(toEdit.fullName);

        // ensure user only edits what is shown on UI (i.e the filtered list)
        if (!model.getFilteredInterviewerList().contains(interviewerToEdit)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }

        Interviewer editedInterviewer = createEditedInterviewer(interviewerToEdit, editInterviewerDescriptor);

        // an interviewee should not be edited to be the same as another interviewee in the interviewee list
        if (!interviewerToEdit.isSamePerson(editedInterviewer) && model.hasInterviewer(editedInterviewer)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setInterviewer(interviewerToEdit, editedInterviewer);
        model.updateFilteredInterviewerList(PREDICATE_SHOW_ALL_INTERVIEWERS);

        return new CommandResult(MESSAGE_EDIT_PERSON_SUCCESS);
    }

    /**
     * Generates the EditInterviewerDescriptor from the interviewer provided.
     */
    private Interviewer createEditedInterviewer(Interviewer interviewer, EditInterviewerDescriptor descriptor) {
        assert interviewer != null;

        Name updatedName = descriptor.getName().orElse(interviewer.getName());
        Phone updatedPhone = descriptor.getPhone().orElse(interviewer.getPhone());
        Set<Tag> updatedTags = descriptor.getTags().orElse(interviewer.getTags());
        Department updatedDepartment = descriptor.getDepartment().orElse(interviewer.getDepartment());
        Email updatedEmail = descriptor.getEmail().orElse(interviewer.getEmail());
        List<Slot> updatedAvailabilities = descriptor.getAvailabilities().orElse(interviewer.getAvailabilities());

        return new Interviewer.InterviewerBuilder(updatedName, updatedPhone, updatedTags)
                .department(updatedDepartment)
                .email(updatedEmail)
                .availabilities(updatedAvailabilities)
                .build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditInterviewerCommand // instanceof handles nulls
                && toEdit.equals(((EditInterviewerCommand) other).toEdit) // state check
                && editInterviewerDescriptor.equals(((EditInterviewerCommand) other).editInterviewerDescriptor));
    }

    /**
     * Represents the details to edit an Interviewer with.
     */
    public static class EditInterviewerDescriptor {
        private Name name;
        private Phone phone;
        private Set<Tag> tags;

        private Department department;
        private Email email;
        private List<Slot> availabilities;

        // for testing purposes - instantiates an empty descriptor.
        public EditInterviewerDescriptor() {}

        EditInterviewerDescriptor(EditInterviewerDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.tags = toCopy.tags;
            this.department = toCopy.department;
            this.email = toCopy.email;
            this.availabilities = toCopy.availabilities;
        }

        /**
         * Returns true if any one field is edited (i.e non-null).
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, tags, department, email, availabilities);
        }

        public void setName(Name name) {
            requireNonNull(name);
            this.name = name;
        }

        public void setPhone(Phone phone) {
            requireNonNull(phone);
            this.phone = phone;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            requireNonNull(tags);
            this.tags = new HashSet<>(tags);
        }

        public void setDepartment(Department department) {
            requireNonNull(department);
            this.department = department;
        }

        public void setEmail(Email email) {
            requireNonNull(email);
            this.email = email;
        }

        /**
         * Sets {@code availabilities} to this object's {@code availabilities}.
         * A defensive copy of {@code availabilities} is used internally.
         */
        public void setAvailabilities(List<Slot> availabilities) {
            requireNonNull(availabilities);
            this.availabilities = new ArrayList<>(availabilities);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        public Optional<Department> getDepartment() {
            return Optional.ofNullable(department);
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public Optional<List<Slot>> getAvailabilities() {
            return Optional.ofNullable(availabilities);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditInterviewerDescriptor)) {
                return false;
            }

            // state check
            EditInterviewerDescriptor e = (EditInterviewerDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getTags().equals(e.getTags())
                    && getDepartment().equals(e.getDepartment())
                    && getEmail().equals(e.getEmail())
                    && getAvailabilities().equals(e.getAvailabilities());
        }
    }
}
