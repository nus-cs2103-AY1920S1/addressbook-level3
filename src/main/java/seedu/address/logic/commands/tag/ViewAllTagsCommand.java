package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.UniqueTagList;

public class ViewAllTagsCommand extends Command {

    public static final String COMMAND_WORD = "viewalltags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all tags in the study plan. "
        + "Example: "
        + "viewalltags";

    public static final String MESSAGE_SUCCESS = "All tags shown %1$s.";

    /**
     * Creates an {@code ViewAllTagsCommand} to show all tags in the study plan.
     */
    public ViewAllTagsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        UniqueTagList uniqueTagList = activeStudyPlan.getTags();

        return new CommandResult(String.format(MESSAGE_SUCCESS, uniqueTagList));
    }

}
