//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyKeyboardFlashCards;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.storage.JsonKeyboardFlashCardsStorage;

/**
 * Utility class that handles importing of FlashCards from an external json file (not the data save file).
 */
public class JsonImportUtil {

    /**
     * Imports a List of FlashCards from a file at the given JsonExportPath
     *
     * @param jsonExportPath Path of the file to import from
     * @return Optional List of FlashCards that were imported from the given file
     * @throws IOException If there is an error in accessing or reading from the file
     * @throws DataConversionException If there is an error in converting the data from the file
     */
    public static Optional<List<FlashCard>> importFlashCardsFromJson(JsonExportPath jsonExportPath)
            throws DataConversionException {

        requireNonNull(jsonExportPath);
        assert JsonExportPath.isValid(jsonExportPath.toString());

        return readFromOptionalKfc(
                getOptionalKfc(
                        jsonExportPath
                )
        );
    }

    private static Optional<ReadOnlyKeyboardFlashCards> getOptionalKfc(JsonExportPath jsonExportPath)
            throws DataConversionException {

        requireNonNull(jsonExportPath);

        JsonKeyboardFlashCardsStorage jsonStorage = new JsonKeyboardFlashCardsStorage(
                jsonExportPath.getPath()
        );

        return jsonStorage.readKeyboardFlashCards();
    }

    /**
     * Reads an Optional {@code KeyboardFlashCards}. Returns an Optional {@code List} of {@code FlashCard}s. If the
     * provided Optional is empty, the returned Optional will also be empty.
     *
     * @param optionalKfc Optional KeyboardFlashCards
     *
     * @return Optional List of {@code FlashCard}s, representing all the {@code FlashCard}s in the provided
     * {@code KeyboardFlashCards} Optional.
     */
    private static Optional<List<FlashCard>> readFromOptionalKfc(Optional<ReadOnlyKeyboardFlashCards> optionalKfc) {
        requireNonNull(optionalKfc);

        if (!optionalKfc.isPresent()) {
            return Optional.empty();
        }

        ReadOnlyKeyboardFlashCards importedKfc = optionalKfc.get();

        return Optional.of(
                importedKfc.getFlashcardList()
        );
    }
}
