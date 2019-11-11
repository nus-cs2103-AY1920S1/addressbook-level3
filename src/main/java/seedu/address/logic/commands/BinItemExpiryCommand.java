package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MINUTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECONDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEARS;
import static seedu.address.model.binitem.BinItem.DATE_TIME_FORMATTER;

import java.time.temporal.ChronoUnit;

import seedu.address.commons.core.UserSettings;
import seedu.address.commons.util.BinItemBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.binitem.BinItem;

/**
 * Sets the expiry time of bin items in the bin.
 */
public class BinItemExpiryCommand extends Command {

    public static final String COMMAND_WORD = "binitemexpiry";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the expiry time of items in the Bin. "
        + "Parameters: "
        + "UNIT/AMOUNT\n"
        + "Available UNITs: "
        + PREFIX_SECONDS + ", "
        + PREFIX_MINUTES + ", "
        + PREFIX_HOURS + ", "
        + PREFIX_DAYS + ", "
        + PREFIX_MONTHS + ", "
        + PREFIX_YEARS + "\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_DAYS + "30";

    public static final String MESSAGE_SUCCESS = "Changed bin item expiry time! Items in the Bin will be removed"
        + " permanently %s %s after their deletion.";

    private final int timeToLiveAmount;
    private final ChronoUnit timeToLiveUnit;

    public BinItemExpiryCommand(int amount, ChronoUnit unit) {
        requireAllNonNull(amount, unit);

        this.timeToLiveAmount = amount;
        this.timeToLiveUnit = unit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        // Set time to live in binItem class
        assert timeToLiveAmount > 0 : "timeToLiveAmount must be positive.";
        BinItem.setTimeToLive(timeToLiveAmount, timeToLiveUnit);
        for (BinItem b : model.getAddressBook().getBinItemList()) {
            BinItem newBinItem = (new BinItemBuilder(b)
                .withExpiryDate(b.generateExpiryDate().format(DATE_TIME_FORMATTER))).build();
            model.setBinItem(b, newBinItem);
        }

        // Update user settings
        UserSettings userSettings = new UserSettings(model.getUserSettings().isSuggestionsOn(),
            timeToLiveAmount, timeToLiveUnit);
        model.setUserSettings(userSettings);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
            timeToLiveAmount, timeToLiveUnit.toString().toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BinItemExpiryCommand // instanceof handles nulls
            && timeToLiveAmount == (((BinItemExpiryCommand) other).timeToLiveAmount) // state check
            && timeToLiveUnit.equals(((BinItemExpiryCommand) other).timeToLiveUnit)); // state check
    }

}
