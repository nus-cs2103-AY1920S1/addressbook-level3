package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.tag.Tag;

/**
 * Represents the command that list all the tags in the flashcard list.
 */
public class ListTagCommand extends Command {

    public static final String COMMAND_WORD = "listtag";

    public static final String MESSAGE_SUCCESS = "Listed all tags in the system "
        + "(If no tag names are shown below, it means you have not add any tags before.):\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String tagNameList = "";
        for (Tag tag : model.getAllSystemTags()) {
            tagNameList = tagNameList + tag.tagName + "\n";
        }
        return new CommandResult(MESSAGE_SUCCESS + tagNameList);
    }
}
