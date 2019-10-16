package seedu.address.diaryfeature.diaryCommands;

import seedu.address.diaryfeature.diaryModel.DiaryObject;
import seedu.address.logic.commands.CommandResult;

public class AddDiaryEntryCommand extends DiaryCommand {
    DiaryObject myAddedObject;

    /**
     *
     * @param input
     */

    public AddDiaryEntryCommand(String input) {
        myAddedObject = new DiaryObject(input);
    }

    /**
     *
     * @return
     */

    @Override
    public CommandResult executeCommand() {
        myReference.addEntry(myAddedObject);
        CommandResult addedResult =
                new CommandResult("ADDED " + myAddedObject.toString(),false,false);
        return addedResult;
    }


}

