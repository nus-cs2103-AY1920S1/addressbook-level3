package seedu.travezy.diaryfeature.diaryParser;

import seedu.travezy.diaryfeature.diaryCommands.AddDiaryEntryCommand;
import seedu.travezy.diaryfeature.diaryCommands.ByeDiaryCommand;
import seedu.travezy.diaryfeature.diaryCommands.DeleteDiaryEntryCommand;
import seedu.travezy.diaryfeature.diaryCommands.DiaryCommand;
import seedu.travezy.diaryfeature.diaryCommands.DiaryErrorCommand;
import seedu.travezy.diaryfeature.diaryCommands.ShowDiaryCommand;
import seedu.travezy.diaryfeature.diaryExceptions.CommandNotFoundException;
import seedu.travezy.logic.parser.exceptions.ParseException;

public class DiaryParser{
    /**
     *
     * @param userInput
     * @return
     * @throws ParseException
     */
    public DiaryCommand parse(String userInput)  {
        try {
            String trimmed = userInput.trim();
            if (trimmed.startsWith("add entry")) {
                //Note that "add entry" counts as 9 characters, and hence the added task
                //only starts from pos 10
                String entry = trimmed.substring(10);
                String[] temp = entry.split("/");
                return new AddDiaryEntryCommand(temp[0],temp[1],temp[2],temp[3],temp[4]);
            } else if (trimmed.startsWith("delete entry")) {
                //Note that "delete Entry" counts as 12 characters,
                //and hence, we start from pos 13
                int deleteIndex = Integer.parseInt(trimmed.substring(13));
                return new DeleteDiaryEntryCommand(deleteIndex);
            } else if(trimmed.startsWith("show")) {
                return new ShowDiaryCommand();
            } else if (trimmed.equalsIgnoreCase("bye")) {
                return new ByeDiaryCommand();
            } else {
                throw new CommandNotFoundException("No such command yet");
            }
        }
        catch (Exception x) {
            return new DiaryErrorCommand(x);
        }
    }
}
