package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.ActivityWithTime;

import seedu.address.model.day.Day;
import seedu.address.model.day.Itinerary;
import seedu.address.model.day.time.TimeInHalfHour;
import seedu.address.model.field.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Unschedules an activity from the day by time.
 */
public class UnscheduleTimeCommand extends UnscheduleCommand {

    public static final String SECOND_COMMAND_WORD = "time";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
            + ": Unschedules an activity by a time occupied by the activity on a certain day. "
            + "Parameters: "
            + PREFIX_DAY + "DAY "
            + PREFIX_START_TIME + "TIME";

    public static final String MESSAGE_UNSCHEDULE_TIME_SUCCESS = "Activity unscheduled: %1$s";

    private final Index dayIndex;
    private final TimeInHalfHour time;

    /**
     * @param dayIndex of the contacts in the filtered contacts list to edit
     * @param time details to edit the contacts with
     */
    public UnscheduleTimeCommand(Index dayIndex, TimeInHalfHour time) {
        requireNonNull(dayIndex);
        requireNonNull(time);
        this.dayIndex = dayIndex;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Itinerary lastShownItinerary = model.getItinerary(); // last shown itinerary?

        if (dayIndex.getZeroBased() >= lastShownItinerary.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }

        List<Day> daysInItinerary = lastShownItinerary.getDays();
        Day dayToEdit = daysInItinerary.get(dayIndex.getZeroBased());
        Day editedDay = createUnscheduledActivityDay(dayToEdit, this.time);
        List<Day> editedDays = new ArrayList<>(daysInItinerary);
        editedDays.set(dayIndex.getZeroBased(), editedDay);

        if (!dayToEdit.isSameDay(editedDay) && model.has(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.setDays(editedDays);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(String.format(MESSAGE_UNSCHEDULE_TIME_SUCCESS, editedContact));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnscheduleTimeCommand // instanceof handles nulls
                && this.dayIndex.equals(((UnscheduleTimeCommand) other).dayIndex)
                && this.time.equals(((UnscheduleTimeCommand) other).time));
    }
}