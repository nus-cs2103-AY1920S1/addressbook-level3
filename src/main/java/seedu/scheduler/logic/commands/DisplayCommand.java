package seedu.scheduler.logic.commands;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;

/**
 * Represent result of display command.
 */
public class DisplayCommand extends Command {

    public static final String COMMAND_WORD = "display";
    private static final String INTERVIEWEE_TAB_COMMAND_WORD = " interviewee";
    private static final String INTERVIEWER_TAB_COMMAND_WORD = " interviewer";
    private static final String SCHEDULE_TAB_COMMAND_WORD = " schedule";
    private static final String CHANGE_TAB_SUCCESS = " displayed.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": command to display "
            + "the schedules in a list of tables, the interviewer's information "
            + "or the interviewer's information in a list.\n"
            + "Example: " + COMMAND_WORD + SCHEDULE_TAB_COMMAND_WORD;

    private final String commandType;

    public DisplayCommand(String commandType) {
        this.commandType = commandType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (this.commandType) {
        case INTERVIEWEE_TAB_COMMAND_WORD:
            model.intervieweeTabChange();
            return new CommandResult(this.commandType + CHANGE_TAB_SUCCESS, false, false);
        case INTERVIEWER_TAB_COMMAND_WORD:
            model.interviewerTabChange();
            return new CommandResult(this.commandType + CHANGE_TAB_SUCCESS, false, false);
        case SCHEDULE_TAB_COMMAND_WORD:
            model.scheduleTabChange();
            return new CommandResult(this.commandType + CHANGE_TAB_SUCCESS, false, false);
        default:
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }
}
