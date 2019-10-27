package seedu.address.model.diary.photo;

import java.util.Collection;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;

/**
 * Abstraction of a list of {@code Photo}s to be displayed in the diary.
 * Supports accessors that use the {@code description} of the Photo.
 * It is backed by an {@code ObservableList}.
 */
public class PhotoList {
    private final ObservableList<Photo> photos;

    public PhotoList() {
        this.photos = FXCollections.observableArrayList();
    }

    public PhotoList(Collection<Photo> photos) {
        this();
        this.photos.addAll(photos);
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    public Photo deletePhoto(Index index) {
        return photos.remove(index.getZeroBased());
    }

    public ObservableList<Photo> getObservablePhotoList() {
        return photos;
    }

    /**
     * Searches for the first matching photo in the list with the given {@code searchTerm}.
     *
     * @param searchTerm The string search term to use.
     */
    public Optional<Photo> getPhoto(String searchTerm) {
        for (Photo photo : photos) {
            if (photo.getDescription().matches(searchTerm)) {
                return Optional.of(photo);
            }
        }

        return Optional.empty();
    }

    /**
     * Removes all photos in {@code photos} with a {@code description} matching the given searchTerm.
     *
     * @param searchTerm The string search term to use.
     */
    public void removeAllPhotos(String searchTerm) {
        photos.removeIf(photo -> photo.getDescription().matches(searchTerm));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Photo photo : photos) {
            builder.append(photo.toString()).append("\n");
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PhotoList)) {
            return false;
        }

        PhotoList otherPhotoList = (PhotoList) obj;

        return photos.equals(otherPhotoList.photos);
    }
}
