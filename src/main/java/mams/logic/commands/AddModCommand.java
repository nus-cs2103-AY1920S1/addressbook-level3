package mams.logic.commands;

import static java.util.Objects.requireNonNull;
import static mams.commons.core.Messages.MESSAGE_CREDIT_INSUFFICIENT;
import static mams.commons.core.Messages.MESSAGE_STUDENT_COMPLETED_MODULE;
import static mams.logic.commands.ClashCommand.MESSAGE_CLASH_IN_STUDENT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import mams.commons.core.Messages;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;

import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * Adds a module to a student
 */
public class AddModCommand extends ModCommand {

    public static final String MESSAGE_ADD_MOD_SUCCESS = "Added module to : %1$s";
    public static final String MESSAGE_STUDENT_ADD_MOD = "Added %s to : ";
    private final String moduleIdentifier;
    private final String studentIdentifier;
    private boolean moduleUsingIndex;
    private boolean studentUsingIndex;

    /**
     * Builder class for AddModCommand.
     */
    public static class AddModCommandBuilder {

        private final String moduleIdentifier;
        private final String studentIdentifier;
        private boolean moduleUsingIndex;
        private boolean studentUsingIndex;

        public AddModCommandBuilder (String moduleIdentifier, String studentIdentifier) {
            this.moduleIdentifier = moduleIdentifier;
            this.studentIdentifier = studentIdentifier;
            this.moduleUsingIndex = checkIfModuleIndex(moduleIdentifier);
            this.studentUsingIndex = checkIfStudentIndex(studentIdentifier);
        }

        /**
         * Checks if identifier given for prefix m/ is a number
         * @param moduleIdentifier string given under m/
         * @return true if is a index given
         */
        boolean checkIfModuleIndex(String moduleIdentifier) {
            assert moduleIdentifier != null;
            requireNonNull(moduleIdentifier);

            boolean result = true;
            if (moduleIdentifier.substring(0, 1).contains("C")
                    || moduleIdentifier.substring(0, 1).contains("c")) {
                result = false;
            }

            return result;
        }

        /**
         * Checks if identifier given for prefix s/ is a number
         * @param studentIdentifier string given under s/
         * @return true if is a index given
         */
        boolean checkIfStudentIndex(String studentIdentifier) {
            assert studentIdentifier != null;
            requireNonNull(studentIdentifier);

            boolean result = true;
            if (studentIdentifier.substring(0, 1).contains("A")
                    || studentIdentifier.substring(0, 1).contains("a")) {
                result = false;
            }
            return result;
        }

        public AddModCommand build() {
            return new AddModCommand(this);
        }
    }

    private AddModCommand(AddModCommandBuilder builder) {
        this.moduleIdentifier = builder.moduleIdentifier;
        this.studentIdentifier = builder.studentIdentifier;
        this.moduleUsingIndex = builder.moduleUsingIndex;
        this.studentUsingIndex = builder.studentUsingIndex;
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
        List<Student> studentList;
        List<Module> moduleList;
        Student studentToEdit;
        Module moduleToEdit;

        moduleList = moduleUsingIndex ? model.getFilteredModuleList() : model.getFullModuleList();
        studentList = studentUsingIndex ? model.getFilteredStudentList() : model.getFullStudentList();

        //various checks
        moduleToEdit = returnModuleIfExist(moduleList);
        studentToEdit = returnStudentIfExist(studentList);
        checkIfStudentHasModule(studentToEdit, moduleToEdit.getModuleCode());
        checkIfStudentCompletedModule(studentToEdit, moduleToEdit.getModuleCode());
        checkQuotaLimit(moduleToEdit);
        checkStudentWorkloadLimit(studentToEdit);
        checkIfModuleClash(studentToEdit, model.getFullModuleList(), moduleToEdit.getModuleCode());

        //add module to student
        Set<Tag> studentAllTags = studentToEdit.getTags();
        Set<Tag> ret = new HashSet<>(studentAllTags);
        ret.add(new Tag(moduleToEdit.getModuleCode()));

        //add student to module field
        Set<Tag> moduleAllStudents = moduleToEdit.getStudents();
        Set<Tag> ret2 = new HashSet<>(moduleAllStudents);
        ret2.add(new Tag(studentToEdit.getMatricId().toString()));

        //replace old student and old module objects with new objects
        return updateList(model, studentToEdit, moduleToEdit, ret, ret2, MESSAGE_ADD_MOD_SUCCESS);
    }

    /**
     * Checks if the module exists
     * @param moduleList mMdule list being checked
     * @return Module if found
     * @throws CommandException if module is not found
     */
    Module returnModuleIfExist(List<Module> moduleList) throws CommandException {
        requireNonNull(moduleList);

        if (moduleUsingIndex) {
            int tempIndex = Integer.parseInt(moduleIdentifier);
            if (tempIndex < 1) {
                throw new CommandException(ModCommand.MESSAGE_USAGE_ADD_MOD);
            }
            int tempIndexZeroBased = tempIndex - 1;
            if (tempIndexZeroBased >= moduleList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
            }
            return moduleList.get(tempIndexZeroBased);
        } else {
            List<Module> moduleToCheckList = moduleList.stream()
                    .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleIdentifier)).collect(Collectors.toList());
            if (moduleToCheckList.isEmpty()) {
                throw new CommandException(MESSAGE_INVALID_MODULE);
            }
            return moduleToCheckList.get(0);
        }
    }

    /**
     * Checks if the student exists in mams.
     * @param studentList student list in mams
     * @return student if found
     * @throws CommandException if student is not found
     */
    Student returnStudentIfExist(List<Student> studentList) throws CommandException {
        requireNonNull(studentList);

        if (studentUsingIndex) {
            int tempIndex = Integer.parseInt(studentIdentifier);
            if (tempIndex < 1) {
                throw new CommandException(ModCommand.MESSAGE_USAGE_ADD_MOD);
            }
            int tempIndexZeroBased = tempIndex - 1;
            if (tempIndexZeroBased >= studentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
            return studentList.get(tempIndexZeroBased);
        } else {
            List<Student> studentToCheckList = studentList.stream()
                    .filter(p -> p.getMatricId().toString().equalsIgnoreCase(studentIdentifier))
                    .collect(Collectors.toList());
            if (studentToCheckList.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
            }
            return studentToCheckList.get(0);
        }
    }

    /**
     * Checks if students has already has the module.
     * @param studentToEdit student to be checked
     * @param moduleCode module being added
     * @throws CommandException if the student already has the module
     */
    void checkIfStudentHasModule(Student studentToEdit, String moduleCode) throws CommandException {
        requireNonNull(studentToEdit);
        requireNonNull(moduleCode);

        Set<Tag> studentModules = studentToEdit.getCurrentModules();
        for (Tag tag: studentModules) {
            if (tag.getTagName().equalsIgnoreCase(moduleCode)) {
                throw new CommandException(MESSAGE_DUPLICATE_MODULE);
            }
        }
    }

    /**
     * Checks if the student has already taken the module before.
     * @param studentToEdit student being checked
     * @param moduleCode module being added
     * @throws CommandException if the student has previously completed the module
     */
    void checkIfStudentCompletedModule(Student studentToEdit, String moduleCode) throws CommandException {
        requireNonNull(studentToEdit, moduleCode);

        String prevMods = studentToEdit.getPrevMods().toString();
        if (prevMods.contains(moduleCode)) {
            throw new CommandException(MESSAGE_STUDENT_COMPLETED_MODULE);
        }
    }

    /**
     * Checks if the module has reached max quota.
     * @param moduleToEdit module being checked
     * @throws CommandException if the quota is reached. (and the student should not be added)
     */
    void checkQuotaLimit(Module moduleToEdit) throws CommandException {
        requireNonNull(moduleToEdit);

        if (moduleToEdit.getCurrentEnrolment() == moduleToEdit.getQuotaInt()) {
            throw new CommandException(Module.MESSAGE_CONSTRAINTS_QUOTA_REACHED);
        }
    }

    /**
     * Checks if adding a module will exceed the student's credit limit.
     * All modules have a workload of 4MC.
     * @param studentToEdit student being checked
     * @throws CommandException if the student has insufficient credits
     */
    void checkStudentWorkloadLimit(Student studentToEdit) throws CommandException {
        requireNonNull(studentToEdit);

        int currWorkload = studentToEdit.getNumberOfMods() * 4;
        int maxWorkload = studentToEdit.getCredits().getIntVal();
        if ((maxWorkload - currWorkload) < 4) {
            throw new CommandException(MESSAGE_CREDIT_INSUFFICIENT);
        }
    }

    /**
     * Checks if module being added clashes with current module
     * @param studentToEdit student to be checked
     * @param fullModuleList all modules in list
     * @param moduleCode module to be added
     * @throws CommandException if there are clashes detected
     */
    //@@author chensu2436 and AaronLuk
    private void checkIfModuleClash(Student studentToEdit, ObservableList<Module> fullModuleList,
                                    String moduleCode) throws CommandException {
        //get module object
        List<Module> moduleToCheckList = fullModuleList.stream()
                .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
        Module moduleToEdit = moduleToCheckList.get(0);

        //Get all the modules object student has and add them into an arraylist of modules for checking
        Set<Tag> studentModules = studentToEdit.getCurrentModules();
        ArrayList<Module> currentModules = new ArrayList<>();
        for (Tag currentModule : studentModules) {
            String modCode = currentModule.getTagName();
            List<Module> filteredModulesList = fullModuleList
                    .stream()
                    .filter(m -> m.getModuleCode().equalsIgnoreCase(modCode))
                    .collect(Collectors.toList());
            Module filteredModule = filteredModulesList.get(0);
            currentModules.add(filteredModule);
        }
        ArrayList<ClashCommand.ClashCase> clashCases = new ArrayList<>();
        //Checks if current modules clashes with requested module
        for (Module currentModule : currentModules) {
            if (getClashCase(currentModule, moduleToEdit).isPresent()) {
                clashCases.add(getClashCase(currentModule, moduleToEdit).get());
            }
        }

        //If there exists clashes notify admin via feedback message
        if (clashCases.size() != 0) {
            throw new CommandException(MESSAGE_CLASH_IN_STUDENT
                    + studentToEdit.getMatricId()
                    + ":\n"
                    + getClashDetails(clashCases)
                    + "Unable to add module due to clashes. ");
        }

    }

    private Optional<ClashCommand.ClashCase> getClashCase(Module moduleA, Module moduleB) {
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
            ClashCommand.ClashCase c = new ClashCommand.ClashCase();
            c.setModuleA(moduleA);
            c.setModuleB(moduleB);
            c.setClashingSlots(slots);
            return Optional.of(c);
        }
        return Optional.empty();
    }

    private String getClashDetails(ArrayList<ClashCommand.ClashCase> clashCases) {
        StringBuilder s = new StringBuilder();
        for (ClashCommand.ClashCase c : clashCases) {
            s.append(c.getModuleCodeA());
            s.append("  ");
            s.append(c.getModuleCodeB());
            s.append("\n");
            s.append(c.getClashingSlots());
            s.append("\n");
        }
        return s.toString();
    }
    //@@author

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

