package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_IMAGE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;

public class SetImageCommand extends Command {
    public static final String COMMAND_WORD = "set-image";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the profile image of "
            + "member identified by the id used in the displayed member list\n"
            + "Parameters: "
            + "[" + PREFIX_MEMBER_ID + "ID ] "
            + "[" + PREFIX_MEMBER_IMAGE + "URL] "
            + "Example: " + COMMAND_WORD + " mi/GS im/C:\\Users\\Lynn\\Desktop\\Y2S1\\CS2103T\\tP\\tP helppp\\src\\" +
            "main\\resources\\images\\DaUser.png ";

    public static final String MESSAGE_SUCCESS = "Image set for user";

    private final MemberId memId;
    private final String imageURL;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public SetImageCommand(MemberId id, String url) {
        requireNonNull(id, url);
        memId = id;
        imageURL = url;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMembersList();

        boolean contains = false;
        Member memberInvolved = null;

        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getId().equals(memId)) {
                contains = true;
                memberInvolved = lastShownList.get(i);
                break;
            }
        }

        if (!contains) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_ID);
        }

        memberInvolved.setImage(imageURL);

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetImageCommand // instanceof handles nulls
                && memId.equals(((SetImageCommand) other).memId)
                && imageURL.equals(((SetImageCommand) other).imageURL));
    }
}
