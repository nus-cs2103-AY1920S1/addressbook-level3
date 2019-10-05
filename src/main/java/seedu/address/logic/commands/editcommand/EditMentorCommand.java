package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;

/**
 * Edits a {@link Mentor} in Alfred.
 */
public class EditMentorCommand extends EditCommand {

    /* Possible Fields */
    private EditMentorDescriptor editMentorDescriptor;

    public EditMentorCommand(Id id, EditMentorDescriptor editMentorDescriptor) {
        super(id);
        requireNonNull(editMentorDescriptor);
        this.editMentorDescriptor = editMentorDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // see EditIssueCommand

        return new CommandResult("");
    }

    /**
     * Creates and returns a new {@code Mentor} with the details of {@code mentorToEdit}
     * edited with {@code editMentorDescriptor}.
     *
     * @param mentorToEdit {@code Mentor} that will be updated.
     * @param editMentorDescriptor Descriptor with the details to edit {@code mentorToEdit}.
     * @return Updated {@code Mentor}.
     */
    private Mentor createEditedMentor(Mentor mentorToEdit, EditMentorDescriptor editMentorDescriptor) {
        // Set each field to updated value
        // See EditCommand#EditPersonDescriptor for more context

        // return new Mentor(/* Necessary Fields */);
        return null;
    }

    /**
     * Stores the details to edit the {@code Mentor} with. Each non-empty field value will replace the
     * corresponding field value of the {@code Mentor}.
     */
    public static class EditMentorDescriptor extends EditEntityDescriptor {

        /*
         * Implement the remaining attributes
         */

    }

}
