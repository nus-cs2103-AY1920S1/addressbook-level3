package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import mams.commons.core.Messages;
import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;

import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.tag.Tag;

/**
 * Adds a module to a student
 */
public class AddModCommand extends ModCommand {

    public static final String MESSAGE_ADD_MOD_SUCCESS = "Added module to : %1$s";

    private final String matricId;
    private final String moduleCode;
    private final String index;
    private boolean usingIndex;

    /**
     * Builder class for AddModCommand.
     */
    public static class AddModCommandBuilder {

        private String matricId; //either
        private final String moduleCode; //required
        private String index; //or
        private boolean usingIndex; //required

        public AddModCommandBuilder (String moduleCode, boolean usingIndex) {
            this.moduleCode = moduleCode;
            this.usingIndex = usingIndex;
        }

        public AddModCommandBuilder setMatricId (String matricId) {
            this.matricId = matricId;
            return this;
        }

        public AddModCommandBuilder setIndex (String index) {
            this.index = index;
            return this;
        }

        public AddModCommand build() {
            return new AddModCommand(this);
        }
    }

    private AddModCommand(AddModCommandBuilder builder) {
        this.moduleCode = builder.moduleCode;
        this.matricId = builder.matricId;
        this.index = builder.index;
        this.usingIndex = builder.usingIndex;
    }

    /**
     * Checks for logical errors, such as non-existant modules and students etc.
     * Create a new student with the added module and replaces the old student in mams.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult}
     * @throws CommandException for non-existant modules/student or if the student
     * already has the module.
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownStudentList = model.getFilteredStudentList();
        List<Module> lastShownModuleList = model.getFilteredModuleList();

        Student studentToEdit;
        Student studentWithAddedModule;

        //check if module exist
        List<Module> moduleToCheckList = lastShownModuleList.stream()
                .filter(m -> m.getModuleCode().equalsIgnoreCase(moduleCode)).collect(Collectors.toList());
        if (moduleToCheckList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_MODULE);
        }

        //check if student exist
        if (usingIndex) { //by index
            int tempIndex = Integer.parseInt(index);
            if (tempIndex < 1) {
                throw new CommandException(ModCommand.MESSAGE_USAGE_ADD_MOD);
            }
            int tempIndexZeroBased = tempIndex - 1;
            if (tempIndexZeroBased >= lastShownStudentList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
            studentToEdit = lastShownStudentList.get(tempIndexZeroBased);
        } else { //by matricId
            List<Student> studentToCheckList = lastShownStudentList.stream()
                    .filter(p -> p.getMatricId().toString().equals(matricId)).collect(Collectors.toList());
            if (studentToCheckList.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_MATRIC_ID);
            }
            studentToEdit = studentToCheckList.get(0);
        }

        //check if student already has module.
        Set<Tag> studentModules = studentToEdit.getCurrentModules();
        for (Tag tag: studentModules) {
            if (tag.getTagName().equalsIgnoreCase(moduleCode)) {
                throw new CommandException(MESSAGE_DUPLICATE_MODULE);
            }
        }

        //add module to student.
        Set<Tag> ret = new HashSet<>();
        Set<Tag> studentAllTags = studentToEdit.getTags();
        for (Tag tag : studentAllTags) {
            ret.add(tag);
        }
        ret.add(new Tag(moduleCode));

        studentWithAddedModule = new Student(studentToEdit.getName(),
                studentToEdit.getCredits(),
                studentToEdit.getPrevMods(),
                studentToEdit.getMatricId(),
                ret);
        model.setStudent(studentToEdit, studentWithAddedModule);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_ADD_MOD_SUCCESS,
                studentWithAddedModule.getName()));
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
