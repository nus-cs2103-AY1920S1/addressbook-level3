package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;

/**
 * Adds a number of days to the itinerary.
 */
public class AddDayCommand extends AddCommand {

    public static final String SECOND_COMMAND_WORD = "days";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Adds the number of days specified to the itinerary.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " NUMBER OF DAYS",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 5"
    );

    public static final String MESSAGE_SUCCESS = "%d day(s) added";

    private final int toAdd;

    /**
     * Creates an AddDayCommand to add the specified {@code List} of {@code Day}s
     */
    public AddDayCommand(int numDays) {
        requireNonNull(numDays);
        toAdd = numDays;
    }

    public int getToAdd() {
        return toAdd;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.addDays(toAdd);
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
