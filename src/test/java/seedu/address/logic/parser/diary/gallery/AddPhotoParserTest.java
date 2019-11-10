package seedu.address.logic.parser.diary.gallery;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.diary.photo.Photo.MAXIMUM_DESCRIPTION_LENGTH;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.photo.Photo;

/**
 * Mix of unit and integration tests for {@link AddPhotoParser}.
 */
class AddPhotoParserTest {
    private static final String VALID_TEST_IMAGE_PATH = "src/main/resources/images/calendar.png";
    private static final String INVALID_EXTENSION_FILE_PATH = "src/main/resources/view/MainWindow.fxml";
    private static final String INVALID_FILE_PATH_STRING = ":";
    private static final String VALID_DESCRIPTION = "abcde";
    private static final String VALID_DATE_TIME_STRING = "10/10/2019 1000";


    private static final String VALID_ARGUMENTS = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            VALID_TEST_IMAGE_PATH,
            PREFIX_DESCRIPTION,
            VALID_DESCRIPTION,
            PREFIX_DATE_TIME_START,
            VALID_DATE_TIME_STRING);
    private static final String VALID_ARGUMENTS_NO_OPTIONAL_ARGUMENTS = String.format(" %1$s%2$s",
            PREFIX_DATA_FILE_PATH,
            VALID_TEST_IMAGE_PATH);
    private static final String INVALID_FILE_EXTENSION = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            INVALID_EXTENSION_FILE_PATH,
            PREFIX_DESCRIPTION,
            VALID_DESCRIPTION,
            PREFIX_DATE_TIME_START,
            VALID_DATE_TIME_STRING);
    private static final String INVALID_FILE_PATH = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            INVALID_FILE_PATH_STRING,
            PREFIX_DESCRIPTION,
            VALID_DESCRIPTION,
            PREFIX_DATE_TIME_START,
            VALID_DATE_TIME_STRING);
    private static final String INVALID_DESCRIPTION = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            VALID_TEST_IMAGE_PATH,
            PREFIX_DESCRIPTION,
            "abcdeabcdeabcdeabcdeabcde",
            PREFIX_DATE_TIME_START,
            VALID_DATE_TIME_STRING);
    private static final String INVALID_DATE_FORMAT = String.format(" %1$s%2$s %3$s%4$s %5$s%6$s",
            PREFIX_DATA_FILE_PATH,
            VALID_TEST_IMAGE_PATH,
            PREFIX_DESCRIPTION,
            VALID_DESCRIPTION,
            PREFIX_DATE_TIME_START,
            "10/10-2019 1000");
    private static final Supplier<File> VALID_TEST_IMAGE = () -> {
        try {
            return new File(VALID_TEST_IMAGE_PATH);
        } catch (InvalidPathException ex) {
            fail(String.format("VALID_TEST_IMAGE for %1$s was invalid", AddPhotoParserTest.class.getSimpleName()));
        }
        return null;
    };

    @Test
    void parse_validArguments_success() {
        assertDoesNotThrow(() -> {
            LocalDateTime validDateTime = ParserDateUtil.getDateTimeFromString(VALID_DATE_TIME_STRING);
            AddPhotoCommand expectedCommand = new AddPhotoCommand(
                    new Photo(VALID_TEST_IMAGE_PATH, VALID_DESCRIPTION, validDateTime));

            assertParseSuccess(new AddPhotoParser(), VALID_ARGUMENTS, expectedCommand);
        });
    }

    @Test
    void parse_validArgumentsNoOptionalArguments_success() {
        assertDoesNotThrow(() -> {
            File testImageFile = VALID_TEST_IMAGE.get();
            String testImageFileName = testImageFile.getName().substring(
                    0, Math.min(MAXIMUM_DESCRIPTION_LENGTH, testImageFile.getName().length()));
            LocalDateTime testImageDateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(testImageFile.lastModified()),
                    ZoneOffset.systemDefault());

            AddPhotoCommand expectedCommand = new AddPhotoCommand(
                    new Photo(VALID_TEST_IMAGE_PATH, testImageFileName, testImageDateTime));
            assertParseSuccess(new AddPhotoParser(), VALID_ARGUMENTS_NO_OPTIONAL_ARGUMENTS, expectedCommand);
        });
    }

    @Test
    void parse_invalidFileExtension_throwsParseException() {
        assertDoesNotThrow(() -> {
            LocalDateTime validDateTime = ParserDateUtil.getDateTimeFromString(VALID_DATE_TIME_STRING);
            AddPhotoCommand expectedCommand = new AddPhotoCommand(
                    new Photo(INVALID_EXTENSION_FILE_PATH, VALID_DESCRIPTION, validDateTime));

            assertParseFailure(new AddPhotoParser(), INVALID_FILE_EXTENSION,
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        });
    }

    //assertParseFailures not used in below tests as Photo constructor throws IllegalArgumentException
    //or InvalidPathException, preventing construction of expected command
    @Test
    void parse_invalidDescription_throwsParseException() {
        assertThrows(ParseException.class, () -> {
            AddPhotoParser addPhotoParser = new AddPhotoParser();
            addPhotoParser.parse(INVALID_DESCRIPTION);
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
    void parse_invalidDateFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> {
            AddPhotoParser addPhotoParser = new AddPhotoParser();
            addPhotoParser.parse(INVALID_DATE_FORMAT);
        });
    }
}
