package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContextType;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;

/**
 * Disinvites a person to the activity.
 */
public class DisinviteCommand extends Command{

    public static final String COMMAND_WORD = "disinvite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Disinvites participant(s) " +
            "from current viewed Activity.\n"
            + "Parameters: "
            + "[" + PREFIX_PARTICIPANT + "PARTICIPANT]...\n"
            + "Example: disinvite p/Ben p/David";

    private final List<String> peopleToDisinvite;

    /**
     * Creates a DisinviteCommand to disinvite the specified {@code Person(s)}
     */
    public DisinviteCommand(List<String> peopleToDisinvite) {
        requireNonNull(peopleToDisinvite);
        this.peopleToDisinvite = peopleToDisinvite;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (peopleToDisinvite.size() == 0) {
            throw new CommandException(MESSAGE_NO_ONE_TO_DISINVITE);
        }

        if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
            throw new CommandException(MESSAGE_NO_VIEWED_ACTIVITY);
        }

        Activity activityToDisinviteFrom = model.getContext().getActivity().get();
        ArrayList<Integer> participantIds = activityToDisinviteFrom.getParticipantIds();

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisinviteCommand // instanceof handles nulls
                && peopleToDisinvite.containsAll(((DisinviteCommand) other).peopleToDisinvite)
                && ((DisinviteCommand) other).peopleToDisinvite.containsAll(peopleToDisinvite));
    }
}
