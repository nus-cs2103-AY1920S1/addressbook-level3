package seedu.pluswork.logic.commands.member;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_IMAGE;

import java.util.Arrays;
import java.util.List;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.member.MemberNameContainsKeywordsPredicate;

public class SetImageCommand extends Command {
    public static final String COMMAND_WORD = "set-image";
    public static final String PREFIX_USAGE = PREFIX_MEMBER_ID + " " + PREFIX_MEMBER_IMAGE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the profile image of "
            + "member identified by the id used in the displayed member list\n"
            + "Parameters: "
            + PREFIX_MEMBER_ID + "MEMBER_ID"
            + PREFIX_MEMBER_IMAGE + "IMAGE_PATH"
            + "Example: " + COMMAND_WORD + " mi/GS im/C:\\Desktop\\images\\DaUser.png ";

    public static final String MESSAGE_SUCCESS = "Image set for user";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already has this image as profile picture.";
    public static final String MESSAGE_NO_IMAGE_FOUND = "The file path entered does not have an image.";

    private final MemberId memId;
    private final String imageURL;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public SetImageCommand(MemberId id, String imageURL) {
        requireNonNull(id);
        requireNonNull(imageURL);

        this.memId = id;
        this.imageURL = imageURL;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMembersList();

        boolean contains = false;
        Member mem = null;

        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getId().equals(memId)) {
                contains = true;
                mem = lastShownList.get(i);
                break;
            }
        }

        if (!contains) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_ID);
        }

        Member editedMember = new Member(mem.getName(), mem.getId(), mem.getTags(), imageURL);

        if (!mem.isSameMember(editedMember) && model.hasMember(editedMember)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        }

        if (editedMember.getImage().errorProperty().getValue()) {
            throw new CommandException(MESSAGE_NO_IMAGE_FOUND);
        }

        model.setMember(mem, editedMember);

        model.updateFilteredMembersList(new MemberNameContainsKeywordsPredicate(Arrays.asList(editedMember.getName().fullName.split(" "))));
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedMember));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetImageCommand)) {
            return false;
        }

        // state check
        SetImageCommand e = (SetImageCommand) other;
        return memId.equals(e.memId)
                && imageURL.equals(e.imageURL);
    }

}


