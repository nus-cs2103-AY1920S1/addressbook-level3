package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContextType;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;
import seedu.address.model.activity.Title;
import seedu.address.model.activity.exceptions.PersonNotInActivityException;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Command to create a new Expense.
 */

public class ExpenseCommand extends Command {
    public static final String COMMAND_WORD = "expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds expenses to the current activity. "
            + "The first name to appear will pay for everyone else in the list that follows. "
            + "If there is no list provided, it will be assume that everyone in the activity is involved. "
            + "Parameters: "
            + PREFIX_PARTICIPANT + "NAME "
            + PREFIX_EXPENSE + "AMOUNT "
            + "[" + PREFIX_PARTICIPANT + "NAME  ] ... "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION ]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PARTICIPANT + "John Doe "
            + PREFIX_EXPENSE + "10.0 "
            + PREFIX_DESCRIPTION + "Bubble tea";

    public static final String MESSAGE_SUCCESS =
            "Expense of %s by %s successfully created (rounded to 2 decimal places)."
            + "\n\tDescription: %s\n\tPeople involved:\n%s\nWarnings:\n";
    public static final String WARNING_DUPLICATE_PERSON =
            "\tPerson with name %s already added to expense.\n";
    public static final String MESSAGE_NON_UNIQUE_SEARCH_RESULT =
            "Participant search term \"%s\" has no unique search result in the current context!";
    public static final String MESSAGE_MISSING_DESCRIPTION =
            "Creating an expense outside an activity requires a description!";
    public static final String MESSAGE_MISSING_PERSON_DESCRIPTION =
            "At least one person is not found in the activity\nNo expense was added.";

    private final List<String> persons;
    private final Amount amount;
    private final String description;

    public ExpenseCommand(List<String> persons, Amount amount, String description) {
        requireAllNonNull(persons, amount, description);
        this.persons = persons;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> searchScope;
        Activity activity;

        // Contextual behaviour
        if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
            if (!Title.isValidTitle(description)) {
                throw new CommandException(MESSAGE_MISSING_DESCRIPTION);
            }
            activity = new Activity(new Title(description));
            searchScope = model.getAddressBook().getPersonList();
        } else {
            activity = model.getContext().getActivity().get();
            // TODO: Use the new view thingy in Activity class.
            searchScope = model.getAddressBook().getPersonList().stream()
                    .filter(x -> activity.getParticipantIds().contains(x.getPrimaryKey()))
                    .collect(Collectors.toList());
        }

        // This loop adds expenses one by one using keyword matching.
        // For each participant argument passed through, the argument is broken up into keywords
        // which are then used to search through the searchScope (context dependent).
        // Expenses will only be added if every keyword string has a unique match.
        StringBuilder successMessage = new StringBuilder();
        StringBuilder warningMessage = new StringBuilder();
        Person payingPerson = searchPerson(persons.get(0), searchScope);
        int payingId = payingPerson.getPrimaryKey();
        int[] involvedArr;

        // Contextual behaviour
        if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
            if (!activity.hasPerson(payingPerson.getPrimaryKey())) {
                activity.invite(payingPerson.getPrimaryKey());
            }
        }

        if (persons.size() > 1) {
            List<Person> personList = new ArrayList<>();
            personList.add(payingPerson);
            for (int i = 1; i < persons.size(); i++) {
                Person person = searchPerson(persons.get(i), searchScope);

                if (personList.contains(person)) {
                    warningMessage.append(String.format(WARNING_DUPLICATE_PERSON, person.getName()));
                    continue;
                }
                personList.add(person);
                successMessage.append("\t\t" + person.getName() + "\n");

                // Contextual behaviour
                if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
                    if (!activity.hasPerson(person.getPrimaryKey())) {
                        activity.invite(person.getPrimaryKey());
                    }
                }
            }
            involvedArr = personList.stream()
                    .filter(x -> x.getPrimaryKey() != payingId)
                    .mapToInt(x -> x.getPrimaryKey())
                    .toArray();
        } else {
            // Contextual behaviour
            if (model.getContext().getType() == ContextType.VIEW_ACTIVITY) {
                involvedArr = searchScope.stream()
                        .filter(x -> x.getPrimaryKey() != payingId)
                        .mapToInt(x -> x.getPrimaryKey())
                        .toArray();
                searchScope.stream()
                        .filter(x -> x.getPrimaryKey() != payingId)
                        .forEach(x -> successMessage.append("\t\t" + x.getName() + "\n"));
            } else {
                involvedArr = new int[0];
            }
        }

        try {
            activity.addExpense(new Expense(payingId, amount, description, involvedArr));
        } catch (PersonNotInActivityException e) {
            throw new CommandException(MESSAGE_MISSING_PERSON_DESCRIPTION);
        }

        // Contextual behaviour
        if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
            model.addActivity(activity);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                amount, payingPerson.getName(), description, successMessage.toString()) + warningMessage.toString());
    }

    /**
     * Searches for a {@code Person} object from a given list of people using a name search string.
     * If an exact match is found (non-case sensitive), it will return the exact match.
     * Otherwise, it will use keyword based matching to look for names.
     * @param str The search string
     * @param searchScope The {@code Person} list to search from
     * @return The search result as a {@code Person} object
     * @throws CommandException if the search result is not unique
     */
    private Person searchPerson(String str, List<Person> searchScope) throws CommandException {
        List<Person> findResult = searchScope.stream()
                .filter(x -> str.toLowerCase().equals(x.getName().toString().toLowerCase()))
                .collect(Collectors.toList());

        if (findResult.size() == 1) {
            return findResult.get(0);
        }

        List<String> keywords = Arrays.asList(str.split(" "));
        NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(keywords);
        findResult = searchScope.stream().filter(predicate).collect(Collectors.toList());

        if (findResult.size() != 1) {
            throw new CommandException(String.format(MESSAGE_NON_UNIQUE_SEARCH_RESULT, str));
        }

        return findResult.get(0);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenseCommand)) {
            return false;
        }

        // state check
        ExpenseCommand e = (ExpenseCommand) other;
        return persons.equals(e.persons)
            && amount.equals(e.amount)
            && description.equals(e.description);
    }
}
