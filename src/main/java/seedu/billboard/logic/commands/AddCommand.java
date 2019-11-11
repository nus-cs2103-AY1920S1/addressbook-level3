package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;

/**
 * Adds an expense to the billboard.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a expense to the billboard. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_TAG + "TAG]..."
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Bought "
            + PREFIX_DESCRIPTION + "Buy a book "
            + PREFIX_AMOUNT + "9.00 "
            + PREFIX_DATE + "25/3/2019 1200 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the billboard";

    private final Name name;
    private final Description description;
    private final Amount amount;
    private final CreatedDateTime date;
    private final List<String> tagNames;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddCommand(Name name, Description description, Amount amount,
                      CreatedDateTime date, List<String> tagNames) {
        requireAllNonNull(name, description, amount, date, tagNames);
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.tagNames = tagNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Set<Tag> tags = model.retrieveTags(tagNames);
        model.incrementCount(tags);
        Expense expense = new Expense(name, description, amount, date, tags);

        if (model.hasExpense(expense)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

        model.addExpense(expense);
        return new CommandResult(String.format(MESSAGE_SUCCESS, expense),
                false, false, CommandResult.DEFAULT_LIST_VIEW);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && name.equals(((AddCommand) other).name)
                && description.equals(((AddCommand) other).description)
                && amount.equals(((AddCommand) other).amount)
                && date.equals(((AddCommand) other).date)
                && tagNames.equals(((AddCommand) other).tagNames));
    }
}
