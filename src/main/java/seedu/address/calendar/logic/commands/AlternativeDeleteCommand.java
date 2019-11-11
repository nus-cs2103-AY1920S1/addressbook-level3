package seedu.address.calendar.logic.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.Event;
import seedu.address.calendar.model.event.EventQuery;
import seedu.address.calendar.model.event.EventType;
import seedu.address.calendar.model.event.Holiday;
import seedu.address.calendar.model.event.SchoolBreak;
import seedu.address.calendar.model.event.Trip;
import seedu.address.calendar.logic.parser.Option;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class AlternativeDeleteCommand extends DeleteCommand implements AlternativeCommand {
    private static final boolean IS_BINARY_OPTION = false;
    private List<DeleteCommand> suggestedCommands;
    private DeleteCommand chosenCommand;
    private EventQuery eventQuery;

    public AlternativeDeleteCommand(EventQuery eventQuery) {
        suggestedCommands = new ArrayList<>();
        this.eventQuery = eventQuery;
    }

    @Override
    public CommandResult execute(Calendar calendar, Option option) throws CommandException {
        AlternativeCommandUtil.isValidUserCommand(option, IS_BINARY_OPTION);

        if (option.isBinary()) {
            if (!option.getBinaryOption()) {
                return new CommandResult(AlternativeCommandUtil.MESSAGE_COMMAND_NOT_EXECUTED);
            } else {
                assert false : "Yes is not a valid input. This should have been caught earlier";
            }
        }

        calendar.getEventsAtSpecificTime(eventQuery)
                .forEach(this::addToSuggestions);

        int optionNum = option.getNumber();

        if (optionNum < 0 || optionNum >= suggestedCommands.size()) {
            throw new CommandException(AlternativeCommandUtil.MESSAGE_INVALID_INPUT);
        }

        chosenCommand = suggestedCommands.get(optionNum);

        return chosenCommand.execute(calendar);
    }

    private void addToSuggestions(Event event) {
        EventType eventType = event.getEventType();
        switch (eventType) {
        case COMMITMENT:
            suggestedCommands.add(new DeleteCommitmentCommand((Commitment) event));
            break;
        case HOLIDAY:
            suggestedCommands.add(new DeleteHolidayCommand((Holiday) event));
            break;
        case SCHOOL_BREAK:
            suggestedCommands.add(new DeleteSchoolBreakCommand((SchoolBreak) event));
            break;
        default:
            assert eventType.equals(EventType.TRIP) : "There are only four possible event types";
            suggestedCommands.add(new DeleteTripCommand((Trip) event));
            break;
        }
    }

    @Override
    public CommandResult execute(Calendar calendar) throws CommandException {
        requireNonNull(chosenCommand);
        return chosenCommand.execute(calendar);
    }
}
