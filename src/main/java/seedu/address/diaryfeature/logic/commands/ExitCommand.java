package seedu.address.diaryfeature.logic.commands;


import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Terminates the program with a specific String
 */
public class ExitCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "exit";
    private static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "No more memories to journal? We're sad to see you go :(";

    /**
     * Exits the program while showing the acknowledgement message
     *
     * @param model {@code model} which the command should operate on.
     * @return {@code CommandResult} which is the readable form of the exit command
     */
    @Override
    public CommandResult execute(DiaryModel model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
