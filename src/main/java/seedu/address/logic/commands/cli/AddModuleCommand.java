package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;


/**
 * Adds module to a semester
 */
public class AddModuleCommand extends Command {
    public static final String COMMAND_WORD = "addmod";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Assigning modules to a given semester";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the specified modules to a given semester of the current study plan.\n"
            + "Parameters: "
            + "SEMESTER "
            + "MODULE_CODE ... "
            + "MODULE_CODE\n"
            + "Example: addmod Y1S1 CS1101S CS1231S";

    public static final String MESSAGE_SUCCESS = "Module %1$s added to %2$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "One of these modules already exists in the semester";
    public static final String MESSAGE_DUPLICATE_MODULE_STUDY_PLAN =
            "One of these modules already exists in the study plan";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "One of these modules does not exist.";
    public static final String MESSAGE_SEMESTER_DOES_NOT_EXIST = "This semester does not exist.";
    public static final String MESSAGE_SEMESTER_BLOCKED = "This semester is blocked.";

    private final SemesterName sem;
    private final List<String> moduleCodes;

    public AddModuleCommand(List<String> moduleCodes, SemesterName sem) {
        requireNonNull(moduleCodes);
        requireNonNull(sem);
        assert moduleCodes.size() > 0;
        this.sem = sem;
        this.moduleCodes = moduleCodes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getSemester(this.sem) == null) {
            throw new CommandException(MESSAGE_SEMESTER_DOES_NOT_EXIST);
        }

        if (model.getSemester(this.sem).isBlocked()) {
            throw new CommandException(MESSAGE_SEMESTER_BLOCKED);
        }

        for (Semester sem : model.getActiveStudyPlan().getSemesters()) {
            for (String moduleCode : this.moduleCodes) {
                if (sem.hasModule(moduleCode)) {
                    throw new CommandException(MESSAGE_DUPLICATE_MODULE_STUDY_PLAN);
                }
            }
        }

        StringBuilder resultString = new StringBuilder();

        for (String moduleCode : moduleCodes) {
            if (model.semesterHasModule(moduleCode, this.sem)) {
                resultString.append(String.format(MESSAGE_DUPLICATE_MODULE, moduleCode, this.sem));
                resultString.append("\n");
                continue;
            }

            if (!model.isValidModuleCode(moduleCode)) {
                resultString.append(String.format(MESSAGE_MODULE_DOES_NOT_EXIST, moduleCode));
                resultString.append("\n");
                continue;
            }

            model.addModule(moduleCode, sem);
            resultString.append(String.format(MESSAGE_SUCCESS, moduleCode, this.sem));
            resultString.append("\n");
        }
        resultString.setLength(resultString.length() - 1);
        model.addToHistory();
        return new CommandResult(resultString.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && sem.equals(((AddModuleCommand) other).sem)
                && moduleCodes.equals(((AddModuleCommand) other).moduleCodes));
    }
}
