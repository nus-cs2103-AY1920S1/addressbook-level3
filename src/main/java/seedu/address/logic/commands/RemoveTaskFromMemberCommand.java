package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.mapping.Mapping;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.member.MemberName;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * Removes a task that member was responsible for
 */
public class RemoveTaskFromMemberCommand extends Command {
    public static final String COMMAND_WORD = "fire-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a task indicated "
            + "by the index number used in the displayed task list, to the member indicated "
            + "by the member ID. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TASK_INDEX + "TASK_INDEX"
            + PREFIX_MEMBER_ID + "MEMBER_ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_INDEX + " 2 "
            + PREFIX_MEMBER_ID + " JD";

    public static final String MESSAGE_REMOVE_TASK_SUCCESS = "Removes task for member: %1$s";
    public static final String MESSAGE_INVALID_TASK_ID = "This task does not exist under member.";

    private final Index taskId;
    private final MemberId memberId;

    /**
     * @param taskId of the task in the filtered task list to be added to member
     * @param memberId of the member involved
     */
    public RemoveTaskFromMemberCommand(Index taskId, MemberId memberId) {
        requireNonNull(memberId);

        this.taskId = taskId;
        this.memberId = memberId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownTaskList = model.getFilteredTasksList();
        List<Member> lastShownMemberList = model.getFilteredMembersList();

        if (taskId.getZeroBased() >= lastShownTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        boolean contains = false;
        Member involvedMember = null;

        for (int i = 0; i < lastShownMemberList.size(); i++) {
            if (lastShownMemberList.get(i).getId() == memberId) {
                contains = true;
                involvedMember = lastShownMemberList.get(i);
                break;
            }
        }

        if (!contains) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_ID);
        }

        Task taskToRemove = lastShownTaskList.get(taskId.getZeroBased());
        Mapping mappingToRemove = createMapping(involvedMember, taskToRemove);
        model.deleteMapping(mappingToRemove);
        return new CommandResult(String.format(MESSAGE_REMOVE_TASK_SUCCESS, involvedMember));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * where TaskStatus is updated to 'In Progress".
     */
    private static Mapping createMapping(Member involvedMember, Task taskToAdd) {
        assert taskToAdd != null;
        assert involvedMember != null;

        return new Mapping(involvedMember, taskToAdd);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveTaskFromMemberCommand)) {
            return false;
        }

        // state check
        RemoveTaskFromMemberCommand e = (RemoveTaskFromMemberCommand) other;
        return memberId.equals(e.memberId)
                && taskId.equals(e.taskId);
    }
}
