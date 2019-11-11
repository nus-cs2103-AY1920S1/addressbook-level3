package seedu.pluswork.logic.commands.member;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_TAG;
import static seedu.pluswork.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import java.util.List;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.member.Member;

/**
 * Adds a task to the address book.
 */
public class AddMemberCommand extends Command {
    public static final String COMMAND_WORD = "add-member";
    public static final String PREFIX_USAGE = PREFIX_MEMBER_NAME + " " + PREFIX_MEMBER_ID + " " + PREFIX_MEMBER_TAG;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member to the address book. "
            + "Parameters: "
            + PREFIX_MEMBER_NAME + "NAME "
            + PREFIX_MEMBER_ID + "ID "
            + PREFIX_MEMBER_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER_NAME + "John Doe "
            + PREFIX_MEMBER_ID + "JD "
            + PREFIX_MEMBER_TAG + "friends "
            + PREFIX_MEMBER_TAG + "to finish Dashboard";

    public static final String MESSAGE_SUCCESS = "New member added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in the address book";
    public static final String MESSAGE_DUPLICATE_ID = "This ID is already in use, please choose another member id!";

    private final Member toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddMemberCommand(Member member) {
        requireNonNull(member);
        toAdd = member;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredMembersList(PREDICATE_SHOW_ALL_MEMBERS);
        List<Member> members = model.getFilteredMembersList();

        if (model.hasMember(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        }

        for (Member mem : members) {
            if (mem.getId().equals(toAdd.getId())) {
                throw new CommandException(MESSAGE_DUPLICATE_ID);
            }
        }

        model.addMember(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMemberCommand // instanceof handles nulls
                && toAdd.equals(((AddMemberCommand) other).toAdd));
    }
}
