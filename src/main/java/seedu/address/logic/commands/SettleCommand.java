package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.ContextType;
import seedu.address.model.Model;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;
import seedu.address.model.activity.exceptions.PersonNotInActivityException;
import seedu.address.model.person.Person;

/**
 * Command to create a new Expense.
 */

public class SettleCommand extends ExpenseCommand {
    public static final String COMMAND_WORD = "settle";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Settles debt in the current activity. "
            + "Provide two people and optionally an amount specifying how much"
            + " the first person paid the second. If the amount is not specified,"
            + " it will be assumed that their entire debt is to be settled.\n\n"
            + "Parameters: "
            + PREFIX_PARTICIPANT + "NAME "
            + PREFIX_PARTICIPANT + "NAME "
            + "[" + PREFIX_EXPENSE + "AMOUNT]"
            + "\n\nExample: " + COMMAND_WORD + " "
            + PREFIX_PARTICIPANT + "John Doe "
            + PREFIX_PARTICIPANT + "Bob Lee "
            + PREFIX_EXPENSE + "10.0 ";

    public static final String MESSAGE_SUCCESS =
            "%s paid %s $%.2f successfully!";
    public static final String MESSAGE_NOT_TWO_PEOPLE =
            "You must specify only two people!";
    public static final String MESSAGE_OVERLY_COMPENSATING =
            "%s is not owed that much, not adding!";
    public static final String MESSAGE_TOO_MANY_AMOUNTS =
            "You can only specify one or zero amounts to settle!";
    public static final String MESSAGE_NOT_IN_ACTIVITY =
            "A person(s) supplied is not in this activity!\n%s";
    public static final String MESSAGE_REPEATED_PERSON =
            "A person doesn't owe himself anything!";
    public static final String MESSAGE_NOT_OWED =
            "%s doesn't owe %s anything to be settled!";
    public static final String MESSAGE_NO_CONTEXT =
            "Unsure which activity to target, use the view command to go to one first!";
    public static final String EXPENSE_DESCRIPTION =
            "%s paid %s";

    private Amount amount;

    /**
     * A constructor for a settlement.
     * @param persons A list of IDs of the two people that are settling. The first element pays.
     * @param amount The amount settled. null indicates "settle all"
     */
    public SettleCommand(List<String> persons, Amount amount) {
        super(persons, amount, "");
        this.amount = amount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getContext().getType() != ContextType.VIEW_ACTIVITY) {
            throw new CommandException(MESSAGE_MISSING_DESCRIPTION);
        }

        getScope(model);
        // The parser gurantees our persons array has 2 unique people
        Person payingPerson = searchPerson(persons.get(0), searchScope);
        Person receivingPerson = searchPerson(persons.get(1), searchScope);
        int payingId = payingPerson.getPrimaryKey();
        int receivingId = receivingPerson.getPrimaryKey();

        if (payingId == receivingId) {
            throw new CommandException(MESSAGE_REPEATED_PERSON);
        }

        String payingName = payingPerson.getNameStr();
        String receivingName = receivingPerson.getNameStr();

        double debt = activity.getOwed(receivingId, payingId);
        if (amount.value == 0) {
            if (debt < 0) {
                throw new CommandException(String.format(MESSAGE_NOT_OWED,
                            payingName, receivingName));
            } else {
                amount = new Amount(debt);
            }
        }

        if (amount.value > debt) {
            throw new CommandException(String.format(MESSAGE_OVERLY_COMPENSATING,
                        receivingName));
        }

        String description = String.format(EXPENSE_DESCRIPTION, payingName, receivingName);

        try {
            Expense e = new Expense(payingId, amount, description, true, receivingId);
            activity.addExpense(e);
        } catch (PersonNotInActivityException e) {
            throw new CommandException(MESSAGE_MISSING_PERSON_DESCRIPTION);
        }

        String successMessage = String.format(MESSAGE_SUCCESS, payingName, receivingName, amount.value);

        Context context = new Context(activity);
        model.setContext(context);

        return new CommandResult(successMessage, context);
    }
}
