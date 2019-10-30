package seedu.address.logic.commands.global;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.model.Model;

/**
 * Lists all tags in the StudyBuddy Application to the user.
 */
public class ListTagCommand extends Command {
    public static final String COMMAND_WORD = "list_tag";

    public static final String MESSAGE_SUCCESS = "Listing all tags";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        return new GlobalCommandResult(MESSAGE_SUCCESS);
    }
}
