package seedu.address.model.diary.photo;

import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;

/**
 * Stub class for {@link PhotoList}.
 */
public class PhotoListStub extends PhotoList {

    public static final String PHOTO_LIST_STUB_STRING = "This is a photo list stub";

    public PhotoListStub() {}

    @Override
    public void addPhoto(Photo photo) {}

    @Override
    public Photo deletePhoto(Index index) {
        return null;
    }

    @Override
    public ObservableList<Photo> getObservablePhotoList() {
        return null;
    }

    @Override
    public Optional<Photo> getPhoto(String searchTerm) {
        return null;
    }

    @Override
    public void removeAllPhotos(String searchTerm) { }

    @Override
    public String toString() {
        return PHOTO_LIST_STUB_STRING;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        return obj instanceof PhotoListStub;
    }
}
