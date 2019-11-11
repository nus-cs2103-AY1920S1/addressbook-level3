package seedu.pluswork.logic.commands.multiline;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;

import java.time.LocalDateTime;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.member.MemberId;

public class AddDCommand extends Command {
    public static final String COMMAND_WORD = "add-d";
    public static final String PREFIX_USAGE = PREFIX_DEADLINE + " " + PREFIX_MEMBER_ID;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds deadline & member to task. "
            + "Parameters: "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_MEMBER_ID + "MEMBERID"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DEADLINE + "10-10-2019 18:00"
            + PREFIX_MEMBER_ID + "AR";

    public static final String MESSAGE_SUCCESS = "Details added: %1$s";
    public static final String MESSAGE_DUPLICATE_DETAILS = "These details already exists in the address book";
    public static final String MESSAGE_INDEX_EXCEEDED = "The index entered for tasks is invalid";
    public static final String MESSAGE_MEMBERID_INVALID = "The member Id entered is invalid";

    private final LocalDateTime dateTime;
    private final MemberId memId;

    /**
     * Creates an AddDCommand to add the specified details
     */
    public AddDCommand(LocalDateTime dateTime, MemberId memId) {
        requireAllNonNull(dateTime, memId);
        this.dateTime = dateTime;
        this.memId = memId;
    }

    public AddDCommand(LocalDateTime dateTime) {
        requireAllNonNull(dateTime);
        this.dateTime = dateTime;
        this.memId = new MemberId("NIL");
    }

    public AddDCommand(MemberId memId) {
        requireAllNonNull(memId);
        this.dateTime = LocalDateTime.MIN;
        this.memId = memId;
    }

    public LocalDateTime getDeadline() {
        return dateTime;
    }

    public MemberId getMemId() {
        return memId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if ((!memId.equals(new MemberId("NIL"))) && (!model.hasMemberId(memId))) {
            throw new CommandException(MESSAGE_MEMBERID_INVALID);
        }

        return new CommandResult("final2");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDCommand // instanceof handles nulls
                && memId.equals(((AddDCommand) other).memId)
                && dateTime.equals(((AddDCommand) other).dateTime));
    }
}
