package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.PrefixType;

/**
 * Lists every {@link Mentor} in Alfred.
 */
public class ListMentorCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all mentors";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all of the mentors.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        this.displayMentors(model);

        model.updateHistory(this);
        return new CommandResult(MESSAGE_SUCCESS, PrefixType.M);
    }
}
