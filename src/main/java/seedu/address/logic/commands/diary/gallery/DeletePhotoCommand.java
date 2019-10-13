package seedu.address.logic.commands.diary.gallery;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.diary.EditDiaryEntryCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.photo.Photo;

public class DeletePhotoCommand extends Command {
    public static final String COMMAND_WORD = "delphoto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the photo specified by the index in the diary entry's gallery.\n"
            + "Parameters: Index (must be a valid positive integer)";

    public static final String MESSAGE_DELETE_SUCCESS = "Deleted your photo! %1$s";

    public static final String MESSAGE_PHOTO_MISSING = "The photo you specified at the index %1$s does not exist!";

    private final Index photoIndex;

    public DeletePhotoCommand(Index photoIndex) {
        this.photoIndex = photoIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DiaryEntry diaryEntry = model.getPageStatus().getDiaryEntry();

        if (diaryEntry == null) {
            throw new CommandException(EditDiaryEntryCommand.MESSAGE_NO_DIARY_ENTRY);
        }

        Photo deletedPhoto = diaryEntry.getPhotoList().deletePhoto(photoIndex);
        if (deletedPhoto == null) {
            throw new CommandException(String.format(MESSAGE_PHOTO_MISSING, photoIndex.getOneBased()));
        }

        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, deletedPhoto));
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
                || (obj instanceof DeletePhotoCommand
                && ((DeletePhotoCommand) obj).photoIndex.equals(photoIndex));
    }
}
