package seedu.exercise.logic.commands;

import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Schedules a regime or completes a schedule on a certain date
 */
public abstract class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " usage 1: Schedules a regime at a specific date. Parameters: "
            + PREFIX_NAME + "REGIME NAME "
            + PREFIX_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "cardio "
            + PREFIX_DATE + "19/12/2019\n"
            + COMMAND_WORD + " usage 2: Completes a schedule and adds to exercise tracker. Parameters: "
            + PREFIX_INDEX + "INDEX OF SCHEDULE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1";
}
