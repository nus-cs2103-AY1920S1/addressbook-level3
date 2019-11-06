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
import mams.logic.history.FilterOnlyCommandHistory;
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
    public static final String MESSAGE_CLASH_IN_STUDENT = "Timetable clash detected for ";
    public static final String MESSAGE_CLASH_NOT_DETECTED = "There is no timetable clash.";
    public static final String MESSAGE_NEED_TWO_MODULES = "Please enter two modules to check clashes.";
    public static final String MESSAGE_ONLY_ONE_ITEM_ALLOWED = "Please check only one item at a time.";
    public static final String MESSAGE_INVALID_MODULE = "Module not found. ";
    public static final String MESSAGE_INVALID_STUDENT = "Student not found. ";
    public static final String MESSAGE_INVALID_INDEX = "Please enter a valid index. ";
    public static final String MESSAGE_INVALID_APPEAL_TYPE = "This is not a add/drop module appeal. "
            + "No need to check clashes.";
    private ArrayList<ClashCase> clashCases;

    private ClashCommandParameters params;

    public ClashCommand(ClashCommandParameters params) {
        this.params = params;
    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        List<Appeal> lastShownAppealList = model.getFilteredAppealList();
        List<Module> lastShownModuleList = model.getFilteredModuleList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        clashCases = new ArrayList<>();

        if (params.getAppealIndex().isPresent()) {
            verifyIndex(params.getAppealIndex().get().getZeroBased(), lastShownAppealList.size());
            Appeal appeal = lastShownAppealList.get(params.getAppealIndex().get().getZeroBased());
            if (!isAddOrDropModAppeal(appeal)) {
                throw new CommandException(MESSAGE_INVALID_APPEAL_TYPE);
            }
            Module moduleToAdd = getModule(appeal.getModuleToAdd(), model.getFullModuleList());
            Student studentToCheck = getStudent(appeal.getStudentId(), model.getFullStudentList());
            ArrayList<Module> currentModules = getStudentCurrentModules(studentToCheck, model);
            for (Module currentModule : currentModules) {
                if (getClashCase(currentModule, moduleToAdd).isPresent()) {
                    clashCases.add(getClashCase(currentModule, moduleToAdd).get());
                }
            }
        }

        if (params.getModuleIndices().isPresent()) {

            verifyIndex(params.getFirstModuleIndex().getZeroBased(), lastShownModuleList.size());
            verifyIndex(params.getSecondModuleIndex().getZeroBased(), lastShownModuleList.size());
            Module firstModule = lastShownModuleList.get(params.getFirstModuleIndex().getZeroBased());
            Module secondModule = lastShownModuleList.get(params.getSecondModuleIndex().getZeroBased());
            if (getClashCase(firstModule, secondModule).isPresent()) {
                clashCases.add(getClashCase(firstModule, secondModule).get());
            }
        }

        if (params.getModuleCodes().isPresent()) {

            Module firstModule = getModule(params.getFirstModuleCode(), model.getFilteredModuleList());
            Module secondModule = getModule(params.getSecondModuleCode(), model.getFilteredModuleList());
            if (getClashCase(firstModule, secondModule).isPresent()) {
                clashCases.add(getClashCase(firstModule, secondModule).get());
            }
        }

        if (params.getStudentIndex().isPresent()) {
            verifyIndex(params.getStudentIndex().get().getZeroBased(), lastShownStudentList.size());
            Student student = lastShownStudentList.get(params.getStudentIndex().get().getZeroBased());
            ArrayList<Module> currentModules = getStudentCurrentModules(student, model);
            for (int i = 0; i < currentModules.size() - 1; i++) {
                for (int j = i + 1; j < currentModules.size(); j++) {
                    if (getClashCase(currentModules.get(i), currentModules.get(j)).isPresent()) {
                        clashCases.add(getClashCase(currentModules.get(i), currentModules.get(j)).get());
                    }
                }
            }
        }

        if (clashCases.size() != 0) {
            return new CommandResult(MESSAGE_CLASH_DETECTED + getClashDetails());
        } else {
            return new CommandResult(MESSAGE_CLASH_NOT_DETECTED);
        }

    }

    private String getClashDetails() {
        StringBuilder s = new StringBuilder();
        for (ClashCase c : clashCases) {
            s.append(c.getModuleCodeA());
            s.append("  ");
            s.append(c.getModuleCodeB());
            s.append("\n");
            s.append(c.getClashingSlots());
            s.append("\n");
        }
        return s.toString();
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
            List<Module> filteredModulesList = model.getFullModuleList()
                    .stream()
                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode))
                    .collect(Collectors.toList());
            Module filteredModule = filteredModulesList.get(0);
            currentModules.add(filteredModule);
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
     * @param moduleA a Module object of module A
     * @param moduleB a Module object of module B
     * @return an ArrayList of Integers that contains the clashing time slots.
     */
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
     * caused by the optional nature of the parameters passed to ClashCommand.
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
            return Optional.ofNullable(studentIndex);
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

    /**
     * Stores the details of the clash cases that a {@code ClashCommand} will operate on.
     * This helps to display relevant information of the clash details.
     */
    public static class ClashCase {
        private Module moduleA;
        private Module moduleB;
        private ArrayList<Integer> clashingSlots;

        public String getModuleCodeA() {
            return moduleA.getModuleCode();
        }

        public String getModuleCodeB() {
            return moduleB.getModuleCode();
        }

        public String getClashingSlots() {
            return generateTempMod().getModuleTimeTableToString();
        }

        public void setModuleA(Module moduleA) {
            this.moduleA = moduleA;
        }

        public void setModuleB(Module moduleB) {
            this.moduleB = moduleB;
        }

        public void setClashingSlots(ArrayList<Integer> clashingSlots) {
            this.clashingSlots = clashingSlots;
        }

        /**
         * Returns a temporary module object which stores the clashing time slots
         * @return Returns a temporary module object which stores the clashing time slots
         */
        private Module generateTempMod() {
            StringBuilder sb = new StringBuilder("");
            for (int slot : clashingSlots) {
                sb.append(slot).append(",");
            }
            return new Module("", "", "", "", sb.toString(),
                    "", new HashSet<>());
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ClashCase)) {
                return false;
            }

            // state check
            ClashCase c = (ClashCase) other;

            return this.getModuleCodeA().equals(c.getModuleCodeA())
                    && this.getModuleCodeB().equals(c.getModuleCodeB())
                    && this.getClashingSlots().equals(c.getClashingSlots());
        }
    }
}
