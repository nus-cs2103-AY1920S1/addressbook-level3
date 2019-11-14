package seedu.address.model.diary.photo;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import javafx.scene.image.Image;

/**
 * Stub class for {@link DiaryPhoto}, which uses the {@code VALID_IMAGE_PATH_WINDOWS} or {@code VALID_IMAGE_PATH_UNIX}
 * as defined in {@link DiaryPhotoTest}.
 * If construction of the {@link DiaryPhotoStub} instance fails with both paths, then the {@link InvalidPathException}
 * thrown is propagated upwards.
 */
public class DiaryPhotoStub extends DiaryPhoto {

    static final String STUB_TO_STRING = "This is a photo stub, but with a working image.";

    private static final String STUB_DESCRIPTION = "asdfghjk";
    private static final String STUB_FILE_PATH = "*";

    private final String customDescription;

    private DiaryPhotoStub(Path imagePath, String customDescription) {
        super(imagePath, STUB_DESCRIPTION, LocalDateTime.now());
        this.customDescription = customDescription;
    }

    static DiaryPhotoStub getPhotoStub() {
        try {
            Path windowsPath = DiaryPhotoTest.VALID_IMAGE_PATH_WINDOWS.get();

            if (windowsPath != null) {
                return new DiaryPhotoStub(windowsPath, STUB_DESCRIPTION);
            }

            throw new InvalidPathException("invalid windows test path", "test file path specified was invalid");
        } catch (InvalidPathException ex) {
            Path unixPath = DiaryPhotoTest.VALID_IMAGE_PATH_UNIX.get();

            if (unixPath != null) {
                return new DiaryPhotoStub(unixPath, STUB_DESCRIPTION);
            }

            throw new InvalidPathException("invalid test paths", ex.getReason());
        }
    }

    static DiaryPhotoStub getPhotoStubWithDescription(String customDescription) {
        try {
            Path windowsPath = DiaryPhotoTest.VALID_IMAGE_PATH_WINDOWS.get();

            if (windowsPath != null) {
                return new DiaryPhotoStub(windowsPath, customDescription);
            }

            throw new InvalidPathException("invalid windows test path", "test file path specified was invalid");
        } catch (InvalidPathException ex) {
            Path unixPath = DiaryPhotoTest.VALID_IMAGE_PATH_UNIX.get();

            if (unixPath != null) {
                return new DiaryPhotoStub(unixPath, customDescription);
            }

            throw new InvalidPathException("invalid test paths", ex.getReason());
        }
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getDescription() {
        return customDescription;
    }

    @Override
    public String getImageFilePath() {
        return null;
    }

    @Override
    public LocalDateTime getDateTaken() {
        return null;
    }

    @Override
    public String toString() {
        return STUB_TO_STRING;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        return obj instanceof DiaryPhotoStub;
    }
}
