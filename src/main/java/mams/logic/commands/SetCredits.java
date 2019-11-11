package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;

import mams.model.student.Credits;
import mams.model.student.Student;

/**
 * Adds a module to a student
 */
public class SetCredits extends StudentCommand {

    public static final String MESSAGE_CREDIT_CHANGE_SUCCESS = "Student max credits updated to : %d";
    public static final String MESSAGE_STUDENT_CREDIT_CHANGE = "%s max credits updated to: %d";
    private final String matricId;
    private final String newCredits;
    private final Index index;

    public SetCredits(Index index, String newCredits) {
        requireNonNull(index);
        requireNonNull(newCredits);

        this.matricId = null;
        this.index = index;
        this.newCredits = newCredits;
    }

    public SetCredits(String matricId, String newCredits) {
        requireNonNull(matricId);
        requireNonNull(newCredits);

        this.matricId = matricId;
        this.index = null;
        this.newCredits = newCredits;
    }

    /**
     * Checks for logical errors, such as non-existant modules and students etc.
     * Create a new student with the added module and replaces the old student in mams.
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory
     * @return {@code CommandResult}
     * @throws CommandException for non-existant modules/student or if the student
     * already has the module.
     */
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        Student studentToEdit;
        Student editedStudent;

        //check if student exist
        if (index != null) {
            if (index.getZeroBased() >= lastShownStudentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
            studentToEdit = lastShownStudentList.get(index.getZeroBased());
        } else {
            List<Student> studentToCheckList = lastShownStudentList.stream()
                    .filter(p -> p.getMatricId().toString().equals(matricId)).collect(Collectors.toList());
            if (studentToCheckList.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
            }
            studentToEdit = studentToCheckList.get(0);
        }

        // check if credit value is valid
        if (!Credits.isValidCredits(newCredits)) {
            throw new CommandException(Messages.MESSAGE_INVALID_CREDIT_VALUE);
        }

        //check if current mods satisfy new credits

        if (4 * studentToEdit.getNumberOfMods() > Integer.parseInt(newCredits)) {
            throw new CommandException(Messages.MESSAGE_CREDIT_UNDER_AMT_MODS);
        }

        editedStudent = new Student(studentToEdit.getName(),
                new Credits(newCredits),
                studentToEdit.getPrevMods(),
                studentToEdit.getMatricId(),
                studentToEdit.getTags());
        model.setStudent(studentToEdit, editedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_CREDIT_CHANGE_SUCCESS,
                editedStudent.getCredits().getIntVal()));
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
        return false;
    }
}
