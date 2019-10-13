package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;


/**
 * Command to create a new Activity.
 */

public class ActivityCommand extends Command {
    public static final String COMMAND_WORD = "activity";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new Activity.\n"
            + "Compulsory Parameters: t/\n"
            + "Optional Parameters: p/\n"
            + "Example: Activity";
    public static final String MESSAGE_SUCCESS =
        "%s successfully created with following participants:\n%s\nWarnings:\n%s";
    public static final String WARNING_SEARCH_RESULTS =
            "Unable to add person with search term \"%s\", as there were %d matches found.\n";
    public static final String WARNING_DUPLICATE_PERSON =
            "Person with name %s already added.\n";
    public static final String MESSAGE_ARGUMENTS = "Title: %s";

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

        String warning = "";
        String success = "";

        List<String> keywords;
        ArrayList<Person> findResult;
        ArrayList<Integer> participantIds = new ArrayList<Integer>();
        Person toAddPerson;
        Integer id;
        for (String searchTerm : participants) {
            keywords = Arrays.asList(searchTerm.split(" "));
            NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);
            findResult = model.findPerson(predicate);
            if (findResult.size() != 1) {
                warning += String.format(WARNING_SEARCH_RESULTS, searchTerm, findResult.size());
                continue;
            }
            toAddPerson = findResult.get(0);
            id = toAddPerson.hashCode();
            if (participantIds.contains(id)) {
                warning += String.format(WARNING_DUPLICATE_PERSON, toAddPerson.getName());
                continue;
            }
            if (participantIds.size() > 0) {
                success += ", ";
            }
            participantIds.add(id);
            success += toAddPerson.getName();
        }
        Activity toAdd = new Activity(title, participantIds.toArray(new Integer[participantIds.size()]));
        model.addActivity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, success, warning));
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
}
