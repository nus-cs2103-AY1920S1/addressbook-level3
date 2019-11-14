package seedu.address.logic.parser.diary;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.diary.CreateDiaryEntryCommand;
import seedu.address.logic.commands.diary.FlipDiaryCommand;
import seedu.address.logic.commands.diary.entry.AppendEntryTextCommand;
import seedu.address.logic.commands.diary.entry.DeleteEntryTextCommand;
import seedu.address.logic.commands.diary.entry.DoneEditEntryTextCommand;
import seedu.address.logic.commands.diary.entry.EditEntryTextCommand;
import seedu.address.logic.commands.diary.entry.InsertEntryTextCommand;
import seedu.address.logic.commands.diary.entry.ShowTextEditorCommand;
import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.logic.commands.diary.gallery.DeletePhotoCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.diary.entry.AppendEntryTextParser;
import seedu.address.logic.parser.diary.entry.DeleteEntryTextParser;
import seedu.address.logic.parser.diary.entry.DoneEditEntryTextParser;
import seedu.address.logic.parser.diary.entry.EditEntryTextParser;
import seedu.address.logic.parser.diary.entry.InsertEntryTextParser;
import seedu.address.logic.parser.diary.entry.ShowTextEditorParser;
import seedu.address.logic.parser.diary.gallery.AddPhotoParser;
import seedu.address.logic.parser.diary.gallery.DeletePhotoParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.navbar.NavbarViewParser;
import seedu.address.model.appstatus.PageType;

/**
 * The {@link PageParser} for parsing user input when the current {@link PageType} in the pageStatus
 * of the application is {@code DIARY}.
 */
public class DiaryParser implements PageParser {
    static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + FlipDiaryCommand.COMMAND_WORD + " "
            + AppendEntryTextCommand.COMMAND_WORD + " "
            + EditEntryTextCommand.COMMAND_WORD + " "
            + InsertEntryTextCommand.COMMAND_WORD + " "
            + ShowTextEditorCommand.COMMAND_WORD + " "
            + DeleteEntryTextCommand.COMMAND_WORD + " "
            + CreateDiaryEntryCommand.COMMAND_WORD + " "
            + DoneEditEntryTextCommand.COMMAND_WORD + " "
            + AddPhotoCommand.COMMAND_WORD + " "
            + DeletePhotoCommand.COMMAND_WORD + " | "
            + NavbarViewParser.MESSAGE_COMMAND_TYPES.replace("diary ", "");

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        DiaryCommand commandType;
        try {
            commandType = DiaryCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case FLIP:
            return new FlipDiaryParser().parse(arguments);
        case CREATE:
            return new CreateDiaryEntryParser().parse(arguments);
        case APPEND:
            return new AppendEntryTextParser().parse(arguments);
        case DELETE:
            return new DeleteEntryTextParser().parse(arguments);
        case EDIT:
            return new EditEntryTextParser().parse(arguments);
        case INSERT:
            return new InsertEntryTextParser().parse(arguments);
        case EDITOR:
            return new ShowTextEditorParser().parse(arguments);
        case DONE:
            return new DoneEditEntryTextParser().parse(arguments);
        case ADDPHOTO:
            return new AddPhotoParser().parse(arguments);
        case DELPHOTO:
            return new DeletePhotoParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
