package seedu.address.diaryfeature.diaryCommands;

import seedu.address.logic.commands.CommandResult;

public class ShowDiaryCommand extends DiaryCommand {


    @Override
    public CommandResult executeCommand() {
        String diaryEntries = myReference.getEntriesAsString();
        CommandResult showResult =
                new CommandResult("MY ENTRIES \n" + diaryEntries,false,false);
        return showResult;
    }
}
