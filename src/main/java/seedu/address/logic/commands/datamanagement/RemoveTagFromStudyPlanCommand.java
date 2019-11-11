package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;
import seedu.address.model.tag.PriorityTag;

/**
 * Removes a priority tag from the study plan.
 */
public class RemoveTagFromStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "removepriority";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Removing the priority tag from a study plan";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the priority tag "
            + "from the study plan of the specified index. "
            + "Parameters: "
            + "STUDY_PLAN_INDEX \n"
            + "Example: "
            + "removepriority 1";

    public static final String MESSAGE_SUCCESS = "Priority tag %1$s has been removed from %2$s";
    public static final String MESSAGE_TAG_DOES_NOT_EXIST = "%1$s does not have any priority tag";
    public static final String MESSAGE_NO_SUCH_STUDYPLAN = "The study plan with this index does not exists!";

    private int index;

    /**
     * Creates a {@code RemoveTagFromStudyPlanCommand} to remove a priority tag with the given priority level from
     * the study plan at the specified index.
     */
    public RemoveTagFromStudyPlanCommand(int index) {
        requireNonNull(index);
        assert index > 0;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        try {
            model.getStudyPlan(index);
        } catch (StudyPlanNotFoundException e) {
            throw new CommandException(MESSAGE_NO_SUCH_STUDYPLAN);
        }

        if (!model.spContainsPriorityTag(index)) {
            throw new CommandException(String.format(MESSAGE_TAG_DOES_NOT_EXIST, model.getStudyPlan(index)));
        }

        PriorityTag priorityTag = model.getPriorityTagFromSp(index);

        model.removeStudyPlanTagFromSp(priorityTag, index);
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, priorityTag, model.getStudyPlan(index)),
                true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagFromStudyPlanCommand // instanceof handles nulls
                && index == (((RemoveTagFromStudyPlanCommand) other).index)); // state check
    }

}
