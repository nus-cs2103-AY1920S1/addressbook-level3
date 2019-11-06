package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.util.List;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;

/**
 * Clears the scheduler book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "List of Interviewees and Interviewers has been cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setIntervieweeList(List.of());
        model.setInterviewerList(List.of());
        try {
            model.setEmptyScheduleList();
            model.setSchedulesList(model.getEmptyScheduleList());
        } catch (ParseException pe) {
            throw new CommandException("Could not regenerate table");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
