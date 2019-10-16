package seedu.address.diaryfeature.diarycommands;

import seedu.address.diaryfeature.diarymain.DiaryObject;
import seedu.address.logic.commands.CommandResult;

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
