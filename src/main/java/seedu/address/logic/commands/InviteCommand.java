package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Invites a person to the activity.
 */
public class InviteCommand extends Command {

    public static final String COMMAND_WORD = "invite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Invites participant(s) into current viewed Activity.\n"
            + "Parameters: "
            + "[" + PREFIX_PARTICIPANT + "PARTICIPANT]...\n"
            + "Example: invite p/Ben p/David";

    public static final String MESSAGE_RESULT =
            "Invited the following participants successfully to the activity:\n%s\n%s";

    public static final String MESSAGE_DUPLICATE_PERSON_IN_ACTIVITY =
            "Unable to invite \"%s\" as he/she already exists in the activity.";

    public static final String MESSAGE_NON_UNIQUE_SEARCH_RESULT =
            "Unable to invite \"%s\" as he/she has no unique search result in the contacts.";

    public static final String MESSAGE_NO_ONE_TO_INVITE =
            "There is no one to invite.";

    public static final String MESSAGE_NO_VIEWED_ACTIVITY = "There is no viewed activity currently.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "\"%s\" has duplicate entries, the first one will be added.";

    private final List<String> peopleToInvite;

    /**
     * Creates an InviteCommand to invite the specified {@code Person(s)}
     */
    public InviteCommand(List<String> peopleToInvite) {
        requireNonNull(peopleToInvite);
        this.peopleToInvite = peopleToInvite;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (peopleToInvite.size() == 0) {
            throw new CommandException(MESSAGE_NO_ONE_TO_INVITE);
        }

        if (model.getContext().getType() != Context.Type.VIEW_ACTIVITY) {
            throw new CommandException(MESSAGE_NO_VIEWED_ACTIVITY);
        }

        Activity activityToInviteTo = model.getContext().getActivity().get();
        ArrayList<Integer> participantIds = activityToInviteTo.getParticipantIds();

        List<String> keywords;
        List<Person> findResult;
        List<Integer> idsToInvite = new ArrayList<>();
        StringBuilder messageNotInvited = new StringBuilder();
        StringBuilder messageInvited = new StringBuilder();

        for (String name : peopleToInvite) {

            keywords = Arrays.asList(name.split(" "));
            NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(keywords);

            try {
                findResult = model.findPersonAll(predicate);
                assert findResult != null : "List of people in contacts should not be null.";
                if (findResult.size() != 1) {
                    throw new CommandException(String.format(MESSAGE_NON_UNIQUE_SEARCH_RESULT, name));
                }
            } catch (CommandException e) {
                messageNotInvited.append(e.getMessage() + "\n");
                continue;
            }

            assert findResult.size() == 1 : "There should only be 1 match";

            Person personToInvite = findResult.get(0);
            Integer idOfPersonToInvite = personToInvite.getPrimaryKey();

            try {
                if (activityToInviteTo.hasPerson(idOfPersonToInvite)) {
                    throw new CommandException(String.format(MESSAGE_DUPLICATE_PERSON_IN_ACTIVITY, name));
                }
                if (idsToInvite.contains(idOfPersonToInvite)) {
                    throw new CommandException(String.format(MESSAGE_DUPLICATE_ENTRY, name));
                }
                idsToInvite.add(idOfPersonToInvite);
                messageInvited.append(personToInvite.getName() + "\n");
            } catch (CommandException e) {
                messageNotInvited.append(e.getMessage() + "\n");
                continue;
            }
        }

        for (Integer id : idsToInvite) {
            activityToInviteTo.invite(id);
        }

        model.updateFilteredPersonList(x -> participantIds.contains(x.getPrimaryKey()));

        return new CommandResult(String.format(MESSAGE_RESULT, messageInvited, messageNotInvited));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InviteCommand // instanceof handles nulls
                && peopleToInvite.containsAll(((InviteCommand) other).peopleToInvite)
                && ((InviteCommand) other).peopleToInvite.containsAll(peopleToInvite));
    }

}
