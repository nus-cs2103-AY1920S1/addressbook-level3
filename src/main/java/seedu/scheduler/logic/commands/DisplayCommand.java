package seedu.scheduler.logic.commands;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;

/**
 * Represent result of display command.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";
    public static final String CHANGE_TAB_SUCCESS = " displayed.";
    private static final String INTERVIEWEE_TAB_COMMAND_WORD = "interviewee";
    private static final String INTERVIEWER_TAB_COMMAND_WORD = "interviewer";
    private static final String SCHEDULE_TAB_COMMAND_WORD = "schedule";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Command to display "
            + "the schedules in a list of tables, the interviewer's information "
            + "or the interviewer's information in a list.\n"
            + "Only schedule, interviewee and interviewer keywords are allowed.\n"
            + "Example: " + COMMAND_WORD + " " + SCHEDULE_TAB_COMMAND_WORD;

    private final String commandType;

    public DisplayCommand(String commandType) {
        this.commandType = commandType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (this.commandType.equalsIgnoreCase(INTERVIEWEE_TAB_COMMAND_WORD)) {
            model.intervieweeTabChange();
            return new CommandResult(INTERVIEWEE_TAB_COMMAND_WORD + CHANGE_TAB_SUCCESS, false, false);
        } else if (this.commandType.equalsIgnoreCase(INTERVIEWER_TAB_COMMAND_WORD)) {
            model.interviewerTabChange();
            return new CommandResult(INTERVIEWER_TAB_COMMAND_WORD + CHANGE_TAB_SUCCESS, false, false);
        } else if (this.commandType.equalsIgnoreCase(SCHEDULE_TAB_COMMAND_WORD)) {
            model.scheduleTabChange();
            return new CommandResult(SCHEDULE_TAB_COMMAND_WORD + CHANGE_TAB_SUCCESS, false, false);
        }
        throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

}
