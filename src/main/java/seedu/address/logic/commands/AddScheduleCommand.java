package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Status;
import seedu.address.model.schedule.Schedule;

/**
 * Adds a schedule to SML.
 */
public class AddScheduleCommand extends Command {

    public static final String COMMAND_WORD = "add-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a schedule to SML. "
            + "Parameters: "
            + PREFIX_ORDER + "ORDER "
            + PREFIX_CALENDAR + "CALENDAR "
            + PREFIX_VENUE + "VENUE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ORDER + "2 "
            + PREFIX_CALENDAR + "2019.12.12.17.30 "
            + PREFIX_VENUE + "Changi Airport T3 "
            + PREFIX_TAG + "freebie ";

    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule already exists in SML.";
    public static final String MESSAGE_ORDER_DOES_NOT_EXIST = "This order does not exists in SML.";
    public static final String MESSAGE_ORDER_ALREADY_SCHEDULED = "This order is already scheduled in SML.";

    private final Schedule toAdd;

    /**
     * Creates an AddScheduleCommand to add the specified {@code CustomSchedule}
     */
    public AddScheduleCommand(Schedule schedule) {
        requireNonNull(schedule);
        toAdd = schedule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSchedule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        } else if (!model.hasOrder(toAdd.getOrder())) {
            throw new CommandException(MESSAGE_ORDER_DOES_NOT_EXIST);
        } else if (toAdd.getOrder().getStatus().equals(Status.SCHEDULED)) {
            throw new CommandException(MESSAGE_ORDER_ALREADY_SCHEDULED);
        }

        /*
        DOOMED
        */

        model.addSchedule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), PanelType.SCHEDULE);
    }

}
