package seedu.address.model.diary.photo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.model.diary.photo.DiaryPhoto.MESSAGE_DESCRIPTION_CONSTRAINTS;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

public class DiaryPhotoTest {

    static final Supplier<Path> VALID_IMAGE_PATH_WINDOWS = () -> {
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
    static final Supplier<Path> VALID_IMAGE_PATH_UNIX = () -> {
        try {
            return Paths.get(URLDecoder.decode(
                    MainApp.class.getResource("/images/dummytrip.jpeg").getPath(),
                    StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException | InvalidPathException e) {
            return null;
        }
    };

    private static final String VALID_DESCRIPTION_1 = "abcde";
    private static final String VALID_DESCRIPTION_2 = "*&^jausm,a";
    private static final String INVALID_DESCRIPTION_1 = "abcdeabcdeabcdeabcdeabcde";
    private static final String INVALID_DESCRIPTION_2 = " noWhitespacePrefix";
    private static final String INVALID_IMAGE_PATH = "/";

    /**
     * Retrieves a test {@code Photo} instance for integration testing in other test classes.
     * Photo is guranteed to be valid if below unit test cases pass.
     *
     * @return Valid {@link DiaryPhoto} test instance.
     * @throws IOException if photo construction from both test valid image paths fail.
     */
    public static DiaryPhoto getValidTestPhoto() throws IOException {
        Path unixPath = VALID_IMAGE_PATH_UNIX.get();
        Path windowsPath = VALID_IMAGE_PATH_WINDOWS.get();

        if (windowsPath != null) {
            return new DiaryPhoto(windowsPath, VALID_DESCRIPTION_1, LocalDateTime.now());
        }

        if (unixPath != null) {
            return new DiaryPhoto(unixPath, VALID_DESCRIPTION_1, LocalDateTime.now());
        }

        throw new IOException("Failed to retrieve test photo");
    }

    @Test
    void validateDescription_validDescription_success() {
        assertDoesNotThrow(() -> {
            new DiaryPhoto("", VALID_DESCRIPTION_1, LocalDateTime.now());
            new DiaryPhoto("", VALID_DESCRIPTION_2, LocalDateTime.now());
        });
    }

    @Test
    void validateDescription_invalidDescription_throwsIllegalArgumentException() {
        assertAll("Invalid descriptions", () ->
                assertThrows(IllegalArgumentException.class, MESSAGE_DESCRIPTION_CONSTRAINTS, () ->
                        new DiaryPhoto("", INVALID_DESCRIPTION_1, LocalDateTime.now())), () ->
                assertThrows(IllegalArgumentException.class, MESSAGE_DESCRIPTION_CONSTRAINTS, () ->
                        new DiaryPhoto("", INVALID_DESCRIPTION_2, LocalDateTime.now())));
    }

    @Test
    void getDateTaken_dateTimeNow_success() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DiaryPhoto photo = new DiaryPhoto("", VALID_DESCRIPTION_1, localDateTime);

        assertSame(localDateTime, photo.getDateTaken());
    }

    @Test
    void validateImage_validPathAndImage_setsPathAndImage() {
        Path unixPath = VALID_IMAGE_PATH_UNIX.get();
        Path windowsPath = VALID_IMAGE_PATH_WINDOWS.get();

        if (windowsPath != null) {
            DiaryPhoto windowsPhoto = new DiaryPhoto(windowsPath, VALID_DESCRIPTION_1, LocalDateTime.now());
            //Check file path
            assertEquals(Paths.get(windowsPhoto.getImageFilePath()).toAbsolutePath(), windowsPath.toAbsolutePath());

            //Check image width and height is equal, as javafx Image equality checking is not implemented
            try {
                FileInputStream fileInputStreamWindows = new FileInputStream(windowsPath.toAbsolutePath().toFile());
                Image expectedImage = new Image(fileInputStreamWindows);
                assertEquals(windowsPhoto.getImage().getWidth(), expectedImage.getWidth());
                assertEquals(windowsPhoto.getImage().getHeight(), expectedImage.getHeight());
                return;
            } catch (FileNotFoundException ex) {
                System.out.println("Windows path to test file was invalid, trying unix path.");
            }
        }

        if (unixPath != null) {
            DiaryPhoto unixPhoto = new DiaryPhoto(unixPath, VALID_DESCRIPTION_1, LocalDateTime.now());
            //Check file path
            assertEquals(Paths.get(unixPhoto.getImageFilePath()).toAbsolutePath(), unixPath.toAbsolutePath());

            //Check image width and height is equal, as javafx Image equality checking is not implemented
            assertDoesNotThrow(() -> {
                FileInputStream fileInputStreamUnix = new FileInputStream(unixPath.toAbsolutePath().toFile());
                Image expectedImage = new Image(fileInputStreamUnix);
                assertEquals(unixPhoto.getImage().getWidth(), expectedImage.getWidth());
                assertEquals(unixPhoto.getImage().getHeight(), expectedImage.getHeight());
            });
            return;
        }

        fail("Invalid test image path.");
    }

    @Test
    void getDescription_validDescription_success() {
        DiaryPhoto photo = new DiaryPhoto("", VALID_DESCRIPTION_1, LocalDateTime.now());

        assertSame(VALID_DESCRIPTION_1, photo.getDescription());
    }

    @Test
    void equals_sameProperties_true() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DiaryPhoto p1 = new DiaryPhoto(INVALID_IMAGE_PATH, "abc", localDateTime);
        DiaryPhoto p2 = new DiaryPhoto(INVALID_IMAGE_PATH, "abc", localDateTime);

        assertEquals(p1, p1);
        assertEquals(p1, p2);
    }

    @Test
    void equals_differentProperties_false() {
        //Test dateTaken equality
        LocalDateTime localDateTime = LocalDateTime.now();
        DiaryPhoto p1 = new DiaryPhoto(INVALID_IMAGE_PATH, "abc", localDateTime);
        DiaryPhoto p2 = new DiaryPhoto(INVALID_IMAGE_PATH, "abc", localDateTime.plusDays(1));

        assertNotEquals(p1, p2);

        //Test description equality
        DiaryPhoto p3 = new DiaryPhoto(INVALID_IMAGE_PATH, "abcd", localDateTime);
        assertNotEquals(p1, p3);

        //Test file path equality
        DiaryPhoto p4 = new DiaryPhoto("abcde", "abc", localDateTime);
        assertNotEquals(p1, p4);

        //Test instance of equality
        assertNotEquals(p1, new Object());
    }
}
