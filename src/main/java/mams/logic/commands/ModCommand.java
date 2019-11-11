package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.Set;

import mams.model.Model;
import mams.model.module.Module;
import mams.model.student.Student;
import mams.model.tag.Tag;


/**
 * Abstract class for AddModCommand and RemoveModCommand
 */
public abstract class ModCommand extends Command {

    public static final String COMMAND_WORD_ADD_MOD = "addmod";
    public static final String COMMAND_WORD_REMOVE_MOD = "removemod";
    public static final String MESSAGE_NO_PREAMBLE = "No preamble is allowed. \n";
    public static final String MESSAGE_USAGE_ADD_MOD = COMMAND_WORD_ADD_MOD
            + ": Adds a module to a student in MAMS. \n"
            + "Example: addmod "
            + PREFIX_STUDENT + "MATRIC_ID or INDEX(Student list) "
            + PREFIX_MODULE + "MODULE_CODE or INDEX(Module list) ";
    public static final String MESSAGE_UNKNOWN_ARGUMENT_ADDMOD = "Unknown Argument given. \n"
            + MESSAGE_USAGE_ADD_MOD;
    public static final String MESSAGE_UNKNOWN_ARGUMENT_REMOVEMOD = "Unknown Argument given. \n"
            + MESSAGE_USAGE_ADD_MOD;

    public static final String MESSAGE_USAGE_REMOVE_MOD = COMMAND_WORD_REMOVE_MOD
            + ": Remove a module from a student in MAMS. \n"
            + "Example: removemod "
            + PREFIX_STUDENT + "MATRIC_ID or INDEX(Student list)"
            + PREFIX_MODULE + "MODULE_CODE or INDEX(Module list)";

    public static final String MESSAGE_MISSING_MATRICID_OR_INDEX = "Please enter a valid Matric ID or Index. ";
    public static final String MESSAGE_INVALID_MODULE = "Invalid Module Code. ";
    public static final String MESSAGE_MORE_THAN_ONE_MODULE = "Please specify only 1 Module Code";
    public static final String MESSAGE_MORE_THAN_ONE_IDENTIFIER = "Please specify only 1 index or Matric ID";
    public static final String MESSAGE_DUPLICATE_MODULE = "Student is already registered for this module.";
    public static final String MESSAGE_MISSING_MODULE = "Student is not registered for this module. ";

    /**
     * Updates the list after AddModCommand and RemoveModCommand
     * @param model mams model
     * @param studentToEdit student to be edited
     * @param moduleToEdit module to edited
     * @param ret new tags for student
     * @param ret2 new tags for module
     * @param messageRemoveModSuccess result given in GUI window
     * @return commandResult of action
     */
    protected CommandResult updateList(Model model, Student studentToEdit,
                                       Module moduleToEdit, Set<Tag> ret, Set<Tag> ret2,
                                       String messageRemoveModSuccess) {
        Student studentWithRemovedModule;
        Module moduleWithRemovedStudent;
        studentWithRemovedModule = new Student(studentToEdit.getName(),
                studentToEdit.getCredits(),
                studentToEdit.getPrevMods(),
                studentToEdit.getMatricId(),
                ret);

        moduleWithRemovedStudent = new Module(moduleToEdit.getModuleCode(),
                moduleToEdit.getModuleName(),
                moduleToEdit.getModuleDescription(),
                moduleToEdit.getLecturerName(),
                moduleToEdit.getTimeSlot(),
                moduleToEdit.getQuota(),
                ret2);

        model.setStudent(studentToEdit, studentWithRemovedModule);
        model.setModule(moduleToEdit, moduleWithRemovedStudent);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(messageRemoveModSuccess,
                studentWithRemovedModule.getName()));
    }

}
