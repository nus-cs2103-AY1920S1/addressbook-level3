package seedu.address.diaryfeature.diaryCommands;

import seedu.address.diaryfeature.diaryModel.DiaryList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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


