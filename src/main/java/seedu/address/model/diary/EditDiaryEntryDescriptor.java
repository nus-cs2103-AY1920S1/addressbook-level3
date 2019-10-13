package seedu.address.model.diary;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.Photo;
import seedu.address.model.diary.photo.PhotoList;

public class EditDiaryEntryDescriptor {
    public static final String MESSAGE_CONSTRAINTS = "You cannot have multiple diary entries for a one day. ";

    private final Index dayIndex;
    private final PhotoList photoList;
    private String diaryText;

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code day}, {@code photoList},
     * and {@code diaryText} instances.
     *
     * @param dayIndex The {@code Day} instance to use.
     * @param photoList The {@code PhotoList} to use.
     * @param diaryText The {@code String} to use to hold the entry's text.
     */
    public EditDiaryEntryDescriptor(Index dayIndex, PhotoList photoList, String diaryText) {
        requireAllNonNull(dayIndex, photoList, diaryText);
        this.dayIndex = dayIndex;
        this.photoList = photoList;
        this.diaryText = diaryText;
    }

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code dayIndex} {@code Index}.
     *
     * @param dayIndex The {@code Day} instance to use.
     */
    public EditDiaryEntryDescriptor(Index dayIndex) {
        this(dayIndex, new PhotoList(), "");
    }

    public EditDiaryEntryDescriptor(DiaryEntry diaryEntry) {
        this(diaryEntry.getDayIndex(), diaryEntry.getPhotoList(), diaryEntry.getDiaryText());
    }

    public DiaryEntry buildDiaryEntry() {
        requireAllNonNull(dayIndex, diaryText, photoList);

        return new DiaryEntry(dayIndex, photoList, diaryText);
    }

    public Index getDayIndex() {
        return dayIndex;
    }

    public PhotoList getPhotoList() {
        return photoList;
    }

    public String getDiaryText() {
        return diaryText;
    }

    public void setDiaryText(String diaryText) {
        this.diaryText = diaryText;
    }

    public void addPhoto(Photo photo) {
        photoList.addPhoto(photo);
    }

    public Photo deletePhoto(Index index) {
        return photoList.deletePhoto(index);
    }
}
