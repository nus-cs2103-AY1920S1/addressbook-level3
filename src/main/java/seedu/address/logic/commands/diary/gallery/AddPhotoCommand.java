package seedu.address.logic.commands.diary.gallery;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.diary.EditDiaryEntryCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.photo.Photo;

public class AddPhotoCommand extends Command {
    public static final String COMMAND_WORD = "addphoto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the photo specified by the path to the diary entry's gallery.\n"
            + "Parameters: "
            + "[" + PREFIX_DATA_FILE_PATH + "NAME] "
            + "[" + PREFIX_DATE_TIME_START + "DATE TIME TAKEN (" + ParserDateUtil.DATE_TIME_FORMAT + "] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DATA_FILE_PATH + "./image01.png "
            + PREFIX_DATE_TIME_START + "01/08/2019 1245 " + PREFIX_DESCRIPTION + "...";

    public static final String MESSAGE_ADD_SUCCESS = "Added your photo! %1$s";

    private final Photo photo;

    public AddPhotoCommand(Photo photo) {
        this.photo = photo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DiaryEntry diaryEntry = model.getPageStatus().getDiaryEntry();

        if (diaryEntry == null) {
            throw new CommandException(EditDiaryEntryCommand.MESSAGE_NO_DIARY_ENTRY);
        }

        diaryEntry.getPhotoList().addPhoto(photo);

        return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, photo));
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof AddPhotoCommand
                        && ((AddPhotoCommand) obj).photo.equals(photo));
    }
}
