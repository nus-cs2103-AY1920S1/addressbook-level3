package seedu.address.logic.commands.diary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.diary.gallery.AddPhotoCommand.MESSAGE_ADD_SUCCESS;
import static seedu.address.logic.commands.diary.gallery.AddPhotoCommand.MESSAGE_NO_DIARY_ENTRY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appstatus.PageStatus;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.photo.Photo;
import seedu.address.model.diary.photo.PhotoTest;

public class AddPhotoCommandIntegrationTest {
    @Test
    void execute_validPhotoAndModelHasDiaryEntryWithPhotoList_photoAdded() {
        Model model = new ModelManager();
        DiaryEntry diaryEntry = new DiaryEntry(Index.fromOneBased(1));
        PageStatus pageStatus = new PageStatus(null, null, null, null, diaryEntry,
                null, null, null, null,
                null, null, null, null,
                null, null, null);
        model.setPageStatus(pageStatus);

        Model expectedModel = new ModelManager();
        DiaryEntry diaryEntry2 = new DiaryEntry(Index.fromOneBased(1));
        PageStatus pageStatus2 = new PageStatus(null, null, null, null, diaryEntry2,
                null, null, null, null, null,
                null, null, null,
                null, null, null);
        expectedModel.setPageStatus(pageStatus2);

        assertDoesNotThrow(() -> {
            Photo testPhoto = PhotoTest.getValidTestPhoto();
            diaryEntry2.getPhotoList().addPhoto(testPhoto);

            AddPhotoCommand addPhotoCommand = new AddPhotoCommand(testPhoto);
            String expectedMessage = String.format(MESSAGE_ADD_SUCCESS, testPhoto);

            assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
        });
    }

    @Test
    void execute_modelHasNoDiaryEntry_throwsCommandException() {
        Model model = new ModelManager();
        PageStatus pageStatus = new PageStatus(null, null, null, null, null,
                null, null, null, null, null,
                null, null, null,
                null, null, null);
        model.setPageStatus(pageStatus);


        assertDoesNotThrow(() -> {
            Photo testPhoto = PhotoTest.getValidTestPhoto();
            AddPhotoCommand addPhotoCommand = new AddPhotoCommand(testPhoto);

            assertCommandFailure(addPhotoCommand, model, MESSAGE_NO_DIARY_ENTRY);
        });
    }

    /**
     * Small unit test.
     */
    @Test
    void constructor_nullPhoto_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddPhotoCommand(null));
    }
}
