package seedu.travezy.diaryfeature.diaryCommands;

import seedu.travezy.diaryfeature.diaryModel.DiaryObject;
import seedu.travezy.logic.commands.CommandResult;

public class AddDiaryEntryCommand extends DiaryCommand {
    DiaryObject myAddedObject;

    /**
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     */
    public AddDiaryEntryCommand(String a, String b, String c, String d, String e) {

        myAddedObject = new DiaryObject(a,b,c,d,e);
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

