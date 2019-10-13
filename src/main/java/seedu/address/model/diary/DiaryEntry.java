package seedu.address.model.diary;

import seedu.address.model.diary.photo.Photo;
import seedu.address.model.diary.photo.PhotoList;
import seedu.address.model.itinerary.day.Day;

/**
 * Abstraction of a singular diary entry stored in a {@link Diary}.
 * Each entry is linked to a specific {@code Day} and multiple {@code Event}s.
 */
public class DiaryEntry {
    private final Day day;
    private PhotoList photoList;
    private String diaryText;

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code day}, {@code photoList},
     * and {@code diaryText} instances.
     *
     * @param day The {@code Day} instance to use.
     * @param photoList The {@code PhotoList} to use.
     * @param diaryText The {@code String} to use to hold the entry's text.
     */
    public DiaryEntry(Day day, PhotoList photoList, String diaryText) {
        this.day = day;
        this.photoList = photoList;
        this.diaryText = diaryText;
    }

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code day} instance.
     *
     * @param day The {@code Day} instance to use.
     */
    public DiaryEntry(Day day) {
        this(day, new PhotoList(), "");
    }
}
