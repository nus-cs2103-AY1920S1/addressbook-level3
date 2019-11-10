package seedu.address.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Represents a student edit command.
 */
public class StudentEditCommand extends StudentCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing student\n"
            + "Full example : student 1 name/newname --> changes student in index 1 to new student with newname\n\n"
            + "Note: index has to be more than 0";
    public static final String MESSAGE_SUCCESS = "Edited student %1$s to %2$s";
    private final Index index;
    private final EditStudentDescriptor editStudentDescriptor;

    /**
     * Creates a student edit command.
     *
     * @param index                 Index of the student to be edited.
     * @param editStudentDescriptor Object used to edit the student which was specified.
     */
    public StudentEditCommand(Index index, EditStudentDescriptor editStudentDescriptor) {
        this.index = index;
        this.editStudentDescriptor = editStudentDescriptor;
    }

    /**
     * Executes the student edit command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Command result if the command was executed succesfully.
     * @throws CommandException if the command was not input in the correct format/index is out of bounds
     *                          /a student with the edited name already exists in the student list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());
        Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);

        if (!editedStudent.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        if (model.hasStudent(editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        //ensures that the mark is transferred over as well
        if (studentToEdit.getIsMarked()) {
            editedStudent.setMarked();
        }

        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(generateSuccessMessage(studentToEdit, editedStudent), CommandResultType.SHOW_STUDENT);
    }

    /**
     * Generates a command execution success message.
     *
     * @param studentToEdit student to be edited.
     * @param editedStudent students new details.
     */
    private String generateSuccessMessage(Student studentToEdit, Student editedStudent) {
        return String.format(MESSAGE_SUCCESS, studentToEdit, editedStudent);
    }


    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    private static Student createEditedStudent(Student studentToEdit, EditStudentDescriptor editStudentDescriptor) {
        assert studentToEdit != null;

        Name updatedName = editStudentDescriptor.getName().orElse(studentToEdit.getName());
        Set<Tag> tags = studentToEdit.getTags();
        return new Student(updatedName, tags);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentEditCommand)) {
            return false;
        }

        // state check
        StudentEditCommand e = (StudentEditCommand) other;
        return index.equals(e.index)
                && editStudentDescriptor.equals(e.editStudentDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditStudentDescriptor {
        private Name name;

        public EditStudentDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditStudentDescriptor(StudentEditCommand.EditStudentDescriptor toCopy) {
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof StudentEditCommand.EditStudentDescriptor)) {
                return false;
            }

            // state check
            StudentEditCommand.EditStudentDescriptor e = (StudentEditCommand.EditStudentDescriptor) other;

            return getName().equals(e.getName());
        }
    }
}
