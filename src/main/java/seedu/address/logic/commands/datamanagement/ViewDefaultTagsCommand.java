package seedu.address.logic.commands.datamanagement;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.DefaultTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Shows all default tags.
 */
public class ViewDefaultTagsCommand extends Command {

    // not in parser yet

    public static final String COMMAND_WORD = "viewdefaulttags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all default tags. "
            + "Example: "
            + "viewdefaulttags";

    public static final String MESSAGE_SUCCESS = "All default tags shown \n%1$s.";

    /**
     * Creates an {@code ViewDefaultTagsCommand} to show all default tags in the active study plan.
     */
    public ViewDefaultTagsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UniqueTagList uniqueTagList = model.getTagsFromActiveSp();

        Set<DefaultTag> defaultTags = getDefaultTags(uniqueTagList);

        final String stringOfDefaultTags = defaultTags.stream()
            .map(item -> item.toString())
            .collect(joining("\n"));

        return new CommandResult(String.format(MESSAGE_SUCCESS, stringOfDefaultTags));
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
