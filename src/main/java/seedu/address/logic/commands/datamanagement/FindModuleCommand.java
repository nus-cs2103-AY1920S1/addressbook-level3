package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.ui.ResultViewType;

/**
 * Finds the location of the specified module in the active study plan.
 */
public class FindModuleCommand extends Command {

    public static final String COMMAND_WORD = "findmod";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Finding modules using the module code";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the semester in which the specified module "
            + "is located at.\n"
            + "Parameters: "
            + "MODULE_CODE \n"
            + "Example: "
            + "findmod cs3230";

    public static final String MESSAGE_SUCCESS = "%1$s is currently located in the following semesters";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "This module does not exist.";
    public static final String MESSAGE_MODULE_NOT_FOUND = "%1$s is not in the current study plan";

    private String moduleCode;

    public FindModuleCommand(String moduleCode) {
        requireNonNull(moduleCode);
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isValidModuleCode(this.moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        UniqueSemesterList semesterList = model.getSemestersFromActiveSp();

        UniqueSemesterList modLocation = findMod(semesterList);

        if (modLocation.asUnmodifiableObservableList().size() == 0) {
            throw new CommandException(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode));
        }

        return new CommandResult<Semester>(String.format(MESSAGE_SUCCESS, moduleCode), ResultViewType.SEMESTER,
                modLocation.asUnmodifiableObservableList());
    }

    /**
     * Finds the semesters in which the module is located.
     *
     * @param semesterList The list of semesters in the active study plan
     * @return A UniqueSemesterList containing the semesters where the module is located.
     */
    private UniqueSemesterList findMod(UniqueSemesterList semesterList) {
        UniqueSemesterList locations = new UniqueSemesterList();
        for (Semester semester : semesterList) {
            UniqueModuleList moduleList = semester.getModules();
            if (moduleList.contains(moduleCode)) {
                locations.add(semester);
            }
        }
        return locations;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModuleCommand // instanceof handles nulls
                && moduleCode.equals(((FindModuleCommand) other).moduleCode)); // state check
    }
}
