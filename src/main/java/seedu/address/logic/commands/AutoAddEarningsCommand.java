package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.earnings.Count;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;

/**
 * Auto adds the earnings into the address book based on the interval.
 */
public class AutoAddEarningsCommand extends Command {

    public static final String COMMAND_WORD = "weekly_earnings";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Auto adds the earnings into the address book based on the interval, \n"
            + "by the index number used in the displayed earnings list. \n"
            + "Count parameters have a max value of 13 \n"

            + "Parameters: " + "INDEX (must be a positive integer) "
            + PREFIX_COUNT + "INTERVAL \n"

            + "Example: " + COMMAND_WORD + " 3 "
            + PREFIX_COUNT + "5";

    public static final String MESSAGE_SUCCESS = "Auto addition of earnings registered";
    public static final String MESSAGE_DUPLICATE_ADDITION =
            "This earnings with the same index, end date and interval already exists in the auto add list.";
    public static final String MESSAGE_DUPLICATE_EARNINGS =
            "This earnings with the same module, date and amount already exists in the address book";
    private final Index index;
    private final Count count;

    public AutoAddEarningsCommand(Index index, Count count) {
        requireAllNonNull(index, count);

        this.index = index;
        this.count = count;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Earnings> lastShownList = model.getFilteredEarningsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
        }

        Date date = lastShownList.get(index.getZeroBased()).getDate();
        String key = Count.parseDateToDays(date.dateNum);
        Earnings earnings = lastShownList.get(index.getZeroBased());
        earnings.setCount(this.count);

        if (earnings.getListOfAutoEarnings().containsKey(key)) {
            ArrayList<Earnings> earningsList = earnings.getArrayListOfAutoEarnings(key);

            if (earningsList.contains(earnings)) {
                throw new CommandException(MESSAGE_DUPLICATE_ADDITION);
            } else {
                earningsList.add(earnings);
                model.saveListToMap(key, earnings);
            }

        } else {
            ArrayList<Earnings> listOfEarnings = new ArrayList<>();
            listOfEarnings.add(earnings);
            earnings.putIntoList(key, listOfEarnings);
            model.saveToMap(key, listOfEarnings);
        }

        return new CommandResult(MESSAGE_SUCCESS,
                false, false, true, false, false,
                false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AutoAddEarningsCommand // instanceof handles nulls
            && index.equals(((AutoAddEarningsCommand) other).index)
            && count.equals(((AutoAddEarningsCommand) other).count));
    }
}
