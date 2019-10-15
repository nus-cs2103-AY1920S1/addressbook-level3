package seedu.address.diaryfeature.diaryparser;

import seedu.address.diaryfeature.diarycommands.AddDiaryEntryCommand;
import seedu.address.diaryfeature.diarycommands.DeleteDiaryEntryCommand;
import seedu.address.diaryfeature.diarycommands.DiaryErrorCommand;
import seedu.address.diaryfeature.diaryexceptions.CommandNotFoundException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class DiaryParser implements Parser {
    /**
     *
     * @param userInput
     * @return
     * @throws ParseException
     */
    @Override
    public Command parse(String userInput)  {
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
