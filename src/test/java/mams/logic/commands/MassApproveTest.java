package mams.logic.commands;

import static mams.logic.commands.Approve.MESAGE_NO_APPEALS_APPROVED;
import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.testutil.TypicalMams.getTypicalMams;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Credits;
import mams.model.student.Student;
import mams.model.tag.Tag;

public class MassApproveTest {

    private Model model = new ModelManager(getTypicalMams(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getMams(), new UserPrefs());



    @Test
    public void equal() {

        ArrayList<String> validIDs = new ArrayList<>();
        ArrayList<String> invalidIDs = new ArrayList<>();


        // same object -> returns true
        MassApprove firstEmptyCommand = new MassApprove(validIDs, invalidIDs);
        assertTrue(firstEmptyCommand.equals(firstEmptyCommand));

        validIDs.add("C000000");
        validIDs.add("C000023");
        validIDs.add("C000007");

        MassApprove validCommand = new MassApprove(validIDs, invalidIDs);
        MassApprove validCommandCopy = new MassApprove(validIDs, invalidIDs);
        // same values -> returns true
        assertTrue(validCommand.equals(validCommandCopy));

        ArrayList<String> secondValidIDs = new ArrayList<>();
        secondValidIDs.add("C000001");
        secondValidIDs.add("C000123");
        // different values -> returns false
        assertFalse(validCommand.equals(secondValidIDs));

        invalidIDs.add("C0123");
        invalidIDs.add("C0132");

        ArrayList<String> validListWithRightOrder = new ArrayList<>();
        validIDs.add("C000000");
        validIDs.add("C000007");
        validIDs.add("C000023");

        MassApprove validCommandCopyWithRightOrder = new MassApprove(validIDs, invalidIDs);
        // same values different order and different invalid list -> returns true
        assertTrue(validCommand.equals(validCommandCopyWithRightOrder));

        // different type -> returns false
        assertFalse(validCommand.equals(1));

        // different type -> returns false
        assertFalse(validCommand.equals("1"));

        // null -> returns false
        assertFalse(validCommand.equals(null));
    }

    @Test
    public void execute_nothingApproved_Success() {
        ArrayList<String> validIDs = new ArrayList<>();
        ArrayList<String> invalidIDs = new ArrayList<>();
        String expectedMessage = "";
        invalidIDs.add("C0123");
        invalidIDs.add("C0132");
        MassApprove nothingApprovedCommand = new MassApprove(validIDs, invalidIDs);
        expectedMessage += MESAGE_NO_APPEALS_APPROVED + "\nInvalid appeal IDs: " + invalidIDs.toString();
        assertCommandSuccess(nothingApprovedCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noSuchAppealId_Success() {
        ArrayList<String> validIDs = new ArrayList<>();
        ArrayList<String> invalidIDs = new ArrayList<>();
        String expectedMessage = "";
        validIDs.add("C120000");
        validIDs.add("C999999");
        MassApprove command = new MassApprove(validIDs, invalidIDs);
        expectedMessage += MESAGE_NO_APPEALS_APPROVED + "\nThese appeal IDs do not exist: " + validIDs.toString();

    }

    @Test
    public void execute_validInput_Success() {

        ArrayList<String> inputValidIds = new ArrayList<>();
        ArrayList<String> inputInvalidIds = new ArrayList<>();
        String expectedMessage = "";

        inputValidIds.add("C000001");

        inputInvalidIds.add("C0123");
        inputInvalidIds.add("C0132");

        MassApprove command = new MassApprove(inputValidIds, inputInvalidIds);

        List<String> appealsWithClash = new ArrayList<>();
        List<String> cannotFindIdList = new ArrayList<>();
        List<String> alreadyApproved = new ArrayList<>();
        List<String> alreadyRejected = new ArrayList<>();
        List<String> approvedSuccessfully = new ArrayList<>();


        List<Student> fullStudentList = expectedModel.getFullStudentList();
        List<Module> fullModuleList = expectedModel.getFullModuleList();
        List<Appeal> fullAppealList = expectedModel.getFullAppealList();
        boolean foundId;
        for (String appealId : inputValidIds) {
            foundId = false;
            for (Appeal appeal : fullAppealList) {
                if (appealId.equalsIgnoreCase(appeal.getAppealId())) {
                    Appeal approvedAppeal;
                    Appeal appealToApprove = appeal;
                    if (!appealToApprove.isResolved()) {

                        Student studentToEdit;
                        Student editedStudent;
                        Module moduleToEdit;
                        Module editedModule;
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


                        } else if (appealType.equalsIgnoreCase("Drop module")) {
                            moduleCode = appealToApprove.getModuleToDrop();

                            //Check if student exists
                            List<Student> studentToCheckList = fullStudentList.stream()
                                    .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                                    .collect(Collectors.toList());

                            studentToEdit = studentToCheckList.get(0);

                            //check if student has the module (ready for deletion).
                            Set<Tag> studentModules = studentToEdit.getCurrentModules();


                            //check if module exist
                            List<Module> moduleToCheckList = fullModuleList.stream()
                                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode))
                                    .collect(Collectors.toList());

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

                        } else {
                            ArrayList<ClashCommand.ClashCase> clashCases = new ArrayList<>();
                            moduleCode = appealToApprove.getModuleToAdd();

                            List<Student> studentToCheckList = fullStudentList.stream()
                                    .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                                    .collect(Collectors.toList());

                            studentToEdit = studentToCheckList.get(0);

                            //check if module exist
                            List<Module> moduleToCheckList = fullModuleList.stream()
                                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode))
                                    .collect(Collectors.toList());

                            moduleToEdit = moduleToCheckList.get(0);


                            //check if student already has module.
                            Set<Tag> studentModules = studentToEdit.getCurrentModules();


                            //Get all the modules student has and add them into an arraylist of modules for checking
                            ArrayList<Module> currentModules = new ArrayList<>();
                            for (Tag currentModule : studentModules) {
                                String modCode = currentModule.getTagName();
                                List<Module> filteredModulesList = expectedModel.getFullModuleList()
                                        .stream()
                                        .filter(m -> m.getModuleCode().equalsIgnoreCase(modCode))
                                        .collect(Collectors.toList());
                                Module filteredModule = filteredModulesList.get(0);
                                currentModules.add(filteredModule);
                            }

                            //Checks if current modules clashes with requested module
//                            for (Module currentModule : currentModules) {
//                                if (getClashCase(currentModule, moduleToEdit).isPresent()) {
//                                    clashCases.add(getClashCase(currentModule, moduleToEdit).get());
//                                }
//                            }
//
//                            if (!clashCases.isEmpty()) {
//                                appealsWithClash.add(appealId);
//                                break;
//                            }

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
                        expectedModel.setAppeal(appealToApprove, approvedAppeal);
                        approvedSuccessfully.add(appealId);

                    } else if (appealToApprove.isResolved() == true
                            && appealToApprove.getResult().equalsIgnoreCase("APPROVED")) {
                        alreadyApproved.add(appeal.getAppealId());
                    } else if (appealToApprove.isResolved() == true
                            && appealToApprove.getResult().equalsIgnoreCase("REJECTED")) {
                        alreadyRejected.add(appeal.getAppealId());
                    }
                    foundId = true;
                    break;
                }
            }
            if (!foundId) {
                cannotFindIdList.add(appealId);
            }
        }

        expectedMessage += resultGenerator(approvedSuccessfully,
                alreadyApproved,
                alreadyRejected,
                inputInvalidIds,
                appealsWithClash,
                cannotFindIdList);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    /**
     * Generates feedback message
     * @param approvedSuccessfully
     * @param alreadyApproved
     * @param alreadyRejected
     * @param invalidIds
     * @param appealsWithClash
     * @param cannotFindIDList
     * @return
     */
    private String resultGenerator(List<String> approvedSuccessfully,
                                   List<String> alreadyApproved,
                                   List<String> alreadyRejected,
                                   List<String> invalidIds,
                                   List<String> appealsWithClash,
                                   List<String> cannotFindIdList) {
        String result = "";
        if (approvedSuccessfully.isEmpty()) {
            result += MESAGE_NO_APPEALS_APPROVED;
        } else {
            result += "Successfully approved: " + approvedSuccessfully.toString();
        }
        if (!alreadyApproved.isEmpty()) {
            result += "\nAlready approved: " + alreadyApproved.toString();
        }
        if (!alreadyRejected.isEmpty()) {
            result += "\nAlready rejected: " + alreadyRejected.toString();
        }
        if (!invalidIds.isEmpty()) {
            result += "\nInvalid appeal IDs: " + invalidIds.toString();
        }
        if (!appealsWithClash.isEmpty()) {
            result += "\nAppeals with module clash: " + appealsWithClash.toString();
        }
        if (!cannotFindIdList.isEmpty()) {
            result += "\nThese appeal IDs do not exist: " + cannotFindIdList.toString();
        }
        return result;
    }


}
