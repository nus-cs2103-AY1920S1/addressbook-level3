package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Team;

/**
 * Edits a {@link Team} in Alfred.
 */
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

    /**
     * Creates and returns a new {@code Team} with the details {@code teamToEdit}
     * edited with {@code editTeamDescriptor}.
     *
     * @param teamToEdit {@code Team} that will be updated.
     * @param editTeamDescriptor Descriptor with the details to edit {@code teamToEdit}.
     * @return Updated {@code Team}.
     */
    private Team createEditedTeam(Team teamToEdit, EditTeamDescriptor editTeamDescriptor) {
        // Set each field to updated value
        // See EditCommand#EditPersonDescriptor for more context

        // return new Team(/* Necessary Fields */);
        return null;
    }

    /**
     * Stores the details to edit the {@code Team} with. Each non-empty field value will replace the
     * corresponding field value of the {@code Team}.
     */
    public static class EditTeamDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
