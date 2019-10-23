package seedu.travezy.diaryfeature.diaryCommands;

import seedu.travezy.logic.commands.CommandResult;

public class DiaryErrorCommand extends DiaryCommand {
    Exception issue;
    /**
     *
     * @param x
     */

    public DiaryErrorCommand(Exception x) {
        issue = x;
    }


    /**
     *
     * @return
     */

    @Override
    public CommandResult executeCommand() {
        CommandResult deletedResult =
                new CommandResult("Error " + issue,false,false);
        return deletedResult;
    }


}
