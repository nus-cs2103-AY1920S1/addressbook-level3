package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.ContextType;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Disinvites a person to the activity.
 */
public class DisinviteCommand extends Command {

    public static final String COMMAND_WORD = "disinvite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Disinvites participant(s) "
            + "from current viewed Activity.\n"
            + "Parameters: "
            + "[" + PREFIX_PARTICIPANT + "PARTICIPANT]...\n"
            + "Example: disinvite p/Ben p/David";

    public static final String MESSAGE_NO_ONE_TO_DISINVITE = "There is no one to disinvite.";

    public static final String MESSAGE_NO_VIEWED_ACTIVITY = "There is no viewed activity currently.";

    public static final String MESSAGE_INVALID_PERSON = "Blank name encountered.";

    public static final String MESSAGE_NON_UNIQUE_SEARCH_RESULT =
            "Unable to disinvite \"%s\" as he/she has no unique search result in the current activity.";

    public static final String MESSAGE_SUCCESS_DISINVITE = "Disinvited \"%s\" from the activity.";

    public static final String MESSAGE_RESULT = "%s\n%s";

    public static final String MESSAGE_RESULT_NONE_SUCCESS = "%s";

    public static final String MESSAGE_UNSUCCESSFUL_DISINVITE_HAS_EXPENSE = "Cannot disinvite \"%s\" from activity: "
            + "involved in expense(s).";

    public static final String MESSAGE_PERSON_NOT_FOUND_IN_ACTIVITY = "\"%s\" is not in Activity, "
            + "cannot be disinvited.";

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
        List<Integer> participantIds = activityToDisinviteFrom.getParticipantIds();
        model.updateFilteredPersonList(x -> participantIds.contains(x.getPrimaryKey()));
        List<Person> searchScope = model.getFilteredPersonList();

        List<String> keywords;
        List<Person> findResult;
        List<Integer> idsToRemove = new ArrayList<>();
        List<String> namesToRemove = new ArrayList<>();
        StringBuilder warningMessage = new StringBuilder();
        StringBuilder successMessage = new StringBuilder();

        for (String name : peopleToDisinvite) {
            if (name.trim().equals("")) {
                warningMessage.append(MESSAGE_INVALID_PERSON + "\n");
                continue;
            }

            Person personToDisinvite;
            Integer idOfPersonToDisinvite;

            Optional<Person> person = model.findPersonByName(name.trim());

            if (person.isPresent()) {
                personToDisinvite = person.get();
            } else {
                keywords = Arrays.asList(name.split(" "));
                NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(keywords);

                findResult = searchScope.stream().filter(predicate).collect(Collectors.toList());

                assert findResult != null : "List of people should not be null.";

                if (findResult.size() != 1) { //not in activity or duplicate
                    String warning = String.format(MESSAGE_NON_UNIQUE_SEARCH_RESULT, name);
                    warningMessage.append(warning).append("\n");
                    continue;
                }
                personToDisinvite = findResult.get(0);
            }

            idOfPersonToDisinvite = personToDisinvite.getPrimaryKey(); // id of person in the activity

            if (!activityToDisinviteFrom.hasPerson(idOfPersonToDisinvite)) {
                String warning = String.format(MESSAGE_PERSON_NOT_FOUND_IN_ACTIVITY, name);
                warningMessage.append(warning).append("\n");
                continue;
            }

            if (idsToRemove.contains(idOfPersonToDisinvite)) { // repeated entry
                continue;
            }

            idsToRemove.add(idOfPersonToDisinvite);
            namesToRemove.add(name);
        }

        for (int i = 0; i < idsToRemove.size(); i++) {
            Integer id = idsToRemove.get(i);
            String name = namesToRemove.get(i);
            activityToDisinviteFrom.disinvite(id);
            if (participantIds.contains(id)) { // not removed, has existing expenses
                String warning = String.format(MESSAGE_UNSUCCESSFUL_DISINVITE_HAS_EXPENSE, name);
                warningMessage.append(warning).append("\n");
            } else { // removed, no existing expenses
                String success = String.format(MESSAGE_SUCCESS_DISINVITE, name);
                successMessage.append(success).append("\n");
            }
        }

        model.updateFilteredPersonList(x -> participantIds.contains(x.getPrimaryKey()));

        String result;

        if (successMessage.toString().equals("")) {
            result = String.format(MESSAGE_RESULT_NONE_SUCCESS, warningMessage);
            return new CommandResult(result);
        } else {
            result = String.format(MESSAGE_RESULT, successMessage, warningMessage);
            Context newContext = new Context(activityToDisinviteFrom);
            model.setContext(newContext);
            return new CommandResult(result, newContext);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisinviteCommand // instanceof handles nulls
                && peopleToDisinvite.containsAll(((DisinviteCommand) other).peopleToDisinvite)
                && ((DisinviteCommand) other).peopleToDisinvite.containsAll(peopleToDisinvite));
    }
}
