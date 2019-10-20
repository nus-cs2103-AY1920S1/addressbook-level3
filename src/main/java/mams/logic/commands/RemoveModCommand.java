package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;
import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;

import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * Adds a module to a student
 */
public class RemoveModCommand extends ModCommand {

    public static final String MESSAGE_REMOVE_MOD_SUCCESS = "Removed module from : %1$s";

    private final String matricId;
    private final String moduleCode;
    private final Index index;

    public RemoveModCommand(Index index, String moduleCode) {
        requireNonNull(index);

        this.matricId = null;
        this.index = index;
        this.moduleCode = moduleCode;
    }

    public RemoveModCommand(String matricId, String moduleCode) {
        requireNonNull(matricId);
        requireNonNull(moduleCode);

        this.matricId = matricId;
        this.index = null;
        this.moduleCode = moduleCode;
    }

    /**
     * Checks for logical errors, such as non-existant modules and students etc.
     * Create a new student with the removed module and replaces the old student in mams.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult}
     * @throws CommandException for non-existant modules/student or if the student
     * does not have the module in the first place
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownStudentList = model.getFilteredStudentList();
        List<Module> lastShownModuleList = model.getFilteredModuleList();

        Student studentToEdit;
        Student studentWithRemovedModule;

        //check if module exist
        List<Module> moduleToCheckList = lastShownModuleList.stream()
                .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
        if (moduleToCheckList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        //check if student exist
        if (index != null) { //by index
            if (index.getZeroBased() >= lastShownStudentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
            studentToEdit = lastShownStudentList.get(index.getZeroBased());
        } else { //by matricId
            List<Student> studentToCheckList = lastShownStudentList.stream()
                    .filter(p -> p.getMatricId().toString().equals(matricId)).collect(Collectors.toList());
            if (studentToCheckList.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
            }
            studentToEdit = studentToCheckList.get(0);
        }

        //check if student has the module (ready for deletion).
        Set<Tag> studentModules = studentToEdit.getCurrentModules();
        boolean hasModule = false;
        for (Tag tag: studentModules) {
            if (tag.getTagName().equalsIgnoreCase(moduleCode)) {
                hasModule = true;
            }
        }
        if (hasModule == false) {
            throw new CommandException(MESSAGE_MISSING_MODULE_CODE);
        }

        //create a tag list without the module
        Set<Tag> ret = new HashSet<>();
        Set<Tag> studentAllTags = studentToEdit.getTags();
        for (Tag tag : studentAllTags) {
            if (!tag.getTagName().equalsIgnoreCase(moduleCode)) {
                ret.add(tag);
            }
        }

        studentWithRemovedModule = new Student(studentToEdit.getName(),
                studentToEdit.getCredits(),
                studentToEdit.getPrevMods(),
                studentToEdit.getMatricId(),
                ret);
        model.setStudent(studentToEdit, studentWithRemovedModule);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_REMOVE_MOD_SUCCESS,
                studentWithRemovedModule.getName()));
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
