package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddProjectMeetingCommand extends Command {
    public static final String COMMAND_WORD = "addProjectMeeting";

    public static final String MESSAGE_SUCCESS = "Meeting added: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the project. "
            + "Parameters: "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TIME + "29/09/2019 1900"
            + PREFIX_DESCRIPTION + "milestone discussion";

    public static final String MESSAGE_DUPLICATE_PROJECT = "Project list contains duplicate project(s).";

    private final Meeting toAdd;

    /*
    Constructor
     */
    public AddProjectMeetingCommand(Meeting meeting) {
        requireNonNull(meeting);
        toAdd = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.getWorkingProject().get().addNewMeeting(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
