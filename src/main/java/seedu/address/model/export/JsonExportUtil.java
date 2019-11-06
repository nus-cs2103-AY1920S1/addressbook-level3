//@@author LeowWB

package seedu.address.model.export;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.List;

import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.storage.JsonKeyboardFlashCardsStorage;

/**
 * Utility class that handles exporting of FlashCards to an external json file.
 */
public class JsonExportUtil {

    /**
     * Exports a List of FlashCards to a file at the given JsonExportPath.
     *
     * @param cards List of FlashCards
     * @param jsonExportPath JsonExportPath to export the FlashCards to
     * @throws IOException If an error arises in writing to the File.
     */
    public static void exportFlashCardsToJson(List<FlashCard> cards, JsonExportPath jsonExportPath) throws IOException {
        requireAllNonNull(cards, jsonExportPath);
        assert cards.size() > 0;
        assert JsonExportPath.isValid(jsonExportPath.toString());

        KeyboardFlashCards exportKfc = new KeyboardFlashCards();
        exportKfc.setFlashCards(cards);

        JsonKeyboardFlashCardsStorage jsonStorage = new JsonKeyboardFlashCardsStorage(
                jsonExportPath.getPath()
        );

        jsonStorage.saveAddressBook(exportKfc);
    }
}
