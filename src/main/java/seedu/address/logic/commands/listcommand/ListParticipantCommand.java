package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;

/**
 * Lists every {@link Participant} in Alfred.
 */
public class ListParticipantCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all participants";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all of the participants.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        this.displayEntities(model, PrefixType.P);
        model.resetFilteredLists();
        model.updateHistory(this);

        return new CommandResult(MESSAGE_SUCCESS, CommandType.P);
    }
}
