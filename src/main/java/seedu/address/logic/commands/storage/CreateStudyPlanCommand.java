package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModulesInfo;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;

/**
 * Creates a new studyPlan.
 */
public class CreateStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "newplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new studyPlan."
            + "Parameters: "
            + "PLAN_TITLE \n"
            + "Example: " + COMMAND_WORD + " "
            + "newplan NOC halfyear";

    public static final String MESSAGE_SUCCESS = "New studyPlan added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDYPLAN = "This studyPlan already exists in the module planner";

    private final String studyPlanName;

    /**
     * Creates an CreateStudyPlanCommand to add the specified {@code StudyPlan}
     */
    public CreateStudyPlanCommand(String studyPlanName) {
        requireNonNull(studyPlanName);
        this.studyPlanName = studyPlanName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModulesInfo modulesInfo = model.getModulesInfo();
        StudyPlan studyPlanToAdd = new StudyPlan(new Title(studyPlanName), modulesInfo,
                model.getModulePlanner().getCurrentSemester());

        if (model.hasStudyPlan(studyPlanToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDYPLAN);
        }

        model.addStudyPlan(studyPlanToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, studyPlanName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateStudyPlanCommand // instanceof handles nulls
                && studyPlanName.equals(((CreateStudyPlanCommand) other).studyPlanName));
    }
}
