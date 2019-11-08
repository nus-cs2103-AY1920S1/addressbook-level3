package mams.logic.commands;

import static mams.logic.commands.AddModCommand.MESSAGE_DUPLICATE_MODULE;
import static mams.logic.commands.ClashCommand.ClashCase;
import static mams.logic.commands.ModCommand.MESSAGE_INVALID_MODULE;
import static mams.logic.commands.RemoveModCommand.MESSAGE_MISSING_MODULE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import mams.commons.core.Messages;

import mams.logic.commands.exceptions.CommandException;

import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Credits;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * Mass approves appeals in Mams
 */
public class MassApprove extends Approve {

    private final List<String> validIds;
    private final List<String> invalidIds;
    private final List<String> appealsWithClash;
    private final List<String> alreadyApproved = new ArrayList<>();
    private final List<String> alreadyRejected = new ArrayList<>();
    private final List<String> approvedSuccessfully = new ArrayList<>();

    public MassApprove(List<String> validIds, List<String> invalidIds) {
        this.validIds = validIds;
        this.invalidIds = invalidIds;
        this.appealsWithClash = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        List<Appeal> fullAppealList = model.getFullAppealList();
        for (String appealId : validIds) {
            for (Appeal appeal : fullAppealList) {
                if (appealId.equalsIgnoreCase(appeal.getAppealId())) {
                    Appeal approvedAppeal;
                    Appeal appealToApprove = appeal;
                    if (appealToApprove.isResolved() == false) {
                        if (appealId.equalsIgnoreCase(appeal.getAppealId())) {

                            Student studentToEdit;
                            Student editedStudent;
                            Module moduleToEdit;
                            Module editedModule;
                            String moduleCode;

                            List<Student> fullStudentList = model.getFullStudentList();
                            List<Module> fullModuleList = model.getFullModuleList();

                            String appealType = appealToApprove.getAppealType();
                            String studentToEditId = appealToApprove.getStudentId();

                            if (appealType.equalsIgnoreCase("Increase workload")) {

                                List<Student> studentToCheckList = fullStudentList.stream()
                                        .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                                        .collect(Collectors.toList());

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


                            } else if (appealType.equalsIgnoreCase("Drop module")) {
                                moduleCode = appealToApprove.getModuleToDrop();

                                //Check if student exists
                                List<Student> studentToCheckList = fullStudentList.stream()
                                        .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                                        .collect(Collectors.toList());
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
                                if (!hasModule) {
                                    throw new CommandException(MESSAGE_MISSING_MODULE);
                                }

                                //check if module exist
                                List<Module> moduleToCheckList = fullModuleList.stream()
                                        .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode))
                                        .collect(Collectors.toList());
                                if (moduleToCheckList.isEmpty()) {
                                    throw new CommandException(MESSAGE_INVALID_MODULE);
                                }
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
                                model.setStudent(studentToEdit, editedStudent);
                                model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
                                model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);

                                editedModule = new Module(moduleToEdit.getModuleCode(),
                                        moduleToEdit.getModuleName(),
                                        moduleToEdit.getModuleDescription(),
                                        moduleToEdit.getLecturerName(),
                                        moduleToEdit.getTimeSlot(),
                                        moduleToEdit.getQuota(),
                                        ret2);

                                model.setModule(moduleToEdit, editedModule);
                                model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                                model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);

                            } else {
                                ArrayList<ClashCase> clashCases = new ArrayList<>();
                                moduleCode = appealToApprove.getModuleToAdd();

                                List<Student> studentToCheckList = fullStudentList.stream()
                                        .filter(p -> p.getMatricId().toString().equals(studentToEditId))
                                        .collect(Collectors.toList());
                                if (studentToCheckList.isEmpty()) {
                                    throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
                                }
                                studentToEdit = studentToCheckList.get(0);

                                //check if module exist
                                List<Module> moduleToCheckList = fullModuleList.stream()
                                        .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode))
                                        .collect(Collectors.toList());
                                if (moduleToCheckList.isEmpty()) {
                                    throw new CommandException(MESSAGE_INVALID_MODULE);
                                }
                                moduleToEdit = moduleToCheckList.get(0);


                                //check if student already has module.
                                Set<Tag> studentModules = studentToEdit.getCurrentModules();
                                for (Tag tag : studentModules) {
                                    if (tag.getTagName().equalsIgnoreCase(moduleCode)) {
                                        throw new CommandException(MESSAGE_DUPLICATE_MODULE);
                                    }
                                }

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

                                //Checks if current modules clashes with requested module
                                for (Module currentModule : currentModules) {
                                    if (getClashCase(currentModule, moduleToEdit).isPresent()) {
                                        clashCases.add(getClashCase(currentModule, moduleToEdit).get());
                                    }
                                }

                                if (!clashCases.isEmpty()) {
                                    appealsWithClash.add(appealId);
                                    break;
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

                                model.setStudent(studentToEdit, editedStudent);
                                model.setModule(moduleToEdit, editedModule);
                                model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
                                model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
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
                            model.setAppeal(appealToApprove, approvedAppeal);
                            model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
                            model.setAppeal(appealToApprove, approvedAppeal);
                            approvedSuccessfully.add(appealId);
                        } else if (appealToApprove.isResolved() == true
                                && appealToApprove.getResult().equalsIgnoreCase("APPROVED")) {
                            alreadyApproved.add(appeal.getAppealId());
                        } else if (appealToApprove.isResolved() == true
                                && appealToApprove.getResult().equalsIgnoreCase("REJECTED")) {
                            alreadyRejected.add(appeal.getAppealId());
                        }
                        break;
                    }
                }
            }
        }
        return new CommandResult(resultGenerator());
    }

    /**
     * Generates response for user
     * @return
     */
    private String resultGenerator() {
        String result = "";
        if (approvedSuccessfully.isEmpty()) {
            result += "No appeals were approved";
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
        return result;
    }

    private Optional<ClashCase> getClashCase(Module moduleA, Module moduleB) {
        int[] timeTableA = moduleA.getTimeSlotToIntArray();
        int[] timeTableB = moduleB.getTimeSlotToIntArray();
        ArrayList<Integer> slots = new ArrayList<>();
        for (int i : timeTableA) {
            for (int j : timeTableB) {
                if (i == j) {
                    slots.add(i);
                }
            }
        }
        if (!slots.isEmpty()) {
            ClashCase c = new ClashCase();
            c.setModuleA(moduleA);
            c.setModuleB(moduleB);
            c.setClashingSlots(slots);
            return Optional.of(c);
        }
        return Optional.empty();
    }

}
