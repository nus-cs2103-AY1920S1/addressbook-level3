package seedu.travezy.diaryfeature.diaryCommands;

import seedu.travezy.diaryfeature.diaryModel.DiaryList;
import seedu.travezy.logic.commands.CommandResult;

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


