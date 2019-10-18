package seedu.address.diaryfeature.diaryCommands;

import seedu.address.diaryfeature.diaryModel.DiaryList;
import seedu.address.logic.commands.CommandResult;

/**
 *
 */
public abstract class DiaryCommand  {
    protected static DiaryList myReference;

    /**
     *
     * @param myList is the
     */

   public void setReference(DiaryList myList) {
       myReference = myList;
   }


    /**
     *
     */
    public abstract CommandResult executeCommand();
}


