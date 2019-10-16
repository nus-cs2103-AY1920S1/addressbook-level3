package seedu.address.logic.commands.wastelist;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;

import java.time.LocalDate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.waste.WasteMonth;

/**
 * Lists Waste Command to list out all food waste in a given month.
 */
public class ListWasteCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Lists out your current food waste for a given month. "
            + "If no month is specified, the food waste for the current month is displayed.\n"
            + "Optional Parameters: " + PREFIX_MONTH + "MONTH_OF_YEAR"
            + "Example: wlist " + COMMAND_WORD + " " + PREFIX_MONTH + "09.2019";

    private static final String MESSAGE_MONTH_RESTRICTION = "The given month must not"
            + " be after the current month";

    private static final String MESSAGE_SUCCESS = "Listed all waste items for the month ";

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
        model.updateFilteredWasteItemList(this.wasteMonth);

        System.out.println(model.getWasteListByMonth(this.wasteMonth).getWasteList().get(0));
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setWastelistCommand();
        return commandResult;
    }
}
