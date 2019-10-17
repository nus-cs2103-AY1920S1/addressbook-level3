package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ActivityBook;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;
import seedu.address.model.person.Person;

public class InviteCommand extends Command {

    public static final String COMMAND_WORD = "invite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Invites participant(s) into current viewed Activity.\n"
            + "Parameters: "
            + "[" + PREFIX_PARTICIPANT + "PARTICIPANT]...\n"
            + "Example: invite p/Ben p/David";

    public static final String MESSAGE_INVITE_PERSON_SUCCESS = "Invited Person: %1$s";
//    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This contact already exists in the activity.";

    private final Index index;
    private final List<String> participants;

    public InviteCommand(Index index, List<String> participants) {
        requireNonNull(index);
        requireNonNull(participants);
        this.index = index;
        this.participants = participants;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ActivityBook book = model.getActivityBook();
        List<Activity> activityList = book.getActivityList();

        if (index.getZeroBased() >= activityList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToInvite = activityList.get(index.getZeroBased());
        Title title = activityToInvite.getTitle();
        ArrayList<Integer> participantIds = activityToInvite.getParticipantIds();


        //////


        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InviteCommand // instanceof handles nulls
                && participants.equals(((InviteCommand) other).participants));
    }

}
