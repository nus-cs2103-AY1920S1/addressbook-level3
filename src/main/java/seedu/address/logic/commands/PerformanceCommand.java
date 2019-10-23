package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_TIMING;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.PerformanceEntry;
import seedu.address.model.person.Person;

public class PerformanceCommand extends Command {

    public static final String COMMAND_WORD = "performance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a player performance for an event to Athletick."
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_EVENT + "EVENT"
            + PREFIX_DATE + "DATE"
            + PREFIX_TIMING + "TIME"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT + "50m freestyle "
            + PREFIX_DATE + "02102019 "
            + PREFIX_TIMING + "30s";

    public static final String MESSAGE_SUCCESS = "Performance record added: %1$s";

    private final Event event;
    private final Index index;
    private final String date;
    private final String time;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public PerformanceCommand(Index index, Event event, String date, String time) {
        this.event = event;
        this.index = index;
        this.date = date;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person athlete = lastShownList.get(index.getZeroBased());
        addPerformance(athlete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, athlete));
    }

    private void addPerformance(Person athlete) {
        PerformanceEntry performanceEntry = new PerformanceEntry(date, time);
        event.addPerformance(athlete, performanceEntry);

    }

}
