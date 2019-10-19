package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

public class InviteCommand extends Command {

    public static final String COMMAND_WORD = "invite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Invites participant(s) into current viewed Activity.\n"
            + "Parameters: "
            + "[" + PREFIX_PARTICIPANT + "PARTICIPANT]...\n"
            + "Example: invite p/Ben p/David";

    public static final String MESSAGE_INVITE_PERSON_SUCCESS = "Invited Person: %1$s";
    public static final String MESSAGE_NO_VIEWED_ACTIVITY = "There is no viewed activity currently.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This contact already exists in the activity.";

    private final List<String> peopleToInvite;

    public InviteCommand(List<String> peopleToInvite) {
        requireNonNull(peopleToInvite);
        this.peopleToInvite = peopleToInvite;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getContext().getType() != Context.Type.VIEW_ACTIVITY) {
            throw new CommandException(MESSAGE_NO_VIEWED_ACTIVITY);
        }

        Activity activityToInviteTo = model.getContext().getActivity().get();
        ArrayList<Integer> participantIds = activityToInviteTo.getParticipantIds();
        model.updateFilteredPersonList(x -> participantIds.contains(x.getPrimaryKey()));
        List<Person> members = model.getFilteredPersonList();

        List<String> keywords;
        List<Person> findResult;
        for (String name : peopleToInvite) {
            keywords = Arrays.asList(name.split(" "));



        }




        //////


        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InviteCommand // instanceof handles nulls
                && peopleToInvite.equals(((InviteCommand) other).peopleToInvite));
    }

}
