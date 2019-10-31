package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;

/**
 * Lists every {@link Team} in Alfred.
 */
public class ListTeamCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all teams";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all of the teams.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        this.displayEntities(model, PrefixType.T);
        model.resetFilteredLists();
        model.updateHistory(this);
        model.recordCommandExecution(this.getCommandInputString());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.T);
    }
}
