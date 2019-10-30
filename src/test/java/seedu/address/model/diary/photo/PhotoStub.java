package seedu.address.model.diary.photo;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import javafx.scene.image.Image;

/**
 * Stub class for {@link Photo}, which uses the {@code VALID_IMAGE_PATH_WINDOWS} or {@code VALID_IMAGE_PATH_UNIX}
 * as defined in {@link PhotoTest}.
 * If construction of the {@link PhotoStub} instance fails with both paths, then the {@link InvalidPathException}
 * thrown is propagated upwards.
 */
public class PhotoStub extends Photo {

    static final String STUB_TO_STRING = "This is a photo stub, but with a working image.";

    private static final String STUB_DESCRIPTION = "asdfghjk";
    private static final String STUB_FILE_PATH = "*";

    private final String customDescription;

    private PhotoStub(Path imagePath, String customDescription) {
        super(imagePath, STUB_DESCRIPTION, LocalDateTime.now());
        this.customDescription = customDescription;
    }

    static PhotoStub getPhotoStub() {
        try {
            Path windowsPath = PhotoTest.VALID_IMAGE_PATH_WINDOWS.get();

            if (windowsPath != null) {
                return new PhotoStub(windowsPath, STUB_DESCRIPTION);
            }

            throw new InvalidPathException("invalid windows test path", "test file path specified was invalid");
        } catch (InvalidPathException ex) {
            Path unixPath = PhotoTest.VALID_IMAGE_PATH_UNIX.get();

            if (unixPath != null) {
                return new PhotoStub(unixPath, STUB_DESCRIPTION);
            }

            throw new InvalidPathException("invalid test paths", ex.getReason());
        }
    }

    static PhotoStub getPhotoStubWithDescription(String customDescription) {
        try {
            Path windowsPath = PhotoTest.VALID_IMAGE_PATH_WINDOWS.get();

            if (windowsPath != null) {
                return new PhotoStub(windowsPath, customDescription);
            }

            throw new InvalidPathException("invalid windows test path", "test file path specified was invalid");
        } catch (InvalidPathException ex) {
            Path unixPath = PhotoTest.VALID_IMAGE_PATH_UNIX.get();

            if (unixPath != null) {
                return new PhotoStub(unixPath, customDescription);
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

        return obj instanceof PhotoStub;
    }
}
