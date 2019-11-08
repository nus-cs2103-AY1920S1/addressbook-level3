package seedu.weme.logic.commands.importcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.path.DirectoryPath;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.util.ImageUtil;

class ImportCommandTest extends ApplicationTest {

    private static final DirectoryPath LOAD_DIRECTORY_PATH = new DirectoryPath("src/test/data/memes/");

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalWeme(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalWeme(), new UserPrefs());
    }

    @Test
    public void execute_import_successMessage() throws IOException {

        List<Path> pathList = FileUtil.loadImagePath(LOAD_DIRECTORY_PATH);
        expectedModel.loadMemes(pathList);

        LoadCommand loadCommand = new LoadCommand(LOAD_DIRECTORY_PATH);

        assertCommandSuccess(loadCommand, model, LoadCommand.MESSAGE_SUCCESS, expectedModel);

        // importing the memes
        for (Meme meme : expectedModel.getImportList()) {
            Meme copiedMeme = copyMeme(meme, expectedModel.getMemeImagePath());
            expectedModel.addMeme(copiedMeme);
        }
        expectedModel.clearImportList();
        expectedModel.commitWeme(ImportCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new ImportCommand(), model, ImportCommand.MESSAGE_SUCCESS, expectedModel);

    }

    private MemeStub copyMeme(Meme toCopy, Path memeLocation) throws IOException {
        Path originalPath = toCopy.getImagePath().getFilePath();
        Path newPath = ImageUtil.copyImageFile(originalPath, memeLocation);
        return new MemeStub(new ImagePath(newPath.toString()), toCopy.getDescription(), toCopy.getTags());
    }

    @Test
    public void equals() {
        final ImportCommand standardCommand = new ImportCommand();

        // same values -> returns true
        ImportCommand commandWithSameValues = new ImportCommand();
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

    }

    /**
     * A meme stub that checks for equality without checking for filePath to
     * bypass the random UUID generation issue.
     */
    private class MemeStub extends Meme {

        public MemeStub(ImagePath imagePath, Description description, Set<Tag> tags) {
            super(imagePath, description, tags);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof Meme)) {
                return false;
            }

            Meme otherMeme = (Meme) other;
            return otherMeme.getDescription().equals(getDescription())
                    && otherMeme.getTags().equals(getTags())
                    && otherMeme.isArchived() == isArchived();
        }
    }


}
