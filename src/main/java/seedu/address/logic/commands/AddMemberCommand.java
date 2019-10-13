package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.member.Member;

/**
 * Adds a task to the address book.
 */
public class AddMemberCommand extends Command {
    public static final String COMMAND_WORD = "add member";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a member to the address book. "
            + "Parameters: "
            + PREFIX_MEMBER_NAME + "NAME "
            + "[" + PREFIX_MEMBER_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEMBER_NAME + "John Doe "
            + PREFIX_MEMBER_ID + "JD"
            + PREFIX_MEMBER_TAG + "friends "
            + PREFIX_MEMBER_TAG + "to finish Dashboard";

    public static final String MESSAGE_SUCCESS = "New member added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This member already exists in the address book";

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

        if (model.hasMember(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
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
