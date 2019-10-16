package seedu.address.diaryfeature.diaryParser;

import seedu.address.diaryfeature.diaryCommands.AddDiaryEntryCommand;
import seedu.address.diaryfeature.diaryCommands.DeleteDiaryEntryCommand;
import seedu.address.diaryfeature.diaryCommands.DiaryCommand;
import seedu.address.diaryfeature.diaryCommands.DiaryErrorCommand;
import seedu.address.diaryfeature.diaryExceptions.CommandNotFoundException;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

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
                return new AddDiaryEntryCommand(entry);
            } else if (trimmed.startsWith("delete Entry")) {
                //Note that "delete Entry" counts as 12 characters,
                //and hence, we start from pos 13
                int deleteIndex = Integer.parseInt(trimmed.substring(13));
                return new DeleteDiaryEntryCommand(deleteIndex);
            } else {
                throw new CommandNotFoundException("No such command yet");
            }
        }
        catch (Exception x) {
            return new DiaryErrorCommand(x);
        }
    }
}
