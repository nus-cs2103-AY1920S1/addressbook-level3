package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.day.Day;

/**
 * Adds a number of days to the itinerary.
 */
public class AddDayCommand extends AddCommand {

    public static final String SECOND_COMMAND_WORD = "day";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
            + ": Adds the number of days specified to the itinerary."
            + "Parameters: "
            + "NUMBER"
            + "Example: add " + COMMAND_WORD + " "
            + "NUMBER_OF_DAYS";

    public static final String MESSAGE_SUCCESS = "%d day(s) added";

    private final List<Day> toAdd;

    /**
     * Creates an AddDayCommand to add the specified {@code List} of {@code Day}s
     */
    public AddDayCommand(List<Day> days) {
        requireNonNull(days);
        toAdd = days;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //to be implemented
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddContactCommand
                && toAdd.equals(((AddDayCommand) other).toAdd));
    }
}
