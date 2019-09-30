package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;

public class AddMentorCommand extends AddCommand {

    /* Possible Fields: */
    private Mentor mentor;

    public AddMentorCommand(Mentor mentor) {
        requireNonNull(mentor);
        this.mentor = mentor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // See AddIssueCommand

        return new CommandResult("");
    }

}
