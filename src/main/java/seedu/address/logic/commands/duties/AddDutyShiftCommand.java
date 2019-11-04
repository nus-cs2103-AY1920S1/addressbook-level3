//@@author woon17
package seedu.address.logic.commands.duties;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURSIVE_TIMES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.exceptions.InvalidEventScheduleChangeException;
import seedu.address.model.events.predicates.EventContainsRefIdPredicate;


/**
 * Adds a person to the address book.
 */
public class AddDutyShiftCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "newshift";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds duty shifts recursively "
            + " to the duty roster. \n"
            + "Parameters: "
            + PREFIX_ID + "REFERENCE_ID "
            + PREFIX_START + "PREFIX_START "
            + PREFIX_END + "PREFIX_END "
            + "[" + PREFIX_RECURSIVE + "PREFIX_RECURSIVE w/m/y] "
            + "[" + PREFIX_RECURSIVE_TIMES + "PREFIX_RECURSIVE_TIMES]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "STAFF001 "
            + PREFIX_START + "01/12/19 0900 "
            + PREFIX_END + "01/12/19 2100 "
            + PREFIX_RECURSIVE + "m "
            + PREFIX_RECURSIVE_TIMES + "2\n";

    public static final String MESSAGE_ADD_SHIFT_SUCCESS = "Duty shift added: %1$s";
    public static final String MESSAGE_SUCCESS_RECURSIVE = "%1$s repeated duty shifts were added:\n%2$s";
    public static final String MESSAGE_CANCEL_SHIFTS_CONSTRAINTS = "Must indicate at least 1 shift to add";

    private final Event toAdd;
    private final List<Event> eventList;

    /**
     * Creates an AddAppCommand to add the specified {@code Event}
     */
    public AddDutyShiftCommand(Event toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
        this.eventList = null;
    }

    /**
     * Creates an AddAppCommand to add the specified {@code Events}
     */
    public AddDutyShiftCommand(List<Event> eventList) {
        requireNonNull(eventList);
        checkArgument(eventList.size() > 0, MESSAGE_CANCEL_SHIFTS_CONSTRAINTS);
        this.toAdd = null;
        this.eventList = eventList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setTabListing(OmniPanelTab.DUTY_SHIFT_TAB);
        try {
            if (eventList == null) {
                model.scheduleDutyShift(toAdd);
                model.updateFilteredDutyShiftList(new EventContainsRefIdPredicate(toAdd.getPersonId()));
                return new CommandResult(String.format(MESSAGE_ADD_SHIFT_SUCCESS, toAdd));

            }
            model.scheduleDutyShift(eventList);
            model.updateFilteredDutyShiftList(new EventContainsRefIdPredicate(eventList.get(0).getPersonId()));
            return new CommandResult(String.format(
                    MESSAGE_SUCCESS_RECURSIVE,
                    eventList.size(),
                    eventList.stream()
                            .map(e -> e.toString()).collect(Collectors.joining("\n"))));

        } catch (InvalidEventScheduleChangeException ex) {
            throw new CommandException(ex.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDutyShiftCommand // instanceof handles nulls
                && toAdd.equals(((AddDutyShiftCommand) other).toAdd));
    }
}
