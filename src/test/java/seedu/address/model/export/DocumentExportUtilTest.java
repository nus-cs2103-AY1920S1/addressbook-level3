//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.ExportTestUtil.deleteFileIfExists;
import static seedu.address.testutil.ExportTestUtil.isFilePresent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.TypicalExportPaths;
import seedu.address.testutil.TypicalFlashCards;

public class DocumentExportUtilTest {

    @Test
    public void exportFlashCardsToDocument_valid_success() {
        List<List<FlashCard>> listsToTest = Arrays.asList(
                TypicalFlashCards.getTypicalFlashCards(),
                TypicalFlashCards.getSingletonFlashCardList()
        );

        for (List<FlashCard> list : listsToTest) {
            tryExport(list);
        }
    }

    /**
     * Helper method that tries to export a List of {@code FlashCard}s to a specific Document path.
     * Will clean up after the test is done by deleting the file if it was created.
     *
     * @param list List of {@code FlashCard}s to be exported.
     */
    private void tryExport(List<FlashCard> list) {
        deleteFileIfExists(TypicalExportPaths.CHEATSHEET_DOCX);

        try {
            DocumentExportUtil.exportFlashCardsToDocument(
                    list,
                    TypicalExportPaths.CHEATSHEET_DOCX
            );

            assertTrue(isFilePresent(TypicalExportPaths.CHEATSHEET_DOCX));
        } catch (IOException e) {
            fail("IOException when trying to export document");
        }

        deleteFileIfExists(TypicalExportPaths.CHEATSHEET_DOCX);
    }
}
