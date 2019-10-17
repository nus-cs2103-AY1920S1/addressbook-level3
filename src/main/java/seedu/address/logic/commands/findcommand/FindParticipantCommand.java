package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;

/**
 * Implements the find command for participants.
 */
public class FindParticipantCommand extends FindCommand {
    public static final String COMMAND_WORD = "findParticipant";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the participant by the name "
            + "given. Parameters: name to search for "
            + "Example: " + COMMAND_WORD + " n/John Doe";
    public static final String MESSAGE_SUCCESS = "Successfully ran the find command.";

    private String name;

    public FindParticipantCommand(String name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Participant> results = model.findParticipantByName(name);
        listResults(results, PrefixType.P);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
