package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_WARNING;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Command to create a new Activity.
 */
public class ActivityCommand extends Command {

    public static final String COMMAND_WORD = "activity";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new Activity.\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + "[" + PREFIX_PARTICIPANT + "PARTICIPANT]...\n"
            + "Example: activity t/Mala dinner p/Kaedoon p/Giak Lhee p/Veken";

    public static final String MESSAGE_SUCCESS =
            "%s successfully created with following participants:\n%s\n";
    public static final String WARNING_SEARCH_RESULTS =
            "Unable to add person with search term \"%s\", as there were %d matches found.\n";
    public static final String WARNING_DUPLICATE_PERSON =
            "Person with name %s already added.\n";

    private final Title title;
    private final List<String> participants;

    /**
     * @param title Title of the activity
     * @param participants List of the names of participants
     */
    public ActivityCommand(Title title, List<String> participants) {
        requireAllNonNull(title, participants);
        this.title = title;
        this.participants = participants;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        StringBuilder warningMessage = new StringBuilder();
        StringBuilder successMessage = new StringBuilder();

        List<String> keywords;
        ArrayList<Person> findResult;
        ArrayList<Integer> participantIds = new ArrayList<Integer>();
        Person toAddPerson;
        Integer id;

        // This loop adds participants one by one using keyword matching.
        // For each participant argument passed through, the argument is broken up into keywords
        // which are then used to search through the address book.
        // Participant will only be added if the keyword has a unique match.
        for (String searchTerm : participants) {

            //Check for exact match case
            Optional<Person> exactMatch = model.findPersonByName(searchTerm);
            if (exactMatch.isPresent()) {
                toAddPerson = exactMatch.get();
            } else {

                keywords = Arrays.asList(searchTerm.split(" "));
                NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(keywords);
                findResult = model.findPersonAll(predicate);

                // Non-unique match (0 or more than 1) - this argument is skipped
                if (findResult.size() != 1) {
                    updateWarningNotSingleMatch(warningMessage, searchTerm, findResult.size());
                    continue;
                }
                toAddPerson = findResult.get(0);
            }

            id = toAddPerson.getPrimaryKey();

            // Person already in this activity - this person is not added
            if (participantIds.contains(id)) {
                updateWarningPersonInActivity(warningMessage, toAddPerson);
                continue;
            }
            participantIds.add(id);
            updateSuccessMessage(successMessage, toAddPerson);
        }

        Activity toAdd = new Activity(title, participantIds.toArray(new Integer[participantIds.size()]));
        Context newContext = new Context(toAdd);
        model.addActivity(toAdd);
        model.setContext(newContext);
        model.updateFilteredPersonList(x -> toAdd.getParticipantIds().contains(x.getPrimaryKey()));
        if (warningMessage.length() == 0) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, successMessage), newContext);
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS + MESSAGE_WARNING,
                        toAdd, successMessage, warningMessage), newContext);
        }
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCommand)) {
            return false;
        }

        // state check
        ActivityCommand e = (ActivityCommand) other;
        return title.equals(e.title)
                && participants.equals(e.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, participants);
    }

    /**
     * @param stringBuilder StringBuilder that contains the success message
     * @param person Person that got just added
     *
     */
    private void updateSuccessMessage(StringBuilder stringBuilder, Person person) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(person.getName());
    }

    /**
     * @param stringBuilder StringBuilder that contains the warning message
     * @param searchTerm Search term that was used to find a person
     * @param matchCount Number of valid matches
     */
    private void updateWarningNotSingleMatch(StringBuilder stringBuilder, String searchTerm, int matchCount) {
        stringBuilder.append(String.format(WARNING_SEARCH_RESULTS, searchTerm, matchCount));
    }

    /**
     * @param stringBuilder StringBuilder that contains the warning message
     * @param person Person object that attempted to be added in but failed as it is already in the participant list
     */
    private void updateWarningPersonInActivity(StringBuilder stringBuilder, Person person) {
        stringBuilder.append(String.format(WARNING_DUPLICATE_PERSON, person.getName()));
    }
}
