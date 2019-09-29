package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class EditTeamCommand extends EditCommand {

    /* Possible Fields */
    private EditTeamDescriptor editTeamDescriptor;

    public EditTeamCommand(Id id, EditTeamDescriptor editTeamDescriptor) {
        super(id);
        requireNonNull(editTeamDescriptor);
        this.editTeamDescriptor = editTeamDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // see EditIssueCommand

        return new CommandResult("");
    }

    private Team createEditedTeam(Team teamToEdit, EditTeamDescriptor editTeamDescriptor) {
        // Set each field to updated value
        // See EditCommand#EditPersonDescriptor for more context

//        return new Team(/* Necessary Fields */);
        return null;
    }

    public static class EditTeamDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
