package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Gets details about a module from NusMods
 */
public class ShowNusModCommand extends Command {
    public static final String COMMAND_WORD = "showmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_MODULE_CODE + "MODULE_CODE "
            + "[" + PREFIX_ACAD_YEAR + "ACADEMIC_YEAR] "
            + "[" + PREFIX_SEMESTER + "SEMESTER]\n";

    public static final String MESSAGE_MODULE_NOT_FOUND = "Unable to find module";

    private final ModuleCode moduleCode;
    private final ShowNusModCommandOptions options;

    public ShowNusModCommand(ModuleCode moduleCode, ShowNusModCommandOptions options) {
        this.moduleCode = moduleCode;
        this.options = options;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        AcadYear acadYear = options.getAcadYear().orElse(model.getAppSettings().getAcadYear());
        SemesterNo semesterNo = options.getSemesterNo().orElse(model.getAppSettings().getSemesterNo());

        try {
            Module module = model.findModuleFromAllSources(acadYear, moduleCode);
            String result = module.toString() + "\n";
            result += module.getDescription().toString() + "\n";
            result += module.getSemester(semesterNo).toString();
            return new CommandResult(result);
        } catch (ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof ShowNusModCommand)) {
            return false;
        } else if (((ShowNusModCommand) command).moduleCode.equals(this.moduleCode)
                && ((ShowNusModCommand) command).options.equals(this.options)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Contains optional arguments for executing the ShowNusModCommand.
     */
    public static class ShowNusModCommandOptions {
        private AcadYear acadYear;
        private SemesterNo semesterNo;

        public ShowNusModCommandOptions() {}

        public Optional<AcadYear> getAcadYear() {
            return Optional.ofNullable(acadYear);
        }

        public void setAcadYear(AcadYear acadYear) {
            this.acadYear = acadYear;
        }

        public Optional<SemesterNo> getSemesterNo() {
            return Optional.ofNullable(semesterNo);
        }

        public void setSemesterNo(SemesterNo semesterNo) {
            this.semesterNo = semesterNo;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ShowNusModCommandOptions)) {
                return false;
            }

            // state check
            ShowNusModCommandOptions o = (ShowNusModCommandOptions) other;

            return getAcadYear().equals(o.getAcadYear())
                    && getSemesterNo().equals(o.getSemesterNo());
        }
    }
}
