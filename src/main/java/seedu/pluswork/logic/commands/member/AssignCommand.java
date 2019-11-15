package seedu.pluswork.logic.commands.member;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.List;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.task.Task;

/**
 * Assigns a status to tasks in the project.
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    public static final String PREFIX_USAGE = PREFIX_TASK_INDEX + " " + PREFIX_MEMBER_ID;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a member, identified by "
            + "by the memberId used in the displayed member list, to a task indicated "
            + "by the task index. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TASK_INDEX + "TASK_INDEX "
            + PREFIX_MEMBER_ID + "MEMBER_ID \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_INDEX + " 2 "
            + PREFIX_MEMBER_ID + "JD";

    public static final String MESSAGE_ASSIGN_SUCCESS = "Task set under member";
    public static final String MESSAGE_DUPLICATE_MAPPING = "This assignment already exists!";

    private final Index taskId;
    private final MemberId memberId;

    /**
     * @param taskId   of the task in the filtered task list to be added to member
     * @param memberId of the member involved
     */
    public AssignCommand(Index taskId, MemberId memberId) {
        requireNonNull(memberId);
        requireNonNull(taskId);

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
        Member memberToAdd = null;
        Integer memberIndex = null;

        for (int i = 0; i < lastShownMemberList.size(); i++) {
            if (lastShownMemberList.get(i).getId().equals(memberId)) {
                contains = true;
                memberToAdd = lastShownMemberList.get(i);
                memberIndex = i;
                break;
            }
        }

        if (!contains) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_ID);
        }

        Task involvedTask = lastShownTaskList.get(taskId.getZeroBased());
        TasMemMapping mappingToAdd = createMapping(taskId.getZeroBased(), memberIndex);

        if (model.hasMapping(mappingToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MAPPING);
        }

        model.addMapping(mappingToAdd);
        return new CommandResult(String.format(MESSAGE_ASSIGN_SUCCESS));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * where TaskStatus is updated to 'In Progress".
     */
    private static TasMemMapping createMapping(int taskIndex, int memberIndex) {
        return new TasMemMapping(taskIndex, memberIndex);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        // state check
        AssignCommand e = (AssignCommand) other;
        return memberId.equals(e.memberId)
                && taskId.equals(e.taskId);
    }
}
