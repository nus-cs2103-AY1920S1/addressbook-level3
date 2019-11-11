package seedu.exercise.logic.commands;

import static seedu.exercise.commons.core.Messages.MESSAGE_TAB;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

//@@author t-cheepeng
/**
 * Schedules a regime or completes a schedule on a certain date
 */
public abstract class ScheduleCommand extends Command implements UndoableCommand, TypeDependentCommand {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = MESSAGE_TAB + COMMAND_WORD
            + " command usage:\n"
            + "Usage 1: Schedules a regime at a specific date." + MESSAGE_TAB + "Parameters: "
            + PREFIX_NAME + "REGIME_NAME "
            + PREFIX_DATE + "DATE" + MESSAGE_TAB
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "cardio "
            + PREFIX_DATE + "12/12/2019\n"
            + "Usage 2: Completes a schedule and adds to exercise tracker." + MESSAGE_TAB + "Parameters: "
            + PREFIX_INDEX + "INDEX_OF_SCHEDULE" + MESSAGE_TAB
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1";

    @Override
    public String getUndoableCommandWord() {
        return COMMAND_WORD;
    }
}
