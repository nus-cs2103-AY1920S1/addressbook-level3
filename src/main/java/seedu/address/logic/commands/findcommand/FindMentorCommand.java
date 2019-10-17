package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.PrefixType;

/**
 * Implements the find command for mentors.
 */
public class FindMentorCommand extends FindCommand {
    public static final String COMMAND_WORD = "findMentor";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the mentor by the name "
            + "given. Parameters: name to search for "
            + "Example: " + COMMAND_WORD + " n/John Doe";
    public static final String MESSAGE_SUCCESS = "Successfully ran the find command.";

    private String name;

    public FindMentorCommand(String name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Mentor> results = model.findMentorByName(name);
        listResults(results, PrefixType.P);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
