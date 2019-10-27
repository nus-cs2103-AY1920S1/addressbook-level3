package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContextType;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

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

    public static final String MESSAGE_NO_ONE_TO_DISINVITE = "There is no one to disinvite.";

    public static final String MESSAGE_NO_VIEWED_ACTIVITY = "There is no viewed activity currently.";

    public static final String MESSAGE_INVALID_PERSON = "Blank name encountered.";

    public static final String MESSAGE_NON_UNIQUE_SEARCH_RESULT =
            "Unable to disinvite \"%s\" as he/she has no unique search result in the current activity.";

    public static final String MESSAGE_DUPLICATE_ENTRY = "\"%s\" has duplicate entries, the first one will be added.";

    public static final String MESSAGE_SUCCESS_INVITE = "Disinvited \"%s\" into the activity.";

    public static final String MESSAGE_RESULT = "\n%s\n%s";


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
        model.updateFilteredPersonList(x -> participantIds.contains(x.getPrimaryKey()));
        List<Person> searchScope = model.getFilteredPersonList();

        List<String> keywords;
        List<Person> findResult;
        List<Integer> idsToRemove = new ArrayList<>();
        StringBuilder warningMessage = new StringBuilder();
        StringBuilder successMessage = new StringBuilder();

        for (String name : peopleToDisinvite) {
            if (name.trim().equals("")) {
                warningMessage.append(MESSAGE_INVALID_PERSON + "\n");
                continue;
            }

            keywords = Arrays.asList(name.split(" "));
            NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(keywords);

            findResult = searchScope.stream().filter(predicate).collect(Collectors.toList());
            assert findResult != null : "List of people should not be null.";

            if (findResult.size() != 1) { //not in activity or duplicate
                String warning = String.format(MESSAGE_NON_UNIQUE_SEARCH_RESULT, name);
                warningMessage.append(warning).append("\n");
                continue;
            }

            Person personToDisinvite = findResult.get(0);
            Integer idOfPersonToDisinvite = personToDisinvite.getPrimaryKey();

            if (idsToRemove.contains(idOfPersonToDisinvite)) {
                String warning = String.format(MESSAGE_DUPLICATE_ENTRY, name);
                warningMessage.append(warning).append("\n");
                continue;
            }

            idsToRemove.add(idOfPersonToDisinvite);
            successMessage.append(String.format(MESSAGE_SUCCESS_INVITE, personToDisinvite.getName()) + "\n");



        }

        for (Integer id : idsToRemove) {
            activityToDisinviteFrom.disinvite(id);
        }

        model.updateFilteredPersonList(x -> participantIds.contains(x.getPrimaryKey()));

        return new CommandResult(String.format(MESSAGE_RESULT, successMessage, warningMessage));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisinviteCommand // instanceof handles nulls
                && peopleToDisinvite.containsAll(((DisinviteCommand) other).peopleToDisinvite)
                && ((DisinviteCommand) other).peopleToDisinvite.containsAll(peopleToDisinvite));
    }
}
