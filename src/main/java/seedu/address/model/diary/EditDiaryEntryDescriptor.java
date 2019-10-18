package seedu.address.model.diary;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.PhotoList;

/**
 * Class for storing diary text edit information as a separate buffer,
 * allowing discarding changes before committing to a {@link DiaryEntry}.
 */
public class EditDiaryEntryDescriptor {
    private static final int MAX_DIARY_TEXT_DISPLAY_LENGTH = 20;

    private final Index dayIndex;
    private final PhotoList photoList;
    private String diaryText;

    public EditDiaryEntryDescriptor(DiaryEntry diaryEntry) {
        this(diaryEntry.getDayIndex(), diaryEntry.getPhotoList(), diaryEntry.getDiaryText());
    }

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code day}, {@code photoList},
     * and {@code diaryText} instances.
     *
     * @param dayIndex The {@code Day} instance to use.
     * @param photoList The {@code PhotoList} to use.
     * @param diaryText The {@code String} to use to hold the entry's text.
     */
    private EditDiaryEntryDescriptor(Index dayIndex, PhotoList photoList, String diaryText) {
        requireAllNonNull(dayIndex, photoList, diaryText);
        this.dayIndex = dayIndex;
        this.photoList = photoList;
        this.diaryText = diaryText;
    }

    /**
     * Builds the diary entry from the instance variables that this edit descriptor holds.
     *
     * @return A new {@link DiaryEntry} constructed from {@code dayIndex}, {@code photoList}, {@code diaryText}.
     */
    public DiaryEntry buildDiaryEntry() {
        requireAllNonNull(dayIndex, diaryText, photoList);

        return new DiaryEntry(dayIndex, photoList, diaryText);
    }

    public String getDiaryText() {
        return diaryText;
    }

    public void setDiaryText(String diaryText) {
        this.diaryText = diaryText;
    }

    /**
     * Appends the a newline, with the specified {@link String} to the {@code diaryText}.
     *
     * @param textToAdd The {@link String} to append.
     */
    public void addNewTextLine(String textToAdd) {
        this.diaryText += "\n";
        this.diaryText += textToAdd;
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
                .append(" Photo List: ")
                .append(photoList.toString());

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof EditDiaryEntryDescriptor)) {
            return false;
        }

        EditDiaryEntryDescriptor otherEntry = (EditDiaryEntryDescriptor) obj;

        return dayIndex.equals(otherEntry.dayIndex)
                && diaryText.equals(otherEntry.diaryText)
                && photoList.equals(otherEntry.photoList);
    }
}
