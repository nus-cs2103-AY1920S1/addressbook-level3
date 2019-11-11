package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class RemoveModCommand extends ModCommand {

    public static final String MESSAGE_REMOVE_MOD_SUCCESS = "Removed module from : %1$s";
    public static final String MESSAGE_STUDENT_REMOVE_MOD = "Removed %s from ";

    private final String moduleIdentifier;
    private final String studentIdentifier;
    private boolean moduleUsingIndex;
    private boolean studentUsingIndex;

    /**
     * Builder class for RemoveModCommand.
     */
    public static class RemoveModCommandBuilder {

        private final String moduleIdentifier;
        private final String studentIdentifier;
        private boolean moduleUsingIndex;
        private boolean studentUsingIndex;

        public RemoveModCommandBuilder (String moduleIdentifier, String studentIdentifier) {
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
            boolean result = true;
            if (studentIdentifier.substring(0, 1).contains("A")
                    || studentIdentifier.substring(0, 1).contains("a")) {
                result = false;
            }
            return result;
        }

        public RemoveModCommand build() {
            return new RemoveModCommand(this);
        }
    }

    private RemoveModCommand(RemoveModCommandBuilder builder) {
        this.moduleIdentifier = builder.moduleIdentifier;
        this.studentIdentifier = builder.studentIdentifier;
        this.moduleUsingIndex = builder.moduleUsingIndex;
        this.studentUsingIndex = builder.studentUsingIndex;
    }

    /**
     * Checks for logical errors, such as non-existant modules and students etc.
     * Create a new student with the removed module and replaces the old student in mams.
     * @param model {@code Model} which the command should operate on.
     * @param commandHistory
     * @return {@code CommandResult}
     * @throws CommandException for non-existant modules/student or if the student
     * does not have the module in the first place
     */
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        List<Student> studentList;
        List<Module> moduleList;
        Student studentToEdit;
        Student studentWithRemovedModule;
        Module moduleToEdit;
        Module moduleWithRemovedStudent;

        moduleList = moduleUsingIndex ? model.getFilteredModuleList() : model.getFullModuleList();
        studentList = studentUsingIndex ? model.getFilteredStudentList() : model.getFullStudentList();

        //various checks
        moduleToEdit = returnModuleIfExist(moduleList);
        studentToEdit = returnStudentIfExist(studentList);
        checkIfStudentHasModule(studentToEdit, moduleToEdit.getModuleCode());

        //create a tag list without the module for the new student
        Set<Tag> ret = new HashSet<>();
        Set<Tag> studentAllTags = studentToEdit.getTags();
        for (Tag tag : studentAllTags) {
            if (!tag.getTagName().equalsIgnoreCase(moduleToEdit.getModuleCode())) {
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

        //replace old student and old module objects with edited modules.
        return updateList(model, studentToEdit, moduleToEdit, ret, ret2, MESSAGE_REMOVE_MOD_SUCCESS);
    }

    /**
     * Checks if module exists
     * @param moduleList module list being checked
     * @return module if found
     * @throws CommandException if module is not found
     */
    Module returnModuleIfExist(List<Module> moduleList) throws CommandException {

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
     * Checks if student exists for deletion.
     * @param studentList student list being checked
     * @return student if found
     * @throws CommandException if student does not exist
     */
    Student returnStudentIfExist(List<Student> studentList) throws CommandException {

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
     * Checks if students has the module ready for deletion
     * @param studentToEdit student to be checked
     * @throws CommandException if the student does not have the module
     */
    private void checkIfStudentHasModule(Student studentToEdit, String moduleCode) throws CommandException {
        Set<Tag> studentModules = studentToEdit.getCurrentModules();
        boolean hasModule = false;
        for (Tag tag: studentModules) {
            if (tag.getTagName().equalsIgnoreCase(moduleCode)) {
                hasModule = true;
            }
        }
        if (!hasModule) {
            throw new CommandException(MESSAGE_MISSING_MODULE);
        }
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveModCommand)) {
            return false;
        }

        // state check
        RemoveModCommand e = (RemoveModCommand) other;
        return false;
    }
}
