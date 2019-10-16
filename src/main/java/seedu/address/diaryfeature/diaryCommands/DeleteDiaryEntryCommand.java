package seedu.address.diaryfeature.diarycommands;

import seedu.address.diaryfeature.diarymain.DiaryObject;
import seedu.address.logic.commands.CommandResult;

public class DeleteDiaryEntryCommand extends DiaryCommand {
        int deleted;
    /**
     *
     * @param x
     */

    public DeleteDiaryEntryCommand(int x) {
        deleted = x;
    }


    /**
     *
     * @return
     */

    @Override
    public CommandResult executeCommand() {
        DiaryObject deletedObject = myReference.deleteEntry(deleted);
        CommandResult deletedResult =
                new CommandResult("DELETED " + deletedObject.toString(),false,false);
        return deletedResult;
    }


}
