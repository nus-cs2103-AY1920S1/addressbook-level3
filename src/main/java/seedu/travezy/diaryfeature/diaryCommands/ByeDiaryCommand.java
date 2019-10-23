package seedu.travezy.diaryfeature.diaryCommands;

import seedu.travezy.logic.commands.CommandResult;

public class ByeDiaryCommand extends DiaryCommand {
    @Override
    public CommandResult executeCommand() {
        return new CommandResult("bye",false,true);
    }

}
