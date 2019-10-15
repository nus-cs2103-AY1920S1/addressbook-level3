package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;

/**
 * Deletes a member identified using it's displayed ID from the project dashboard.
 */
public class DeleteMemberCommand extends Command {

    public static final String COMMAND_WORD = "remove member";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the member identified by the index used in the displayed task list.\n"
            + "Parameters: ID (must be an alphanumeric string)\n"
            + "Example: " + COMMAND_WORD + " JD";

    public static final String MESSAGE_DELETE_MEMBER_SUCCESS = "Deleted Member: %1$s";

    private final MemberId targetId;

    public DeleteMemberCommand(MemberId targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMembersList();

        boolean contains = false;
        Member memberToDelete = null;

        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getId() == targetId) {
                contains = true;
                memberToDelete = lastShownList.get(i);
                break;
            }
        }

        if (!contains) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_ID);
        }

        model.deleteMember(memberToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEMBER_SUCCESS, memberToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMemberCommand // instanceof handles nulls
                && targetId.equals(((DeleteMemberCommand) other).targetId)); // id check
    }
}
