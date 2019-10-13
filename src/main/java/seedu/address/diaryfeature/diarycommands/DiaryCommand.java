package seedu.address.diaryfeature.diarycommands;

import seedu.address.diaryfeature.diarymain.DiaryList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 *
 */
public abstract class DiaryCommand extends Command {
    protected static DiaryList myReference;

    /**
     *
     * @param myList
     */

   public void setReference(DiaryList myList) {
       myReference = myList;
   }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    /**
     *
     */
    public abstract CommandResult executeCommand();
}


