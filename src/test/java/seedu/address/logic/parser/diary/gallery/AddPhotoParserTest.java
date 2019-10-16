package seedu.address.logic.parser.diary.gallery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.io.File;
import java.net.URISyntaxException;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import seedu.address.MainApp;
import seedu.address.logic.parser.exceptions.ParseException;

class AddPhotoParserTest {
    private static final String VALID_ARGUMENTS = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            "src/main/resources/images/calendar.png",
            PREFIX_DESCRIPTION,
            "abcde",
            PREFIX_DATE_TIME_START,
            "10/10/2019 1000");
    private static final String VALID_ARGUMENTS_NO_OPTIONAL_ARGUMENTS = String.format(" %1$s%2$s",
            PREFIX_DATA_FILE_PATH,
            "src/main/resources/images/calendar.png");
    private static final String INVALID_FILE_EXTENSION = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            ":",
            PREFIX_DESCRIPTION,
            "abcde",
            PREFIX_DATE_TIME_START,
            "10/10/2019 1000");
    private static final String INVALID_FILE_PATH = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            "src/main/resources/view/MainWindow.fxml",
            PREFIX_DESCRIPTION,
            "abcde",
            PREFIX_DATE_TIME_START,
            "10/10/2019 1000");
    private static final String INVALID_DESCRIPTION = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            "src/main/resources/images/calendar.png",
            PREFIX_DESCRIPTION,
            "abcdeabcdeabcdeabcdeabcde",
            PREFIX_DATE_TIME_START,
            "10/10/2019 1000");
    private static final String INVALID_DATE_FORMAT = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            "src/main/resources/images/calendar.png",
            PREFIX_DESCRIPTION,
            "abcde",
            PREFIX_DATE_TIME_START,
            "10/10-2019 1000");
    private static final Supplier<File> VALID_TEST_IMAGE = () -> {
        try {
            return new File(MainApp.class.getResource("/images/calendar.png").toURI());
        } catch (URISyntaxException ex) {
            fail(String.format("VALID_TEST_IMAGE for %1$s was invalid", AddPhotoParserTest.class.getSimpleName()));
        }
        return null;
    };

    @Test
    void parse_validArguments_success() {
        assertDoesNotThrow(() -> {
            AddPhotoParser addPhotoParser = new AddPhotoParser();
            addPhotoParser.parse(VALID_ARGUMENTS);
        });
    }

    @Test
    void parse_validArgumentsNoOptionalArguments_success() {
        assertDoesNotThrow(() -> {
            AddPhotoParser addPhotoParser = new AddPhotoParser();
            addPhotoParser.parse(VALID_ARGUMENTS_NO_OPTIONAL_ARGUMENTS);
        });
    }

    @Test
    void parse_invalidFileExtension_throwsParseException() {
        assertThrows(ParseException.class, () -> {
            AddPhotoParser addPhotoParser = new AddPhotoParser();
            addPhotoParser.parse(INVALID_FILE_EXTENSION);
        });
    }

    @Test
    void parse_invalidFilePath_throwsParseException() {
        assertThrows(ParseException.class, () -> {
            AddPhotoParser addPhotoParser = new AddPhotoParser();
            addPhotoParser.parse(INVALID_FILE_PATH);
        });
    }

    @Test
    void parse_invalidDescription_throwsParseException() {
        assertThrows(ParseException.class, () -> {
            AddPhotoParser addPhotoParser = new AddPhotoParser();
            addPhotoParser.parse(INVALID_DESCRIPTION);
        });
    }

    @Test
    void parse_invalidDateFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> {
            AddPhotoParser addPhotoParser = new AddPhotoParser();
            addPhotoParser.parse(INVALID_DATE_FORMAT);
        });
    }
}
