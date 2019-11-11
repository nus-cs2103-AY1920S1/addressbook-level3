package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Wish;

public class PurchaseWishCommand extends Command {

    public static final String COMMAND_WORD = "getWish";

    public static final String ONE_LINER_DESC = COMMAND_WORD
            + ": Buys item in WishList adds an entry under expenditure.\n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_WISH_PURCHASED_SUCCESS = "Purchased Wish: %1$s";

    private final Index targetIndex;

    public PurchaseWishCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(final Model model, final CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Wish> lastShownList = model.getFilteredWishes();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Wish toBuy = lastShownList.get(targetIndex.getZeroBased());
        model.deleteWish(toBuy);
        Expense expense = new Expense(toBuy.getCategory(),
                toBuy.getDesc(), toBuy.getDate(), toBuy.getAmount(), toBuy.getTags());
        model.addExpense(expense);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_WISH_PURCHASED_SUCCESS, toBuy));
    }
}
