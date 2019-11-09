package seedu.ifridge.logic.commands.wastelist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_MONTH;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.waste.WasteMonth;

/**
 * Lists Waste Command to list out all food waste in a given month.
 */
public class ListWasteCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Lists out your current food waste for a given month. "
            + "If no month is specified, the food waste for the current month is displayed.\n"
            + "Optional Parameters: " + PREFIX_MONTH + "MONTH_OF_YEAR\n"
            + "Example: wlist " + COMMAND_WORD + " " + PREFIX_MONTH + "Sep 2019\n";

    public static final String MESSAGE_MONTH_RESTRICTION = "The given month must not"
            + " be after the current month";

    public static final String MESSAGE_NO_WASTE_LIST_FOUND = "There is no record found in our waste archive for the "
            + "month of %1$s";

    public static final String MESSAGE_SUCCESS = "Listed all waste items for the month %1$s.";

    private WasteMonth wasteMonth;

    public ListWasteCommand(WasteMonth wasteMonth) {
        this.wasteMonth = wasteMonth;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        WasteMonth currentWasteMonth = WasteMonth.getCurrentWasteMonth();
        if (this.wasteMonth.isAfter(currentWasteMonth)) {
            throw new CommandException(MESSAGE_MONTH_RESTRICTION);
        }

        boolean hasWasteListInArchive = model.hasWasteMonth(this.wasteMonth);
        if (!hasWasteListInArchive && this.wasteMonth.equals(currentWasteMonth)) {
            model.getWasteList();
        } else if (!hasWasteListInArchive) {
            throw new CommandException(String.format(MESSAGE_NO_WASTE_LIST_FOUND, this.wasteMonth));
        }

        model.updateFilteredWasteItemList(this.wasteMonth);

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, this.wasteMonth));
        commandResult.setWasteListCommand();
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListWasteCommand // instanceof handles nulls
                && wasteMonth.equals(((ListWasteCommand) other).wasteMonth));
    }
}
