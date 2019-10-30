package seedu.address.logic.commands.datamanagement;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.PriorityTagType;

/**
 * Removes a priority tag from the study plan.
 */
public class RemoveTagFromStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "removesptag";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Removing a tag from a study plan";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Removes the tag of the specified priority "
            + "(high, medium, low) "
            + "from the study plan of the specified index. "
            + "Parameters: "
            + "PRIORITY_TYPE "
            + "STUDY_PLAN_INDEX \n"
            + "Example: "
            + "removesptag high 1";

    public static final String MESSAGE_SUCCESS = "Tag %1$s has been removed from %2$s";
    public static final String MESSAGE_TAG_DOES_NOT_EXIST = "%1$s does not have the tag %2$s";

    private String priorityLevel;
    private int index;

    /**
     * Creates a {@code RemoveTagFromStudyPlanCommand} to remove a priority tag with the given priority level from
     * the study plan at the specified index.
     */
    public RemoveTagFromStudyPlanCommand(String priorityLevel, int index) {
        requireAllNonNull(priorityLevel, index);
        this.priorityLevel = priorityLevel;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        PriorityTag priorityTag = new PriorityTag(PriorityTagType.valueOf(priorityLevel));

        if (!model.spContainsStudyPlanTag(priorityTag.getTagName(), index)) {
            throw new CommandException(String.format(MESSAGE_TAG_DOES_NOT_EXIST, model.getStudyPlan(index),
                    priorityTag));
        }

        model.removeStudyPlanTagFromSp(priorityTag, index);
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, priorityTag, model.getStudyPlan(index)),
                true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagFromStudyPlanCommand // instanceof handles nulls
                && priorityLevel.equals(((RemoveTagFromStudyPlanCommand) other).priorityLevel))
                && index == (((RemoveTagFromStudyPlanCommand) other).index); // state check
    }

}
