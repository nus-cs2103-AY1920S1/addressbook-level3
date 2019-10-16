package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * Encapsulate a ClashAppealCommand Class to check timetable clashes in an appeal.
 */
public class ClashAppealCommand extends ClashCommand {

    private final String appealId;
    private Module moduleToAdd;
    private Student studentToCheck;
    private ArrayList<Module> currentModules;

    public ClashAppealCommand(String appeal) {
        requireNonNull(appeal);

        this.appealId = appeal;
        this.currentModules = new ArrayList<>();
        super.clashingSlots = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Appeal> lastShownList = model.getFilteredAppealList();
        List<Module> moduleList = model.getFilteredModuleList();
        List<Student> studentList = model.getFilteredStudentList();

        // get the appeal object
        List<Appeal> appealToCheckList = lastShownList.stream()
                .filter(a -> a.getAppealId().equalsIgnoreCase(appealId)).collect(Collectors.toList());

        if (appealToCheckList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_APPEALID);
        }

        Appeal appealToCheck = appealToCheckList.get(0);

        if (!isAddModAppeal(appealToCheck)) {
            throw new CommandException(MESSAGE_NOT_ADDMOD_APPEAL);
        }

        // get module requested and student who submitted this appeal
        moduleToAdd = getRequestedModule(appealToCheck, moduleList);
        studentToCheck = getStudentToCheck(appealToCheck, studentList);

        // to get the student current modules.
        Set<Tag> currentModulesSet = studentToCheck.getCurrentModules();
        for (Tag currentModule : currentModulesSet) {
            String moduleCode = currentModule.getTagName();
            List<Module> modulesToCheckListA = model.getFilteredModuleList().stream()
                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
            Module moduleToCheck = modulesToCheckListA.get(0);
            currentModules.add(moduleToCheck);
        }

        // check timetable clash
        for (Module currentModule : currentModules) {
            clashingSlots.addAll(getClashingSlots(currentModule, moduleToAdd));
        }

        if (clashingSlots.size() != 0) {
            return new CommandResult(MESSAGE_CLASH_DETECTED + getTimeSlotsToString(generateTempMod()));
        } else {
            return new CommandResult(MESSAGE_CLASH_NOT_DETECTED);
        }
    }

    private boolean isAddModAppeal(Appeal appeal) {
        return appeal.getAppealType().equalsIgnoreCase("add module");
    }

    /**
     * Returns a module object of the requested module in this appeal
     * @param appeal a particular appeal object
     * @param moduleList the module list
     * @return a module object of the requested module in this appeal
     */
    private Module getRequestedModule(Appeal appeal, List<Module> moduleList) throws CommandException {
        String moduleToAdd = appeal.getModule_to_add();
        List<Module> modulesToCheckList = moduleList.stream()
                .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleToAdd)).collect(Collectors.toList());

        if (modulesToCheckList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_MODULETOADD);
        }

        return modulesToCheckList.get(0);
    }

    /**
     * Returns a student object who submitted this appeal
     * @param appeal a particular appeal object
     * @param studentList the student list
     * @return a student object who submitted this appeal
     */
    private Student getStudentToCheck(Appeal appeal, List<Student> studentList) throws CommandException {
        String studentId = appeal.getStudentId();
        List<Student> studentToCheckList = studentList.stream()
                .filter(s -> s.getMatricId().toString().equalsIgnoreCase(studentId)).collect(Collectors.toList());

        if (studentToCheckList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT);
        }

        return studentToCheckList.get(0);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClashAppealCommand)) {
            return false;
        }

        // state check
        ClashAppealCommand c = (ClashAppealCommand) other;
        return appealId.equals(c.appealId);
    }
}
