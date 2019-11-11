package seedu.address.model.diary.photo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;

/**
 * Unit test of {@link PhotoList} using {@link DiaryPhotoStub}.
 */
public class PhotoListTest {
    @Test
    void addPhoto_photoStub_photoAdded() {
        DiaryPhotoStub photoStub = DiaryPhotoStub.getPhotoStub();
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(photoStub);

        assertTrue(photoList.getObservablePhotoList().contains(photoStub));
    }

    @Test
    void deletePhoto_photoStub_photoAddedThenDeleted() {
        DiaryPhotoStub photoStub = DiaryPhotoStub.getPhotoStub();
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(photoStub);

        assertTrue(photoList.getObservablePhotoList().contains(photoStub));

        photoList.deletePhoto(Index.fromZeroBased(0));
        assertFalse(photoList.getObservablePhotoList().contains(photoStub));
    }

    @Test
    void getPhoto_matchingTerm_photoReturned() {
        String searchTerm = "asdfasdf";
        DiaryPhotoStub photoStub = DiaryPhotoStub.getPhotoStubWithDescription(searchTerm);
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(photoStub);
        Optional<DiaryPhoto> p = photoList.getPhoto(searchTerm);

        if (p.isEmpty()) {
            fail();
        }
        assertSame(p.get(), photoStub);
    }

    @Test
    void getPhoto_noMatchingTerm_emptyOptionalReturned() {
        String searchTerm = "abcabcabc";
        DiaryPhotoStub photoStub = DiaryPhotoStub.getPhotoStubWithDescription("asdfasdf");
        PhotoList photoList = new PhotoList();
        photoList.addPhoto(photoStub);
        Optional<DiaryPhoto> p = photoList.getPhoto(searchTerm);

        if (p.isPresent()) {
            fail();
        }
    }

    @Test
    void removeAllPhotos_matchingTerm_photosRemoved() {
        String searchTerm = "asdfasdf";
        DiaryPhotoStub photoStub1 = DiaryPhotoStub.getPhotoStubWithDescription(searchTerm);
        DiaryPhotoStub photoStub2 = DiaryPhotoStub.getPhotoStubWithDescription(searchTerm);
        DiaryPhotoStub photoStub3 = DiaryPhotoStub.getPhotoStubWithDescription(searchTerm);

        PhotoList photoList = new PhotoList(Arrays.asList(
                photoStub1, photoStub2, photoStub3));

        assertEquals(3, photoList.getObservablePhotoList().size());
        photoList.removeAllPhotos(searchTerm);

        assertEquals(0, photoList.getObservablePhotoList().size());
    }

    @Test
    void toString_validPhotoStubs_equals() {
        DiaryPhotoStub photoStub1 = DiaryPhotoStub.getPhotoStub();
        DiaryPhotoStub photoStub2 = DiaryPhotoStub.getPhotoStub();
        DiaryPhotoStub photoStub3 = DiaryPhotoStub.getPhotoStub();

        PhotoList photoList = new PhotoList(Arrays.asList(
                photoStub1, photoStub2, photoStub3));

        assertEquals(
                DiaryPhotoStub.STUB_TO_STRING + "\n"
                + DiaryPhotoStub.STUB_TO_STRING + "\n"
                + DiaryPhotoStub.STUB_TO_STRING + "\n",
                photoList.toString());
    }

    @Test
    void equals_sameInstance_returnsTrue() {
        PhotoList photoList = new PhotoList();
        assertEquals(photoList, photoList);
    }

    @Test
    void equals_notInstanceOfPhotoList_returnsFalse() {
        PhotoList photoList = new PhotoList();
        assertNotEquals(photoList, new Object());
    }

    @Test
    void equals_equalFieldsNoAdditions_returnsTrue() {
        PhotoList photoList1 = new PhotoList();
        PhotoList photoList2 = new PhotoList();

        assertEquals(photoList1, photoList2);
    }

    @Test
    void equals_equalFieldsSomeAdditions_returnsTrue() {
        DiaryPhotoStub photoStub1 = DiaryPhotoStub.getPhotoStub();
        DiaryPhotoStub photoStub2 = DiaryPhotoStub.getPhotoStub();
        Collection<DiaryPhoto> photoStubs = Arrays.asList(photoStub1, photoStub2);

        PhotoList photoList1 = new PhotoList(photoStubs);
        PhotoList photoList2 = new PhotoList(photoStubs);

        assertEquals(photoList1, photoList2);
    }

    @Test
    void equals_differentFieldsSomeAdditions_returnsFalse() {
        DiaryPhotoStub photoStub1 = DiaryPhotoStub.getPhotoStub();
        DiaryPhotoStub photoStub2 = DiaryPhotoStub.getPhotoStub();

        PhotoList photoList1 = new PhotoList();
        PhotoList photoList2 = new PhotoList();

        photoList1.addPhoto(photoStub1);
        photoList1.addPhoto(photoStub2);
        photoList2.addPhoto(photoStub1);

        assertNotEquals(photoList1, photoList2);
    }
}
