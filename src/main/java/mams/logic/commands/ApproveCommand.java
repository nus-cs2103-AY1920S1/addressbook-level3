package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;

import mams.logic.commands.exceptions.CommandException;

import mams.model.Model;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Credits;
import mams.model.student.Student;
import mams.model.tag.Tag;


/**
 * Approves a appeal in mams.
 */
public class ApproveCommand extends Approve {

    private final Index index;
    private final String reason;

    public ApproveCommand(Index index, String reason) {
        requireNonNull(index, reason);

        this.index = index;
        this.reason = reason;

    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Appeal> lastShownList = model.getFilteredAppealList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);
        }

        Appeal approvedAppeal;

        Appeal appealToApprove = lastShownList.get(index.getZeroBased());

        if (appealToApprove.isResolved() == false) {
            Student studentToEdit;
            Student editedStudent;
            Module moduleToEdit;
            Module editedModule;
            String feedback = "";

            String moduleCode;

            List<Student> lastShownStudentList = model.getFilteredStudentList();
            List<Module> lastShownModuleList = model.getFilteredModuleList();

            String appealType = appealToApprove.getAppealType();
            String studentToEditId = appealToApprove.getStudentId();

            if(appealType.equalsIgnoreCase("Increase workload")) {
                List<Student> studentToCheckList =
                        lastShownStudentList.stream()
                            .filter(p -> p.getMatricId().toString().equals(studentToEditId)).collect(Collectors.toList());
                    if (studentToCheckList.isEmpty()) {
                        throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
                    }
                    studentToEdit = studentToCheckList.get(0);

                editedStudent = new Student(studentToEdit.getName(),
                            new Credits((Integer.toString(appealToApprove.getStudentWorkload()))),
                            studentToEdit.getPrevMods(),
                            studentToEdit.getMatricId(),
                            studentToEdit.getTags());
                model.setStudent(studentToEdit, editedStudent);
                model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                return new CommandResult(String.format(Credits.MESSAGE_CREDIT_CHANGE_SUCCESS,
                            editedStudent.getCredits().getIntVal()));
            } else if (appealType.equalsIgnoreCase("Drop module")) {
                moduleCode = appealToApprove.getModuleToDrop();
                List<Student> studentToCheckList = lastShownStudentList.stream()
                        .filter(p -> p.getMatricId().toString().equals(studentToEditId)).collect(Collectors.toList());
                if (studentToCheckList.isEmpty()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
                }
                studentToEdit = studentToCheckList.get(0);
                //check if student has the module (ready for deletion).
                Set<Tag> studentModules = studentToEdit.getCurrentModules();
                boolean hasModule = false;
                for (Tag tag : studentModules) {
                    if (tag.getTagName().equalsIgnoreCase(moduleCode)) {
                        hasModule = true;
                    }
                }
                if (hasModule == false) {
                    throw new CommandException(MESSAGE_MISSING_MODULE);
                }

            }


            approvedAppeal = new Appeal(appealToApprove.getAppealId(),
                    appealToApprove.getAppealType(),
                    appealToApprove.getStudentId(),
                    appealToApprove.getAcademicYear(),
                    appealToApprove.getStudentWorkload(),
                    appealToApprove.getAppealDescription(),
                    appealToApprove.getPreviousModule(),
                    appealToApprove.getNewModule(),
                    appealToApprove.getModuleToAdd(),
                    appealToApprove.getModuleToDrop(),
                    true,
                    "APPROVED",
                    reason);
            model.setAppeal(appealToApprove, approvedAppeal);
            model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
            return new CommandResult(
                    generateSuccessMessage(approvedAppeal));
        } else {
            return new CommandResult(MESSAGE_APPEAL_ALREADY_APPROVED);
        }

    }

    private String generateSuccessMessage(Appeal appealToApprove) {
        return "Approved " + appealToApprove.getAppealId();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApproveCommand)) {
            return false;
        }

        // state check
        ApproveCommand e = (ApproveCommand) other;
        return index.equals(e.index)
                && reason.equals(e.reason);
    }
}
