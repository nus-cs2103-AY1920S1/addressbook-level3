package seedu.pluswork.logic.commands.member;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;

import java.util.List;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;

/**
 * Deletes a member identified using it's displayed ID from the project dashboard.
 */
public class DeleteMemberCommand extends Command {

    public static final String COMMAND_WORD = "remove-member";
    public static final String PREFIX_USAGE = PREFIX_MEMBER_ID.getPrefix();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the member identified by the index used in the displayed task list.\n"
            + "Parameters: " + PREFIX_MEMBER_ID + " MEMBER ID (must be an alphanumeric string)\n"
            + "Example: " + COMMAND_WORD + "mi/JD";

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
            if (lastShownList.get(i).getId().equals(targetId)) {
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
