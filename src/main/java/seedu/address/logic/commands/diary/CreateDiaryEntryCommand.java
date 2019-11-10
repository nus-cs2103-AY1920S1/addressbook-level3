package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;

/**
 * {@link Command} that creates a new diary entry with the provided {@code dayIndex}.
 * It fails if there is already an entry for the specified day.
 */
public class CreateDiaryEntryCommand extends Command {
    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new current diary entry for the specified day\n"
            + "Parameters: INDEX (must be a positive integer, and the entry must not already exist) "
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_ENTRY_EXISTS = "There is already an entry for this day! %1$s";

    private static final String MESSAGE_CREATE_SUCCESS = "Created a new diary entry!";

    private static final String MESSAGE_DAY_OUT_OF_BOUNDS = "Your trip only has %1$d days!";

    private final Index dayIndex;

    public CreateDiaryEntryCommand(Index dayIndex) {
        requireNonNull(dayIndex);
        this.dayIndex = dayIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        validateWithinTrip(model.getPageStatus().getTrip().getDayList().internalUnmodifiableList.size());

        DiaryEntry diaryEntry = new DiaryEntry(dayIndex);
        try {
            model.getPageStatus().getCurrentTripDiary().addDiaryEntry(diaryEntry);
        } catch (IllegalArgumentException ex) {
            throw new CommandException(String.format(MESSAGE_ENTRY_EXISTS,
                    model.getPageStatus().getCurrentTripDiary().getDiaryEntry(dayIndex).get()));
        }

        model.setPageStatus(model.getPageStatus()
                .withNewDiaryEntry(diaryEntry)
                .withNewEditDiaryEntryDescriptor(null));

        return new CommandResult(MESSAGE_CREATE_SUCCESS);
    }

    /**
     * Ensures that the positive day index provided is between the first to last day of the trip, inclusive.
     *
     * @param dayListSize The size of the current {@link seedu.address.model.itinerary.day.DayList} of the
     * {@link seedu.address.model.trip.Trip}.
     * @throws CommandException If the {@code dayIndex} refers to a day beyond the last day of the trip.
     */
    private void validateWithinTrip(int dayListSize) throws CommandException {
        if (dayIndex.getZeroBased() >= dayListSize) {
            throw new CommandException(String.format(MESSAGE_DAY_OUT_OF_BOUNDS, dayListSize));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof CreateDiaryEntryCommand
                        && ((CreateDiaryEntryCommand) obj).dayIndex.equals(dayIndex));
    }
}
