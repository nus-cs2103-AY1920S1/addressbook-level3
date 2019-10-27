package seedu.address.model.diary;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.PhotoList;

/**
 * Abstraction of a singular diary entry stored in a {@link Diary}.
 * Each entry is linked to a specific {@code Day} and multiple {@code Event}s.
 */
public class DiaryEntry {

    /** The maximum diary text display length to use when converting the {@link DiaryEntry} to a string. */
    static final int MAX_DIARY_TEXT_DISPLAY_LENGTH = 30;

    private final Index dayIndex;
    private final String diaryText;
    private final PhotoList photoList;

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code day}, {@code photoList},
     * and {@code diaryText} instances.
     *
     * @param dayIndex The {@code Day} instance to use.
     * @param photoList The {@code PhotoList} to use.
     * @param diaryText The {@code String} to use to hold the entry's text.
     */
    public DiaryEntry(Index dayIndex, PhotoList photoList, String diaryText) {
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
    public DiaryEntry(Index dayIndex) {
        this(dayIndex, new PhotoList(), "");
    }

    public Index getDayIndex() {
        return dayIndex;
    }

    public int getDayNumber() {
        return dayIndex.getOneBased();
    }

    public PhotoList getPhotoList() {
        return photoList;
    }

    public String getDiaryText() {
        return diaryText;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int diaryTextLength = diaryText.length();
        int displayLength = Math.min(MAX_DIARY_TEXT_DISPLAY_LENGTH, diaryTextLength);

        builder.append("Day Number: ")
                .append(dayIndex.getOneBased())
                .append(" Diary Text (Truncated): ")
                .append(diaryText.substring(0, displayLength))
                .append("...\n")
                .append("Photo List:\n")
                .append(photoList.toString());

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DiaryEntry)) {
            return false;
        }

        DiaryEntry otherEntry = (DiaryEntry) obj;

        return dayIndex.equals(otherEntry.dayIndex)
                && diaryText.equals(otherEntry.diaryText)
                && photoList.equals(otherEntry.photoList);
    }
}
