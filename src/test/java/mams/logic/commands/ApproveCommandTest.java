package mams.logic.commands;

import static mams.logic.commands.AddModCommand.MESSAGE_STUDENT_ADD_MOD;
import static mams.logic.commands.CommandTestUtil.assertCommandFailure;
import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.logic.commands.RemoveModCommand.MESSAGE_STUDENT_REMOVE_MOD;
import static mams.logic.commands.SetCredits.MESSAGE_STUDENT_CREDIT_CHANGE;
import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_FOURTH;
import static mams.testutil.TypicalIndexes.INDEX_MAX_INT;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;
import static mams.testutil.TypicalMams.getTypicalMams;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;
import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Credits;
import mams.model.student.Student;
import mams.model.tag.Tag;

public class ApproveCommandTest {

    private Model model = new ModelManager(getTypicalMams(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getMams(), new UserPrefs());

    @Test
    public void equal() {
        Index index = INDEX_FIRST;

        // same object -> returns true
        ApproveCommand firstCommand = new ApproveCommand(index, "");
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        ApproveCommand firstCommandCopy = new ApproveCommand(index, "");
        assertTrue(firstCommand.equals(firstCommandCopy));

        index = INDEX_SECOND;

        // different object -> returns false
        ApproveCommand secondCommand = new ApproveCommand(index, "");
        assertFalse(firstCommand.equals(secondCommand));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));
        assertFalse(secondCommand.equals("1"));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        index = INDEX_FIRST;
        ApproveCommand firstCommandWithDifferentRemark = new ApproveCommand(index, "good");
        assertFalse(firstCommand.equals(firstCommandWithDifferentRemark));

    }

    @Test
    public void execute_invalidAppealIndex_throwsCommandException() {

        ApproveCommand command = new ApproveCommand(INDEX_MAX_INT, "");
        // index > size of appeal list
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);

    }


    @Test
    public void execute_validAppealIndex_success() {

        // able to approve fourth appeal in the list
        List<Appeal> lastShownList = expectedModel.getFilteredAppealList();
        Index index = INDEX_FOURTH;
        ApproveCommand command = new ApproveCommand(index, "");
        Appeal approvedAppeal;
        Appeal appealToApprove = lastShownList.get(index.getZeroBased());
        String expectedMessage = "";
        List<Student> fullStudentList = expectedModel.getFullStudentList();
        List<Module> fullModuleList = expectedModel.getFullModuleList();

        if (!appealToApprove.isResolved()) {
            Student studentToEdit;
            Student editedStudent;
            Module moduleToEdit;
            Module editedModule;
            String feedback = "";
            String target = "";
            String type = "";
            String change = "";
            int workLoad = 0;
            String moduleCode;



            String appealType = appealToApprove.getAppealType();
            String studentToEditId = appealToApprove.getStudentId();

            if (appealType.equalsIgnoreCase("Increase workload")) {

                List<Student> studentToCheckList = fullStudentList.stream()
                        .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                        .collect(Collectors.toList());

                studentToEdit = studentToCheckList.get(0);

                editedStudent = new Student(studentToEdit.getName(),
                        new Credits((Integer.toString(appealToApprove.getStudentWorkload()))),
                        studentToEdit.getPrevMods(),
                        studentToEdit.getMatricId(),
                        studentToEdit.getTags());
                expectedModel.setStudent(studentToEdit, editedStudent);
                expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                feedback += MESSAGE_STUDENT_CREDIT_CHANGE;
                workLoad = appealToApprove.getStudentWorkload();
                type += "increase workload";
                target += studentToEditId;

            } else if (appealType.equalsIgnoreCase("Drop module")) {
                moduleCode = appealToApprove.getModuleToDrop();

                //Check if student exists
                List<Student> studentToCheckList = fullStudentList.stream()
                        .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                        .collect(Collectors.toList());

                studentToEdit = studentToCheckList.get(0);
                //check if student has the module (ready for deletion).



                //check if module exist
                List<Module> moduleToCheckList = fullModuleList.stream()
                        .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());

                moduleToEdit = moduleToCheckList.get(0);


                Set<Tag> ret = new HashSet<>();
                Set<Tag> studentAllTags = studentToEdit.getTags();
                for (Tag tag : studentAllTags) {
                    if (!tag.getTagName().equalsIgnoreCase(moduleCode)) {
                        ret.add(tag);
                    }
                }

                //create a tag list without the student for the new module
                Set<Tag> ret2 = new HashSet<>();
                Set<Tag> moduleAllStudents = moduleToEdit.getStudents();
                for (Tag tag : moduleAllStudents) {
                    if (!tag.getTagName().equalsIgnoreCase(studentToEdit.getMatricId().toString())) {
                        ret2.add(tag);
                    }
                }

                editedStudent = new Student(studentToEdit.getName(),
                        studentToEdit.getCredits(),
                        studentToEdit.getPrevMods(),
                        studentToEdit.getMatricId(),
                        ret);
                expectedModel.setStudent(studentToEdit, editedStudent);
                expectedModel.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
                expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

                editedModule = new Module(moduleToEdit.getModuleCode(),
                        moduleToEdit.getModuleName(),
                        moduleToEdit.getModuleDescription(),
                        moduleToEdit.getLecturerName(),
                        moduleToEdit.getTimeSlot(),
                        moduleToEdit.getQuota(),
                        ret2);

                expectedModel.setModule(moduleToEdit, editedModule);
                expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
                feedback = MESSAGE_STUDENT_REMOVE_MOD;
                target = studentToEditId;
                type += "drop module";
                change += moduleCode;

            } else {
                moduleCode = appealToApprove.getModuleToAdd();


                List<Student> studentToCheckList = fullStudentList.stream()
                        .filter(p -> p.getMatricId().toString().equals(studentToEditId)).collect(Collectors.toList());

                studentToEdit = studentToCheckList.get(0);

                //check if module exist
                List<Module> moduleToCheckList = fullModuleList.stream()
                        .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());

                moduleToEdit = moduleToCheckList.get(0);


                //check if student already has module.
                Set<Tag> studentModules = studentToEdit.getCurrentModules();


                //Get all the modules student has and add them into an arraylist of modules for checking
                ArrayList<Module> currentModules = new ArrayList<>();
                for (Tag currentModule : studentModules) {
                    String modCode = currentModule.getTagName();
                    List<Module> filteredModulesList = model.getFullModuleList()
                            .stream()
                            .filter(m -> m.getModuleCode().equalsIgnoreCase(modCode))
                            .collect(Collectors.toList());
                    Module filteredModule = filteredModulesList.get(0);
                    currentModules.add(filteredModule);
                }



                //add module to student.
                Set<Tag> ret = new HashSet<>();
                Set<Tag> studentAllTags = studentToEdit.getTags();
                for (Tag tag : studentAllTags) {
                    ret.add(tag);
                }
                ret.add(new Tag(moduleCode));


                //add student to module field
                Set<Tag> moduleAllStudents = moduleToEdit.getStudents();
                Set<Tag> ret2 = new HashSet<>(moduleAllStudents);
                ret2.add(new Tag(studentToEdit.getMatricId().toString()));

                //replace old student and old module objects with edited modules.
                editedStudent = new Student(studentToEdit.getName(),
                        studentToEdit.getCredits(),
                        studentToEdit.getPrevMods(),
                        studentToEdit.getMatricId(),
                        ret);

                editedModule = new Module(moduleToEdit.getModuleCode(),
                        moduleToEdit.getModuleName(),
                        moduleToEdit.getModuleDescription(),
                        moduleToEdit.getLecturerName(),
                        moduleToEdit.getTimeSlot(),
                        moduleToEdit.getQuota(),
                        ret2);

                expectedModel.setStudent(studentToEdit, editedStudent);
                expectedModel.setModule(moduleToEdit, editedModule);
                expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);

                feedback = MESSAGE_STUDENT_ADD_MOD;
                target = studentToEditId;
                type += "add module";
                change += moduleCode;
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
                    "");
            expectedModel.setAppeal(appealToApprove, approvedAppeal);
            expectedModel.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);

            if (type.equalsIgnoreCase("increase workload")) {
                expectedMessage += (generateSuccessMessageWorkload(appealToApprove, feedback, workLoad, target));
            } else {
                expectedMessage += (generateSuccessMessageModule(approvedAppeal, feedback, target, change));
            }
        }

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // able to approve the second appeal in the list
        index = INDEX_SECOND;
        ApproveCommand secondCommand = new ApproveCommand(index, "");
        Appeal secondApprovedAppeal;
        Appeal secondAppealToApprove = lastShownList.get(index.getZeroBased());
        String secondExpectedMessage = "";

        String appealType = secondAppealToApprove.getAppealType();
        String studentToEditId = secondAppealToApprove.getStudentId();
        Student studentToEdit;
        Student editedStudent;
        Module moduleToEdit;
        Module editedModule;
        String feedback = "";
        String target = "";
        String type = "";
        String change = "";
        int workLoad = 0;
        String moduleCode;

        if (appealType.equalsIgnoreCase("Increase workload")) {


            List<Student> studentToCheckList = fullStudentList.stream()
                    .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                    .collect(Collectors.toList());

            studentToEdit = studentToCheckList.get(0);

            editedStudent = new Student(studentToEdit.getName(),
                    new Credits((Integer.toString(appealToApprove.getStudentWorkload()))),
                    studentToEdit.getPrevMods(),
                    studentToEdit.getMatricId(),
                    studentToEdit.getTags());
            expectedModel.setStudent(studentToEdit, editedStudent);
            expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
            feedback += MESSAGE_STUDENT_CREDIT_CHANGE;
            workLoad = appealToApprove.getStudentWorkload();
            type += "increase workload";
            target += studentToEditId;

        } else if (appealType.equalsIgnoreCase("Drop module")) {
            moduleCode = secondAppealToApprove.getModuleToDrop();

            //Check if student exists
            List<Student> studentToCheckList = fullStudentList.stream()
                    .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                    .collect(Collectors.toList());

            studentToEdit = studentToCheckList.get(0);
            //check if student has the module (ready for deletion).



            //check if module exist
            List<Module> moduleToCheckList = fullModuleList.stream()
                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());

            moduleToEdit = moduleToCheckList.get(0);


            Set<Tag> ret = new HashSet<>();
            Set<Tag> studentAllTags = studentToEdit.getTags();
            for (Tag tag : studentAllTags) {
                if (!tag.getTagName().equalsIgnoreCase(moduleCode)) {
                    ret.add(tag);
                }
            }

            //create a tag list without the student for the new module
            Set<Tag> ret2 = new HashSet<>();
            Set<Tag> moduleAllStudents = moduleToEdit.getStudents();
            for (Tag tag : moduleAllStudents) {
                if (!tag.getTagName().equalsIgnoreCase(studentToEdit.getMatricId().toString())) {
                    ret2.add(tag);
                }
            }

            editedStudent = new Student(studentToEdit.getName(),
                    studentToEdit.getCredits(),
                    studentToEdit.getPrevMods(),
                    studentToEdit.getMatricId(),
                    ret);
            expectedModel.setStudent(studentToEdit, editedStudent);
            expectedModel.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
            expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

            editedModule = new Module(moduleToEdit.getModuleCode(),
                    moduleToEdit.getModuleName(),
                    moduleToEdit.getModuleDescription(),
                    moduleToEdit.getLecturerName(),
                    moduleToEdit.getTimeSlot(),
                    moduleToEdit.getQuota(),
                    ret2);

            expectedModel.setModule(moduleToEdit, editedModule);
            expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
            expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
            feedback = MESSAGE_STUDENT_REMOVE_MOD;
            target = studentToEditId;
            type += "drop module";
            change += moduleCode;

        } else {
            moduleCode = secondAppealToApprove.getModuleToAdd();


            List<Student> studentToCheckList = fullStudentList.stream()
                    .filter(p -> p.getMatricId().toString().equals(studentToEditId)).collect(Collectors.toList());

            studentToEdit = studentToCheckList.get(0);

            //check if module exist
            List<Module> moduleToCheckList = fullModuleList.stream()
                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());

            moduleToEdit = moduleToCheckList.get(0);


            //check if student already has module.
            Set<Tag> studentModules = studentToEdit.getCurrentModules();


            //Get all the modules student has and add them into an arraylist of modules for checking
            ArrayList<Module> currentModules = new ArrayList<>();
            for (Tag currentModule : studentModules) {
                String modCode = currentModule.getTagName();
                List<Module> filteredModulesList = model.getFullModuleList()
                        .stream()
                        .filter(m -> m.getModuleCode().equalsIgnoreCase(modCode))
                        .collect(Collectors.toList());
                Module filteredModule = filteredModulesList.get(0);
                currentModules.add(filteredModule);
            }

            //add module to student.
            Set<Tag> ret = new HashSet<>();
            Set<Tag> studentAllTags = studentToEdit.getTags();
            for (Tag tag : studentAllTags) {
                ret.add(tag);
            }
            ret.add(new Tag(moduleCode));

            //add student to module field
            Set<Tag> moduleAllStudents = moduleToEdit.getStudents();
            Set<Tag> ret2 = new HashSet<>(moduleAllStudents);
            ret2.add(new Tag(studentToEdit.getMatricId().toString()));

            //replace old student and old module objects with edited modules.
            editedStudent = new Student(studentToEdit.getName(),
                    studentToEdit.getCredits(),
                    studentToEdit.getPrevMods(),
                    studentToEdit.getMatricId(),
                    ret);

            editedModule = new Module(moduleToEdit.getModuleCode(),
                    moduleToEdit.getModuleName(),
                    moduleToEdit.getModuleDescription(),
                    moduleToEdit.getLecturerName(),
                    moduleToEdit.getTimeSlot(),
                    moduleToEdit.getQuota(),
                    ret2);

            expectedModel.setStudent(studentToEdit, editedStudent);
            expectedModel.setModule(moduleToEdit, editedModule);
            expectedModel.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
            expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);

            feedback = MESSAGE_STUDENT_ADD_MOD;
            target = studentToEditId;
            type += "add module";
            change += moduleCode;
        }


        secondApprovedAppeal = new Appeal(secondAppealToApprove.getAppealId(),
                secondAppealToApprove.getAppealType(),
                secondAppealToApprove.getStudentId(),
                secondAppealToApprove.getAcademicYear(),
                secondAppealToApprove.getStudentWorkload(),
                secondAppealToApprove.getAppealDescription(),
                secondAppealToApprove.getPreviousModule(),
                secondAppealToApprove.getNewModule(),
                secondAppealToApprove.getModuleToAdd(),
                secondAppealToApprove.getModuleToDrop(),
                true,
                "APPROVED",
                "");
        expectedModel.setAppeal(secondAppealToApprove, secondApprovedAppeal);
        expectedModel.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);

        if (type.equalsIgnoreCase("increase workload")) {
            secondExpectedMessage += (generateSuccessMessageWorkload(secondApprovedAppeal, feedback, workLoad, target));
        } else {
            secondExpectedMessage += (generateSuccessMessageModule(secondApprovedAppeal, feedback, target, change));
        }
        assertCommandSuccess(secondCommand, model, secondExpectedMessage, expectedModel);
    }

    /**
     * Formats feedback message for resolving add and remove mod appeals
     * @param appealToApprove
     * @param feedback
     * @param target
     * @param change
     * @return
     */
    private String generateSuccessMessageModule(Appeal appealToApprove,
                                                String feedback,
                                                String target,
                                                String change) {
        return "Approved "
                + appealToApprove.getAppealId()
                + "\n"
                + String.format(feedback, change)
                + target;
    }

    /**
     * Formats feedback message for resolving increase workload appeals
     * @param appealToApprove
     * @param feedback
     * @param workLoad
     * @param target
     * @return
     */
    private String generateSuccessMessageWorkload(Appeal appealToApprove,
                                                  String feedback,
                                                  int workLoad,
                                                  String target) {
        return "Approved "
                + appealToApprove.getAppealId()
                + "\n"
                + String.format(feedback, target, workLoad);
    }


}
