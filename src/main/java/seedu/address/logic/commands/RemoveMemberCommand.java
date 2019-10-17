package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;

import static java.util.Objects.requireNonNull;

public class RemoveMemberCommand extends Command {
    public static final String COMMAND_WORD = "removeMember";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the member from the working project.\n"
            + "Parameters: n/NAME"
            + "Example: " + COMMAND_WORD + " n/John doe";

    public static final String MESSAGE_REMOVE_MEMBER_SUCCESS = "Removed %1$s from %2$s";

    private final String targetMember;

    public RemoveMemberCommand(String targetMember) {
        this.targetMember = targetMember;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Project projectToEdit = model.getWorkingProject().get();
        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(),projectToEdit.getTasks(), projectToEdit.getFinance());
        editedProject.getMembers().addAll(projectToEdit.getMembers());
        editedProject.deleteMember(targetMember);
        model.setProject(projectToEdit, editedProject);

        return new CommandResult(String.format(MESSAGE_REMOVE_MEMBER_SUCCESS, targetMember,
                editedProject.getTitle().toString()));
    }
}
