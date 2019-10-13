package seedu.address.logic.parser.diary;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.diary.DoneEditDiaryEntryCommand;
import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;
import seedu.address.logic.commands.sidebar.EnterTripManagerCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.diary.gallery.AddPhotoParser;
import seedu.address.logic.parser.diary.gallery.DeletePhotoParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.navbar.NavbarViewParser;

/**
 * Placeholder javadoc.
 */
public class DiaryParser implements PageParser {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EnterTripManagerCommand.COMMAND_WORD + " "
            + EnterDayPageCommand.COMMAND_WORD + " "
            + EnterItineraryPageCommand.COMMAND_WORD + " "
            + NavbarViewParser.MESSAGE_COMMAND_TYPES;

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
        case EDIT:
            return new EditDiaryEntryParser().parse(arguments);
        case DONE:
            return new DoneEditDiaryEntryParser().parse(arguments);
        case ADDPHOTO:
            return new AddPhotoParser().parse(arguments);
        case DELPHOTO:
            return new DeletePhotoParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
