package seedu.address.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.reminders.conditions.TagsCondition;
import seedu.address.model.tag.Tag;

/**
 * Creates a (@Code TagsCondition) when executed.
 */
public class AddTagsConditionCommand extends Command {

    public static final String COMMAND_WORD = "addTagsCondition";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a TagsCondition to the conditions list. \n"
            + "Parameters: "
            + "TAG...(No space between keywords, only a comma)\n"
            + "Example: "
            + COMMAND_WORD
            + " Food,NUS\n";

    public static final String MESSAGE_SUCCESS = "New KeyWordsCondition added: %1$s";

    private List<Tag> tags;

    public AddTagsConditionCommand(List<Tag> tags) {
        this.tags = tags;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        TagsCondition condition = new TagsCondition(tags);
        model.addCondition(condition);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, condition));
        commandResult.showConditionPanel();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagsConditionCommand // instanceof handles nulls
                && tags.equals(((AddTagsConditionCommand) other).tags));
    }
}
