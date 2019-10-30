package mams.logic.commands;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import mams.commons.core.index.Index;
import mams.commons.util.CollectionUtil;
import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * Encapsulate a ClashCommand class to check clashes in timetable.
 */
public class ClashCommand extends Command {

    public static final String COMMAND_WORD = "clash";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks timetable clashes "
            + "by index or two module codes."
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS1010 " + PREFIX_MODULE + "CS2030 "
            + " OR " + COMMAND_WORD + " "
            + PREFIX_MODULE + "1 " + PREFIX_MODULE + "2 "
            + " OR " + COMMAND_WORD + " "
            + PREFIX_APPEAL + "3"
            + " OR " + COMMAND_WORD + " "
            + PREFIX_STUDENT + "5";


    public static final String MESSAGE_CLASH_DETECTED = "Timetable clash detected: \n";
    public static final String MESSAGE_CLASH_NOT_DETECTED = "There is no timetable clash.";
    public static final String MESSAGE_INVALID_MODULE = "Module not found ";
    public static final String MESSAGE_INVALID_STUDENT = "Student not found ";
    public static final String MESSAGE_INVALID_INDEX = "Index out of bound ";
    public static final String MESSAGE_INVALID_APPEAL_TYPE = "This is not a add/drop module appeal. "
            + "No need to check clashes.";
    protected ArrayList<Integer> clashingSlots;

    private ClashCommandParameters params;

    public ClashCommand(ClashCommandParameters params) {
        this.params = params;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appeal> lastShownAppealList = model.getFilteredAppealList();
        List<Module> lastShownModuleList = model.getFilteredModuleList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        clashingSlots = new ArrayList<>();

        if (params.getAppealIndex().isPresent()) {
            verifyIndex(params.getAppealIndex().get().getZeroBased(), lastShownAppealList.size());
            Appeal appeal = lastShownAppealList.get(params.getAppealIndex().get().getZeroBased());
            if (!isAddOrDropModAppeal(appeal)) {
                throw new CommandException(MESSAGE_INVALID_APPEAL_TYPE);
            }
            Module moduleToAdd = getModule(appeal.getModuleToAdd(), lastShownModuleList);
            Student studentToCheck = getStudent(appeal.getStudentId(), lastShownStudentList);
            ArrayList<Module> currentModules = getStudentCurrentModules(studentToCheck, model);
            for (Module currentModule : currentModules) {
                clashingSlots.addAll(getClashingSlots(currentModule, moduleToAdd));
            }
        }

        if (params.getModuleIndices().isPresent()) {
            Module firstModule = lastShownModuleList.get(params.getFirstModuleIndex().getZeroBased());
            Module secondModule = lastShownModuleList.get(params.getSecondModuleIndex().getZeroBased());
            clashingSlots = getClashingSlots(firstModule, secondModule);
        }

        if (params.getModuleCodes().isPresent()) {
            Module firstModule = getModule(params.getFirstModuleCode(), model.getFilteredModuleList());
            Module secondModule = getModule(params.getSecondModuleCode(), model.getFilteredModuleList());
            clashingSlots = getClashingSlots(firstModule, secondModule);
        }

        if (params.getStudentIndex().isPresent()) {
            Student student = lastShownStudentList.get(params.getStudentIndex().get().getZeroBased());
            ArrayList<Module> currentModules = getStudentCurrentModules(student, model);
            for (int i = 0; i < currentModules.size() - 1; i++) {
                for (int j = i + 1; j < currentModules.size(); j++) {
                    clashingSlots.addAll(getClashingSlots(currentModules.get(i), currentModules.get(j)));
                }
            }
        }

        if (clashingSlots.size() != 0) {
            return new CommandResult(MESSAGE_CLASH_DETECTED + getTimeSlotsToString(generateTempMod()));
        } else {
            return new CommandResult(MESSAGE_CLASH_NOT_DETECTED);
        }

    }

    /**
     * Check whether index is within the bound of the list.
     * @param index index input
     * @param size size of the list
     * @throws CommandException when index is larger than the size of the list
     */
    private void verifyIndex(int index, int size) throws CommandException {
        if (index >= size) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }
    }

    /**
     * Returns an ArrayList of Modules which the student are taking currently.
     * @param student a particular Student object
     * @param model the Model object
     * @return an ArrayList of Modules which the student are taking currently
     */
    private ArrayList<Module> getStudentCurrentModules(Student student, Model model) {
        Set<Tag> currentModulesSet = student.getCurrentModules();
        ArrayList<Module> currentModules = new ArrayList<>();
        for (Tag currentModule : currentModulesSet) {
            String moduleCode = currentModule.getTagName();
            List<Module> modulesToCheckList = model.getFilteredModuleList().stream()
                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
            Module moduleToCheck = modulesToCheckList.get(0);
            currentModules.add(moduleToCheck);
        }
        return currentModules;
    }

    /**
     * Returns a Module object according to the module code given.
     * @param moduleCode String of module code
     * @param moduleList The list containing all modules
     * @return a Module object wtih the module code given
     * @throws CommandException if the module is not found
     */
    private Module getModule(String moduleCode, List<Module> moduleList) throws CommandException {
        List<Module> modulesToCheckList = moduleList.stream()
                .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());

        if (modulesToCheckList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_MODULE);
        }

        return modulesToCheckList.get(0);
    }

    /**
     * Returns a Student object according to the matric ID given.
     * @param studentId String of matric id
     * @param studentList The list containing all students
     * @return a Student object with the matric ID given
     * @throws CommandException if the student is not found
     */
    private Student getStudent(String studentId, List<Student> studentList) throws CommandException {
        List<Student> studentToCheckList = studentList.stream()
                .filter(s -> s.getMatricId().toString().equalsIgnoreCase(studentId)).collect(Collectors.toList());

        if (studentToCheckList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT);
        }

        return studentToCheckList.get(0);
    }

    /**
     * Returns an ArrayList of Integers that contains the clashing time slots.
     * @param moduleToCheckA a Module object of module A
     * @param moduleToCheckB a Module object of module B
     * @return an ArrayList of Integers that contains the clashing time slots.
     */
    private ArrayList<Integer> getClashingSlots(Module moduleToCheckA, Module moduleToCheckB) {
        int[] timeTableA = moduleToCheckA.getTimeSlotToIntArray();
        int[] timeTableB = moduleToCheckB.getTimeSlotToIntArray();
        ArrayList<Integer> slots = new ArrayList<>();
        for (int i : timeTableA) {
            for (int j : timeTableB) {
                if (i == j) {
                    slots.add(i);
                }
            }
        }
        return slots;
    }

    /**
     * Returns a temporary module object which stores the clashing time slots
     * @return Returns a temporary module object which stores the clashing time slots
     */
    protected Module generateTempMod() {
        StringBuilder sb = new StringBuilder("");
        for (int slot : clashingSlots) {
            sb.append(slot).append(",");
        }

        return new Module("", "", "", "", sb.toString(),
                "", new HashSet<>());
    }

    /**
     * Returns a string representation of clashing time slots.
     * @param moduleToCheck a Module object to be checked.
     * @return a string representation of clashing time slots.
     */
    private String getTimeSlotsToString(Module moduleToCheck) {
        return moduleToCheck.timeSlotsToString(clashingSlots.stream().mapToInt(i -> i).toArray());
    }

    /**
     * Returns true if the appeal is a request to add or drop module
     * @param appeal a particular Appeal object
     * @return true if the appeal is a request to add or drop module
     */
    private boolean isAddOrDropModAppeal(Appeal appeal) {
        return appeal.getAppealType().equalsIgnoreCase("add module")
                || appeal.getAppealType().equalsIgnoreCase("drop module");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClashCommand)) {
            return false;
        }

        // state check
        ClashCommand c = (ClashCommand) other;
        return params.equals(c.params);
    }

    /**
     * Stores the details of the parsed parameters that a {@code ClashCommand} will operate on.
     * This helps to avoid having too many unnecessary constructors (or passing of null-values)
     * caused by the optional nature of the parameters passed to ViewCommand.
     */
    public static class ClashCommandParameters {
        private Index appealIndex;
        private ArrayList<Index> moduleIndices;
        private Index studentIndex;
        private ArrayList<String> moduleCodes;

        public void setAppealIndex(Index appealIdx) {
            this.appealIndex = appealIdx;
        }

        public void setModuleIndices(Index firstModule, Index secondModule) {
            this.moduleIndices = new ArrayList<>();
            this.moduleIndices.add(firstModule);
            this.moduleIndices.add(secondModule);
        }

        public void setStudentIndex(Index studentIndex) {
            this.studentIndex = studentIndex;
        }

        public void setModuleCodes(String firstModule, String secondModule) {
            this.moduleCodes = new ArrayList<>();
            this.moduleCodes.add(firstModule);
            this.moduleCodes.add(secondModule);
        }

        public Optional<Index> getAppealIndex() {
            return Optional.ofNullable(appealIndex);
        }

        public Optional<List> getModuleIndices() {
            return Optional.ofNullable(moduleIndices);
        }

        public Index getFirstModuleIndex() {
            return moduleIndices.get(0);
        }

        public Index getSecondModuleIndex() {
            return moduleIndices.get(1);
        }

        public Optional<Index> getStudentIndex() {
            return Optional.ofNullable(appealIndex);
        }

        public Optional<List> getModuleCodes() {
            return Optional.ofNullable(moduleCodes);
        }

        public String getFirstModuleCode() {
            return moduleCodes.get(0);
        }

        public String getSecondModuleCode() {
            return moduleCodes.get(1);
        }

        public boolean isPresent() {
            return CollectionUtil.isAnyNonNull(appealIndex, moduleIndices, studentIndex, moduleCodes);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ClashCommandParameters)) {
                return false;
            }

            // state check
            ClashCommandParameters cp = (ClashCommandParameters) other;

            return getAppealIndex().equals(cp.getAppealIndex())
                    && getModuleIndices().equals(cp.getModuleIndices())
                    && getStudentIndex().equals(cp.getStudentIndex())
                    && getModuleCodes().equals(cp.getModuleCodes());
        }
    }
}
