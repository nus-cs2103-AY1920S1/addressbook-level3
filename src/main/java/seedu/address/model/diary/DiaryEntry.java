package seedu.address.model.diary;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.Photo;
import seedu.address.model.diary.photo.PhotoList;
import seedu.address.model.itinerary.day.Day;

/**
 * Abstraction of a singular diary entry stored in a {@link Diary}.
 * Each entry is linked to a specific {@code Day} and multiple {@code Event}s.
 */
public class DiaryEntry {
    public static final String MESSAGE_CONSTRAINTS = "You cannot have multiple diary entries for a one day. ";

    private final Index day;
    private final PhotoList photoList;
    private String diaryText;

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code day}, {@code photoList},
     * and {@code diaryText} instances.
     *
     * @param day The {@code Day} instance to use.
     * @param photoList The {@code PhotoList} to use.
     * @param diaryText The {@code String} to use to hold the entry's text.
     */
    public DiaryEntry(Index day, PhotoList photoList, String diaryText) {
        requireAllNonNull(day, photoList, diaryText);
        this.day = day;
        this.photoList = photoList;
        this.diaryText = diaryText;
    }

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code dayIndex} {@code Index}.
     *
     * @param dayIndex The {@code Day} instance to use.
     */
    public DiaryEntry(Index dayIndex) {
        this(dayIndex, new PhotoList(), "");
    }

    public Index getDayIndex() {
        return day;
    }

    public PhotoList getPhotoList() {
        return photoList;
    }

    public String getDiaryText() {
        return diaryText;
    }
}
