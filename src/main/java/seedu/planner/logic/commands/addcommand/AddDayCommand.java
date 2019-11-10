package seedu.planner.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.day.Day;

/**
 * Adds a number of days to the itinerary.
 */
public class AddDayCommand extends AddCommand {

    public static final String SECOND_COMMAND_WORD = "days";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Adds the number of days specified to the itinerary.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " NUMBER_OF_DAYS",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 5"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "<NUM_DAYS>"
    );

    public static final String MESSAGE_SUCCESS = "%d day(s) added";
    public static final int MAX_NUMBER_OF_DAYS_ALLOWED_IN_PLANNER = 15;
    public static final String MESSAGE_NUMBER_OF_DAYS_LIMIT_EXCEEDED = "Total number of days exceeds the limit of "
            + MAX_NUMBER_OF_DAYS_ALLOWED_IN_PLANNER;

    private final int toAdd;
    private final Index index;
    private final Day dayToAdd;
    private boolean isUndoRedo;

    /**
     * Creates an AddDayCommand to add the specified {@code List} of {@code Day}s
     */
    public AddDayCommand(int numDays, boolean isUndoRedo) {
        requireNonNull(numDays);
        toAdd = numDays;
        index = null;
        dayToAdd = null;
        this.isUndoRedo = isUndoRedo;
    }

    //Constructor used to undo DeleteDayEvent
    public AddDayCommand(Index index, Day dayToAdd) {
        requireAllNonNull(index, dayToAdd);
        this.index = index;
        this.dayToAdd = dayToAdd;
        toAdd = 1;
        this.isUndoRedo = true;
    }

    public int getToAdd() {
        return toAdd;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int currNumDays = model.getNumberOfDays();

        if ((currNumDays + toAdd) > MAX_NUMBER_OF_DAYS_ALLOWED_IN_PLANNER) {
            throw new CommandException(MESSAGE_NUMBER_OF_DAYS_LIMIT_EXCEEDED);
        }

        if (index == null && dayToAdd == null && !isUndoRedo) {
            //Not due to undo method
            updateEventStack(this, model);
            model.addDays(toAdd);
        } else if (isUndoRedo && index != null && dayToAdd != null) {
            //Due to undo method of DeleteDayEvent
            model.addDayAtIndex(index, dayToAdd);
        } else {
            //Due to redo method AddDayEvent
            model.addDays(toAdd);
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, toAdd),
                new UiFocus[]{UiFocus.AGENDA}
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddContactCommand
                && toAdd == ((AddDayCommand) other).toAdd);
    }
}
