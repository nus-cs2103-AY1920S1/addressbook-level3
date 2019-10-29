package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

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

    public static final String MESSAGE_SUCCESS = "%d expense(s) successfully created as follows:\n%s\n";
    public static final String MESSAGE_EXPENSE = "%s paid %s.\n  Description: %s\n";
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
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_ENTRIES);
        } else {
            activity = model.getContext().getActivity().get();
            // TODO: Use the new view thingy in Activity class.
            model.updateFilteredPersonList(x -> activity.getParticipantIds().contains(x.getPrimaryKey()));
        }

        searchScope = model.getFilteredPersonList();

        // This loop adds expenses one by one using keyword matching.
        // For each participant argument passed through, the argument is broken up into keywords
        // which are then used to search through the searchScope (context dependent).
        // Expenses will only be added if every keyword string has a unique match.
        StringBuilder successMessage = new StringBuilder();
        List<String> keywords;
        List<Person> findResult;
        int payingId = -1;
        int[] involvedArr = new int[persons.size() - 1];
        for (int i = 0; i < persons.size(); i++) {
            keywords = Arrays.asList(persons.get(i).split(" "));
            NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(keywords);
            findResult = searchScope.stream().filter(predicate).collect(Collectors.toList());

            if (findResult.size() != 1) {
                throw new CommandException(String.format(MESSAGE_NON_UNIQUE_SEARCH_RESULT, persons.get(i)));
            }

            Person person = findResult.get(0);

            if (i == 0) {
                payingId = person.getPrimaryKey();
            } else {
                involvedArr[i - 1] = person.getPrimaryKey();
            }

            successMessage.append(String.format(MESSAGE_EXPENSE, person.getName(), amount, description));

            // Contextual behaviour
            if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
                if (!activity.hasPerson(person.getPrimaryKey())) {
                    activity.invite(person.getPrimaryKey());
                }
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

        return new CommandResult(String.format(MESSAGE_SUCCESS, persons.size(), successMessage.toString()));
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
