package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import mams.commons.util.CollectionUtil;
import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;

import mams.model.student.Credits;
import mams.model.student.Email;
import mams.model.student.MatricId;
import mams.model.student.Name;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * Adds a module to a student
 */
public class AddModCommand extends Command {

    public static final String COMMAND_WORD = "addmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to a student in MAMS"
            + "Session ID is optional"
            + "Parameters: "
            + "MATRIC_ID"
            + "MODULE_CODE"
            + "[SESSION_ID]";

    //TODO: message for success. userguide?
    public static final String MESSAGE_EDIT_STUDENT_MODULE_SUCCESS = "Edited Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Module not added(Whats the reason).";

    private final String studentId;
    private final String moduleCode;

    private final EditStudentDescriptor editStudentDescriptor;

    public AddModCommand(String studentId, String moduleCode, EditStudentDescriptor editStudentDescriptor) {
        requireNonNull(studentId);
        requireNonNull(moduleCode);
        requireNonNull(editStudentDescriptor);

        this.studentId = studentId;
        this.moduleCode = moduleCode;
        this.editStudentDescriptor = new EditStudentDescriptor(editStudentDescriptor);
    }

    /**
     * temp.
     * @param model {@code Model} which the command should operate on.
     * @return temp
     * @throws CommandException temp
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        List<Student> studentInList = lastShownList.stream()
                .filter(p -> p.getName().fullName.equals(studentId)).collect(Collectors.toList());

        //TODO: addmod command!

        Student studentToEdit = studentInList.get(1);
        Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);

        if (!studentToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
        //throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        //if (model.hasModule(toAdd)) {
        // throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        // }

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_MODULE_SUCCESS, editedStudent));
    }

    /**
     * temp.
     * @param studentToEdit temp
     * @param editStudentDescriptor temp
     * @return temp
     */
    private static Student createEditedStudent(Student studentToEdit, EditStudentDescriptor editStudentDescriptor) {
        assert studentToEdit != null;

        Name updatedName = editStudentDescriptor.getName().orElse(studentToEdit.getName());
        Credits updatedCredits = editStudentDescriptor.getCredits().orElse(studentToEdit.getCredits());
        Email updatedEmail = editStudentDescriptor.getEmail().orElse(studentToEdit.getEmail());
        MatricId updatedMatricId = editStudentDescriptor.getMatricId().orElse(studentToEdit.getMatricId());
        Set<Tag> updatedTags = editStudentDescriptor.getTags().orElse(studentToEdit.getTags());

        return new Student(updatedName, updatedCredits, updatedEmail, updatedMatricId, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddModCommand)) {
            return false;
        }

        // state check
        AddModCommand e = (AddModCommand) other;
        //return index.equals(e.index)
        //        && editStudentDescriptor.equals(e.editStudentDescriptor);
        return false;
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditStudentDescriptor {
        private Name name;
        private Credits credits;
        private Email email;
        private MatricId matricId;
        private Set<Tag> tags;

        public EditStudentDescriptor() {}

        public EditStudentDescriptor(EditStudentDescriptor toCopy) {
            setName(toCopy.name);
            setCredits(toCopy.credits);
            setEmail(toCopy.email);
            setMatricId(toCopy.matricId);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, credits, email, matricId, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCredits(Credits credits) {
            this.credits = credits;
        }

        public Optional<Credits> getCredits() {
            return Optional.ofNullable(credits);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setMatricId(MatricId matricId) {
            this.matricId = matricId;
        }

        public Optional<MatricId> getMatricId() {
            return Optional.ofNullable(matricId);
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

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditStudentDescriptor)) {
                return false;
            }

            // state check
            EditCommand.EditStudentDescriptor e = (EditCommand.EditStudentDescriptor) other;

            return getName().equals(e.getName())
                    && getCredits().equals(e.getCredits())
                    && getEmail().equals(e.getEmail())
                    && getMatricId().equals(e.getMatricId())
                    && getTags().equals(e.getTags());
        }
    }
}
