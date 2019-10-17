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
import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;
import seedu.address.model.activity.Title;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Command to create a new Expense.
 */

public class ExpenseCommand extends Command {
    public static final String COMMAND_WORD = "expense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds expenses to the current activity. "
            + "Parameters: "
            + PREFIX_PARTICIPANT + "NAME "
            + PREFIX_EXPENSE + "AMOUNT "
            + "[" + PREFIX_PARTICIPANT + "NAME " + PREFIX_EXPENSE + "AMOUNT ] ... "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION ]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PARTICIPANT + "John Doe "
            + PREFIX_EXPENSE + "10.0 "
            + PREFIX_PARTICIPANT + "Mary "
            + PREFIX_EXPENSE + "5.0"
            + PREFIX_DESCRIPTION + "Bubble tea";

    public static final String MESSAGE_SUCCESS = "%d expense(s) successfully created as follows:\n%s\n";
    public static final String MESSAGE_EXPENSE = "%s paid %s.\n  Description: %s\n";
    public static final String MESSAGE_INCONSISTENT_EXPENSES = "There are an unequal number of people and expenses";
    public static final String MESSAGE_NON_UNIQUE_SEARCH_RESULT =
            "Participant search term \"%s\" has no unique search result in the current context";
    public static final String MESSAGE_MISSING_DESCRIPTION =
            "Creating an expense outside an activity view context requires a description";

    private final List<String> persons;
    private final List<Amount> amounts;
    private final String description;

    public ExpenseCommand(List<String> persons, List<Amount> amounts, String description) {
        requireAllNonNull(persons, amounts, description);
        this.persons = persons;
        this.amounts = amounts;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (persons.size() != amounts.size()) {
            throw new CommandException(MESSAGE_INCONSISTENT_EXPENSES);
        }

        List<Person> searchScope;
        Activity activity;

        // Contextual behaviour
        if (model.getContext().getType() != Context.Type.VIEW_ACTIVITY) {
            if (!Title.isValidTitle(description)) {
                throw new CommandException(MESSAGE_MISSING_DESCRIPTION);
            }
            activity = new Activity(new Title(description));
        } else {
            activity = new Activity(model.getContext().getActivity().get());
            model.updateFilteredPersonList(x -> activity.getParticipantIds().contains(x));
        }

        searchScope = model.getFilteredPersonList();

        // This loop adds expenses one by one using keyword matching.
        // For each participant argument passed through, the argument is broken up into keywords
        // which are then used to search through the searchScope (context dependent).
        // Expenses will only be added if every keyword string has a unique match.
        StringBuilder successMessage = new StringBuilder();
        List<String> keywords;
        List<Person> findResult;
        for (int i = 0; i < persons.size(); i++) {
            keywords = Arrays.asList(persons.get(i).split(" "));
            NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(keywords);
            findResult = searchScope.stream().filter(predicate).collect(Collectors.toList());

            if (findResult.size() != 1) {
                throw new CommandException(String.format(MESSAGE_NON_UNIQUE_SEARCH_RESULT, persons.get(i)));
            }

            Person person = findResult.get(0);

            successMessage.append(String.format(MESSAGE_EXPENSE, person.getName(), amounts.get(i), description));
            Expense expense = new Expense(person.getPrimaryKey(), amounts.get(i), description);

            // Contextual behaviour
            if (model.getContext().getType() != Context.Type.VIEW_ACTIVITY) {
                activity.invite(person);
            }

            activity.addExpense(expense);
        }

        // Contextual behaviour
        if (model.getContext().getType() != Context.Type.VIEW_ACTIVITY) {
            model.addActivity(activity);
        } else {
            model.setActivity(model.getContext().getActivity().get(), activity);
        }

        //TODO: Switch to activity view to view the newly created activity if not already in activity view?

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
            && amounts.equals(e.amounts)
            && description.equals(e.description);
    }
}
