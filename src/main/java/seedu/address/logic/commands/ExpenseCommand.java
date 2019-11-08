package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_WARNING;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
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
            + "\n\tDescription: %s\n\tOthers involved:\n%s";
    public static final String WARNING_DUPLICATE_PERSON =
            "\tPerson with name %s already added to expense.\n";
    public static final String MESSAGE_NON_UNIQUE_SEARCH_RESULT =
            "Participant search term \"%s\" has no unique search result in the current context!";
    public static final String MESSAGE_MISSING_DESCRIPTION =
            "Creating an expense outside an activity requires a description!";
    public static final String MESSAGE_MISSING_PERSON_DESCRIPTION =
            "At least one person is not found in the activity\nNo expense was added.";
    public static final String MESSAGE_ZERO_EXPENSE =
            "A payment of zero does not require an entry!";

    protected final List<String> persons;
    protected final Amount amount;
    protected final String description;

    protected List<Person> searchScope;
    protected Activity activity;
    protected List<Integer> personList = new ArrayList<>();
    protected StringBuilder successMessage = new StringBuilder();
    protected StringBuilder warningMessage = new StringBuilder();

    public ExpenseCommand(List<String> persons, Amount amount, String description) {
        requireAllNonNull(persons, amount, description);
        this.persons = persons;
        this.amount = amount;
        this.description = description;
    }

    protected void getScope(Model model) throws CommandException {
        if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
            if (!Title.isValidTitle(description)) {
                throw new CommandException(MESSAGE_MISSING_DESCRIPTION);
            }
            // TODO: maybe skip this if no description (have to double check)
            activity = new Activity(new Title(description));
            searchScope = model.getAddressBook().getPersonList();
        } else {
            activity = model.getContext().getActivity().get();
            searchScope = model.getAddressBook().getPersonList().stream()
                    .filter(x -> activity.hasPerson(x.getPrimaryKey()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Gets the payees.
     * @param model The model object containing the context.
     * @param payingId The primary key of the payer.
     */
    protected void getInvolved(Model model, int payingId) throws CommandException {
        if (persons.size() > 1) {
            for (String keyword : persons.subList(1, persons.size())) {
                Person person = searchPerson(keyword, searchScope);
                int personPriKey = person.getPrimaryKey();
                if (personList.contains(personPriKey) || personPriKey == payingId) {
                    warningMessage.append(String.format(WARNING_DUPLICATE_PERSON, person.getName()));
                    continue;
                }

                if (personPriKey != payingId) {
                    personList.add(person.getPrimaryKey());
                    successMessage.append("\t\t" + person.getName() + "\n");
                }

                if (model.getContext().getType() != ContextType.VIEW_ACTIVITY
                        && !activity.hasPerson(personPriKey)) {
                    activity.invite(personPriKey);
                }

            }
        } else {
            // everyone is involved if no list of people is provided
            if (model.getContext().getType() == ContextType.VIEW_ACTIVITY) {
                personList = searchScope.stream()
                        .map(x -> x.getPrimaryKey())
                        .filter(k -> k != payingId)
                        .collect(Collectors.toList());
                searchScope.stream()
                        .filter(x -> x.getPrimaryKey() != payingId)
                        .forEach(x -> successMessage.append("\t\t" + x.getName() + "\n"));
            }
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        getScope(model);

        // This loop adds expenses one by one using keyword matching.
        // For each participant argument passed through, the argument is broken up into keywords
        // which are then used to search through the searchScope (context dependent).
        // Expenses will only be added if every keyword string has a unique match.
        Person payingPerson = searchPerson(persons.get(0), searchScope);
        int payingId = payingPerson.getPrimaryKey();

        // Contextual behaviour
        if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
            if (!activity.hasPerson(payingPerson.getPrimaryKey())) {
                activity.invite(payingPerson.getPrimaryKey());
            }
        }

        getInvolved(model, payingId);

        try {
            // that long personlist expression just unboxes it into an array
            activity.addExpense(new Expense(payingId, amount, description,
                        personList.stream()
                        .mapToInt(x -> x)
                        .toArray()));
        } catch (PersonNotInActivityException e) {
            throw new CommandException(MESSAGE_MISSING_PERSON_DESCRIPTION);
        }

        // Contextual behaviour
        if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
            model.addActivity(activity);
        }

        Context newContext = new Context(activity);
        model.setContext(newContext);

        if (warningMessage.length() == 0) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, amount,
                        payingPerson.getName(), description,
                        successMessage.toString()) , newContext);
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS
                        + MESSAGE_WARNING, amount, payingPerson.getName(),
                        description, successMessage.toString(),
                        warningMessage.toString()), newContext);
        }
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
    protected Person searchPerson(String str, List<Person> searchScope) throws CommandException {
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
