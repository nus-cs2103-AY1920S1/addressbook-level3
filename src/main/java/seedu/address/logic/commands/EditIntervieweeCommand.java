package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERVIEWEES;

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
import seedu.address.model.person.Emails;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing interviewee in the Scheduler's interviewee list.
 */
public class EditIntervieweeCommand extends EditCommand {

    private final Name toEdit;
    private final EditIntervieweeDescriptor editIntervieweeDescriptor;

    /**
     * @param toEdit the Name of the interviewee to edit.
     * @param editIntervieweeDescriptor details to edit the interviewee with.
     */
    public EditIntervieweeCommand(Name toEdit, EditIntervieweeDescriptor editIntervieweeDescriptor) {
        requireAllNonNull(toEdit, editIntervieweeDescriptor);
        this.toEdit = toEdit;
        this.editIntervieweeDescriptor = new EditIntervieweeDescriptor(editIntervieweeDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Interviewee> lastShownList = model.getFilteredIntervieweeList();

        // ensure interviewee to edit exists
        if (!model.hasInterviewee(toEdit)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }

        Interviewee intervieweeToEdit = model.getInterviewee(toEdit.fullName);

        // ensure user only edits what is shown on UI (i.e the filtered list)
        if (!lastShownList.contains(intervieweeToEdit)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }

        Interviewee editedInterviewee = createEditedInterviewee(intervieweeToEdit, editIntervieweeDescriptor);

        // an interviewee should not be edited to be the same as another interviewee in the interviewee list
        if (!intervieweeToEdit.isSamePerson(editedInterviewee) && model.hasInterviewee(editedInterviewee)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setInterviewee(intervieweeToEdit, editedInterviewee);
        model.updateFilteredIntervieweeList(PREDICATE_SHOW_ALL_INTERVIEWEES);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedInterviewee));
    }

    /**
     * Generates the EditIntervieweeDescriptor from the interviewee provided.
     */
    private Interviewee createEditedInterviewee(Interviewee interviewee, EditIntervieweeDescriptor descriptor) {
        assert interviewee != null;

        Name updatedName = descriptor.getName().orElse(interviewee.getName());
        Phone updatedPhone = descriptor.getPhone().orElse(interviewee.getPhone());
        Set<Tag> updatedTags = descriptor.getTags().orElse(interviewee.getTags());
        Faculty updatedFaculty = descriptor.getFaculty().orElse(interviewee.getFaculty());
        Integer updatedYearOfStudy = descriptor.getYearOfStudy().orElse(interviewee.getYearOfStudy());
        List<Department> updatedDepartmentChoices = descriptor.getDepartmentChoices()
                .orElse(interviewee.getDepartmentChoices());
        List<Slot> updatedAvailableTimeslots = descriptor.getAvailableTimeslots()
                .orElse(interviewee.getAvailableTimeslots());
        Emails updatedEmails = descriptor.getEmails().orElse(interviewee.getEmails());

        return new Interviewee.IntervieweeBuilder(updatedName, updatedPhone, updatedTags)
                .faculty(updatedFaculty)
                .yearOfStudy(updatedYearOfStudy)
                .departmentChoices(updatedDepartmentChoices)
                .availableTimeslots(updatedAvailableTimeslots)
                .emails(updatedEmails)
                .build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditIntervieweeCommand // instanceof handles nulls
                && toEdit.equals(((EditIntervieweeCommand) other).toEdit) // state check
                && editIntervieweeDescriptor.equals(((EditIntervieweeCommand) other).editIntervieweeDescriptor));
    }

    /**
     * Represents the details to edit an interviewee with.
     */
    public static class EditIntervieweeDescriptor {
        private Name name;
        private Phone phone;
        private Set<Tag> tags;

        private Faculty faculty;
        private Integer yearOfStudy;
        private List<Department> departmentChoices;
        private List<Slot> availableTimeslots;
        private Emails emails;

        // for testing purposes - instantiates an empty descriptor
        public EditIntervieweeDescriptor() {}

        public EditIntervieweeDescriptor(EditIntervieweeDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.tags = toCopy.tags;
            this.faculty = toCopy.faculty;
            this.yearOfStudy = toCopy.yearOfStudy;
            this.departmentChoices = toCopy.departmentChoices;
            this.availableTimeslots = toCopy.availableTimeslots;
            this.emails = toCopy.emails;
        }

        /**
         * Returns true if any one field is edited (i.e non-null).
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, tags, faculty, yearOfStudy, departmentChoices,
                    availableTimeslots, emails);
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

        public void setFaculty(Faculty faculty) {
            requireNonNull(faculty);
            this.faculty = faculty;
        }

        public void setYearOfStudy(Integer yearOfStudy) {
            requireNonNull(yearOfStudy);
            this.yearOfStudy = yearOfStudy;
        }

        /**
         * Sets {@code departmentChoices} to this object's {@code departmentChoices}.
         * A defensive copy of {@code departmentChoices} is used internally.
         */
        public void setDepartmentChoices(List<Department> departmentChoices) {
            requireNonNull(departmentChoices);
            this.departmentChoices = new ArrayList<>(departmentChoices);
        }

        /**
         * Sets {@code availableTimeslots} to this object's {@code availableTimeslots}.
         * A defensive copy of {@code availableTimeslots} is used internally.
         */
        public void setAvailableTimeslots(List<Slot> availableTimeslots) {
            requireNonNull(availableTimeslots);
            this.availableTimeslots = new ArrayList<>(availableTimeslots);
        }

        public void setEmails(Emails emails) {
            requireNonNull(emails);
            this.emails = emails;
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

        public Optional<Faculty> getFaculty() {
            return Optional.ofNullable(faculty);
        }

        public Optional<Integer> getYearOfStudy() {
            return Optional.ofNullable(yearOfStudy);
        }

        public Optional<List<Department>> getDepartmentChoices() {
            return Optional.ofNullable(departmentChoices);
        }

        public Optional<List<Slot>> getAvailableTimeslots() {
            return Optional.ofNullable(availableTimeslots);
        }

        public Optional<Emails> getEmails() {
            return Optional.ofNullable(emails);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditIntervieweeDescriptor)) {
                return false;
            }

            // state check
            EditIntervieweeDescriptor e = (EditIntervieweeDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getTags().equals(e.getTags())
                    && getFaculty().equals(e.getFaculty())
                    && getYearOfStudy().equals(e.getYearOfStudy())
                    && getDepartmentChoices().equals(e.getDepartmentChoices())
                    && getAvailableTimeslots().equals(e.getAvailableTimeslots())
                    && getEmails().equals(e.getEmails());
        }
    }
}
