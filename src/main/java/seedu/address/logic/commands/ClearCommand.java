package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "List of Interviewees and Interviewers has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setIntervieweeList(List.of());
        model.setInterviewerList(List.of());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
