package seedu.address.model.diary.photo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import javafx.scene.image.Image;

import seedu.address.MainApp;

class PhotoTest {

    private static final String VALID_DESCRIPTION_1 = "abcde";
    private static final String VALID_DESCRIPTION_2 = "*&^jausm,a";
    private static final String INVALID_DESCRIPTION_1 = "abcdeabcdeabcdeabcdeabcde";
    private static final String INVALID_DESCRIPTION_2 = " noWhitespacePrefix";
    private static final Supplier<Path> VALID_IMAGE_PATH_WINDOWS = () -> {
        try {
            return Paths.get(URLDecoder.decode(
                    MainApp.class.getResource("/images/dummytrip.jpeg")
                            .getPath().replaceFirst("/", "")
                            .replace("/", "\\"),
                    StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException | InvalidPathException e) {
            return null;
        }
    };
    private static final Supplier<Path> VALID_IMAGE_PATH_UNIX = () -> {
        try {
            return Paths.get(URLDecoder.decode(
                    MainApp.class.getResource("/images/dummytrip.jpeg").getPath(),
                    StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException | InvalidPathException e) {
            return null;
        }
    };
    private static final String INVALID_IMAGE_PATH = "/";

    @Test
    void validateDescription_validDescription_success() {
        assertDoesNotThrow(() -> {
            new Photo("", VALID_DESCRIPTION_1, LocalDateTime.now());
            new Photo("", VALID_DESCRIPTION_2, LocalDateTime.now());
        });
    }

    @Test
    void validateDescription_invalidDescription_throwsIllegalArgumentException() {
        assertAll("Invalid descriptions", () ->
                assertThrows(IllegalArgumentException.class, () ->
                        new Photo("", INVALID_DESCRIPTION_1, LocalDateTime.now())), () ->
                assertThrows(IllegalArgumentException.class, () ->
                        new Photo("", INVALID_DESCRIPTION_2, LocalDateTime.now())));
    }

    @Test
    void getDateTaken_dateTimeNow_success() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Photo photo = new Photo("", VALID_DESCRIPTION_1, localDateTime);

        assertSame(localDateTime, photo.getDateTaken());
    }

    @Test
    void validateImage_validPathAndImage_setsPathAndImage() {
        Path unixPath = VALID_IMAGE_PATH_UNIX.get();
        Path windowsPath = VALID_IMAGE_PATH_WINDOWS.get();

        if (windowsPath != null) {
            Photo windowsPhoto = new Photo(windowsPath, VALID_DESCRIPTION_1, LocalDateTime.now());
            //Check file path
            assertEquals(Paths.get(windowsPhoto.getImageFilePath()).toAbsolutePath(), windowsPath.toAbsolutePath());

            //Check image width and height is equal, as javafx Image equality checking is not implemented
            try {
                System.out.println(windowsPath);
                FileInputStream fileInputStreamWindows = new FileInputStream(windowsPath.toAbsolutePath().toFile());
                assertEquals(windowsPhoto.getImage().getWidth(), new Image(fileInputStreamWindows).getWidth());
                return;
            } catch (FileNotFoundException ex) {
                System.out.println("Windows path to test file was invalid, trying unix path.");
            }
        }

        if (unixPath != null) {
            Photo unixPhoto = new Photo(unixPath, VALID_DESCRIPTION_1, LocalDateTime.now());
            //Check file path
            assertEquals(Paths.get(unixPhoto.getImageFilePath()).toAbsolutePath(), unixPath.toAbsolutePath());

            //Check image width and height is equal, as javafx Image equality checking is not implemented
            assertDoesNotThrow(() -> {
                System.out.println(unixPath);
                FileInputStream fileInputStreamUnix = new FileInputStream(unixPath.toAbsolutePath().toFile());
                assertEquals(unixPhoto.getImage().getWidth(), new Image(fileInputStreamUnix).getWidth());
            });
            return;
        }

        fail("Invalid test image path.");
    }

    @Test
    void getDescription_validDescription_success() {
        Photo photo = new Photo("", VALID_DESCRIPTION_1, LocalDateTime.now());

        assertSame(VALID_DESCRIPTION_1, photo.getDescription());
    }

    @Test
    void equals_sameProperties_true() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Photo p1 = new Photo(INVALID_IMAGE_PATH, "abc", localDateTime);
        Photo p2 = new Photo(INVALID_IMAGE_PATH, "abc", localDateTime);

        assertEquals(p1, p1);
        assertEquals(p1, p2);
    }

    @Test
    void equals_differentProperties_false() {
        //Test dateTaken equality
        LocalDateTime localDateTime = LocalDateTime.now();
        Photo p1 = new Photo(INVALID_IMAGE_PATH, "abc", localDateTime);
        Photo p2 = new Photo(INVALID_IMAGE_PATH, "abc", localDateTime.plusDays(1));

        assertNotEquals(p1, p2);

        //Test description equality
        Photo p3 = new Photo(INVALID_IMAGE_PATH, "abcd", localDateTime);
        assertNotEquals(p1, p3);

        //Test file path equality
        Photo p4 = new Photo("abcde", "abc", localDateTime);
        assertNotEquals(p1, p4);

        //Test instance of equality
        assertNotEquals(p1, new Object());
    }
}
