package seedu.address.logic.commands.datamanagement;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.PriorityTagType;

/**
 * Adds a priority tag to a study plan.
 */
public class TagStudyPlanCommand extends Command {

    public static final String COMMAND_WORD = "tagstudyplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Adds a tag of the specified priority to "
            + "the study plan of the specified index. "
            + "Parameters: "
            + "PRIORITY_TYPE "
            + "STUDY_PLAN_INDEX \n"
            + "Example: "
            + "tagstudyplan high 1";

    public static final String MESSAGE_SUCCESS = "Tag %1$s has been added to %2$s";
    public static final String MESSAGE_TAG_ALREADY_EXISTS = "%1%s already has the tag %2$s";

    private String priorityLevel;
    private int index;

    /**
     * Creates a {@code TagStudyPlanCommand} to add a priority tag with the given priority level to
     * the study plan at the specified index.
     */
    public TagStudyPlanCommand(String priorityLevel, int index) {
        requireAllNonNull(priorityLevel, index);
        this.priorityLevel = priorityLevel;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        PriorityTag priorityTag = new PriorityTag(PriorityTagType.valueOf(priorityLevel));

        if (model.spContainsStudyPlanTag(priorityLevel, index)) {
            throw new CommandException(String.format(MESSAGE_TAG_ALREADY_EXISTS, model.getStudyPlan(index),
                    priorityTag));
        }

        model.addStudyPlanTagToSp(priorityTag, index);
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, priorityTag, model.getStudyPlan(index)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagStudyPlanCommand // instanceof handles nulls
                && priorityLevel.equals(((TagStudyPlanCommand) other).priorityLevel))
                && index == (((TagStudyPlanCommand) other).index); // state check
    }

}
