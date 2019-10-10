package seedu.address.logic.commands.tag;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.DefaultTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class ViewDefaultTagsCommand extends Command {

    public static final String COMMAND_WORD = "viewdefaulttags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all default tags. "
        + "Example: "
        + "viewdefaulttags";

    public static final String MESSAGE_SUCCESS = "All default tags shown %1$s.";

    /**
     * Creates an {@code ViewDefaultTagsCommand} to show all default tags in the study plan.
     */
    public ViewDefaultTagsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StudyPlan activeStudyPlan = model.getActiveStudyPlan();
        UniqueTagList uniqueTagList = activeStudyPlan.getTags();

        Set<DefaultTag> defaultTags = getDefaultTags(uniqueTagList);

        return new CommandResult(String.format(MESSAGE_SUCCESS, defaultTags));
    }

    private Set<DefaultTag> getDefaultTags(UniqueTagList uniqueTagList) {
        Iterator<Tag> tagIterator = uniqueTagList.iterator();
        Set<DefaultTag> defaultTags = new HashSet<DefaultTag>();
        while (tagIterator.hasNext()) {
            Tag currentTag = tagIterator.next();
            if (currentTag.isDefault()) {
                defaultTags.add((DefaultTag) currentTag);
            }
        }
        return defaultTags;
    }

}
