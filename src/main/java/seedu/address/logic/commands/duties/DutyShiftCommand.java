//@@author woon17
package seedu.address.logic.commands.duties;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.NonActionableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;


/**
 * Finds and lists all events in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class DutyShiftCommand extends NonActionableCommand {

    public static final String COMMAND_WORD = "shift";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose reference Id contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Optional parameters: KEYWORD \n"
            + "Example: " + COMMAND_WORD + " S001A";

    private final Predicate<Event> predicate;

    public DutyShiftCommand(Predicate<Event> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setTabListing(OmniPanelTab.DUTY_SHIFT_TAB);
        model.updateFilteredDutyShiftList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ALL_EVENTS_LISTED_OVERVIEW, model.getFilteredDutyShiftList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DutyShiftCommand // instanceof handles nulls
                && predicate.equals(((DutyShiftCommand) other).predicate)); // state check
    }

}
