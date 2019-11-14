package seedu.address.logic.parser.diary.gallery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.gallery.DeletePhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeletePhotoParserTest {

    private static final String VALID_INDEX = "  1  ";
    private static final String INVALID_INDEX = " abc ";

    @Test
    void parse_validArguments_success() {
        DeletePhotoParser deletePhotoParser = new DeletePhotoParser();
        assertDoesNotThrow(() -> {
            DeletePhotoCommand command = deletePhotoParser.parse(VALID_INDEX);
            DeletePhotoCommand expected = new DeletePhotoCommand(Index.fromOneBased(1));

            assertEquals(command, expected);
        });
    }

    @Test
    void parse_invalidArguments_throwsParseException() {
        DeletePhotoParser deletePhotoParser = new DeletePhotoParser();
        assertThrows(ParseException.class, () -> deletePhotoParser.parse(INVALID_INDEX));
    }
}
