//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.ExportTestUtil.deleteFileIfExists;
import static seedu.address.testutil.ExportTestUtil.isFilePresent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.testutil.TypicalExportPaths;
import seedu.address.testutil.TypicalFlashCards;

public class JsonImportUtilTest {

    @Test
    public void importFlashCardsFromJson_valid_success() {
        List<List<FlashCard>> listsToTest = Arrays.asList(
                TypicalFlashCards.getSingletonFlashCardList(),
                TypicalFlashCards.getTypicalFlashCards()
        );

        for (List<FlashCard> list : listsToTest) {
            tryImportFrom(list);
        }
    }

    /**
     * Helper function to attempt an import from a specific JsonExportPath. Will first export the {@code FlashCard}s to
     * that path, before importing them back in. Verifies the import works as expected.
     *
     * @param list List of {@code FlashCard}s to test the import function with.
     */
    private void tryImportFrom(List<FlashCard> list) {
        JsonExportPath path = TypicalExportPaths.CS2105_JSON;
        deleteFileIfExists(path);

        try {
            JsonExportUtil.exportFlashCardsToJson(list, path);
            Optional<List<FlashCard>> optionalList = JsonImportUtil.importFlashCardsFromJson(path);

            assertTrue(optionalList.isPresent());
            assertEquals(optionalList.get(), list);
            assertTrue(isFilePresent(path));
        } catch (IOException e) {
            fail("IOException when importing from json");
        } catch (DataConversionException e) {
            fail("DataConversionException when importing from json");
        }

        deleteFileIfExists(path);
    }
}
