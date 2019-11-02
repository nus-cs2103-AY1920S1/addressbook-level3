package seedu.address.diaryfeature.logic.commands;


import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command<DiaryModel> {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "No more memories to journal? We're sad to see you go :(";

    @Override
    public CommandResult execute(DiaryModel model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
