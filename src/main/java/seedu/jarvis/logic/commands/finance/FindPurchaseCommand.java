package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.finance.PurchaseNameContainsKeywordsPredicate;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Finds and lists all purchases in FinanceTracker whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPurchaseCommand extends Command {

    public static final String COMMAND_WORD = "find-paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Jarvis will list all purchases whose description "
            + "contains any of the specified keywords (case-insensitive) and displays them as a list with index "
            + "numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " lunch";

    public static final String MESSAGE_NO_INVERSE = COMMAND_WORD + " command cannot be undone.";

    public static final String MESSAGE_PURCHASES_LISTED_OVERVIEW = "Here you go, Jarvis has found %1$d purchases with "
            + "that keyword! \nIf you wish to return your list of purchases, type 'list-finances' in the command box.";
    public static final String MESSAGE_NO_PURCHASES_FOUND = "Sorry, Jarvis did not find any purchases with that "
            + "keyword. \nIf you wish to return your list of purchases, type 'list-finances' in the command box.";

    public static final boolean HAS_INVERSE = false;

    private final PurchaseNameContainsKeywordsPredicate predicate;

    public FindPurchaseCommand(PurchaseNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPurchaseList(predicate);
        model.setViewStatus(ViewType.LIST_FINANCE);

        if (model.getFilteredPurchaseList().size() == 0) {
            return new CommandResult(
                    String.format(MESSAGE_NO_PURCHASES_FOUND, model.getFilteredPurchaseList().size()),
                    true);
        }

        return new CommandResult(
                String.format(MESSAGE_PURCHASES_LISTED_OVERVIEW, model.getFilteredPurchaseList().size()),
                true);

    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        throw new InvalidCommandToJsonException();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPurchaseCommand // instanceof handles nulls
                && predicate.equals(((FindPurchaseCommand) other).predicate)); // state check
    }
}
