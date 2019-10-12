package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Creates a new studyPlan.
 */
public class CreateStudyPlanCommand extends Command {

    // TODO: must be modified accordingly (this was directly refactored from the original address book)

    public static final String COMMAND_WORD = "newplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new studyPlan."
            + "Parameters: "
            + "PLAN_TITLE \n"
            + "Example: " + COMMAND_WORD + " "
            + "newplan NOC halfyear";

    public static final String MESSAGE_SUCCESS = "New studyPlan added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDYPLAN = "This studyPlan already exists in the module planner";

    private final StudyPlan toAdd;

    /**
     * Creates an CreateStudyPlanCommand to add the specified {@code StudyPlan}
     */
    public CreateStudyPlanCommand(StudyPlan studyPlan) {
        requireNonNull(studyPlan);
        toAdd = studyPlan;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudyPlan(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDYPLAN);
        }

        model.addStudyPlan(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateStudyPlanCommand // instanceof handles nulls
                && toAdd.equals(((CreateStudyPlanCommand) other).toAdd));
    }
}
