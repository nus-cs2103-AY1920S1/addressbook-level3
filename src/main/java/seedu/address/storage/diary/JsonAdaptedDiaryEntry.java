package seedu.address.storage.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.photo.Photo;
import seedu.address.model.diary.photo.PhotoList;

/**
 * Jackson-friendly version of {@link DiaryEntry}.
 */
public class JsonAdaptedDiaryEntry {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary entry's %s field is missing!";

    private final Index day;
    private final String diaryText;
    private final PhotoList photoList;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedDiaryEntry(
            @JsonProperty("index") int dayOneBasedIndex,
            @JsonProperty("photolist") List<Photo> photos,
            @JsonProperty("text") String diaryText) {
        requireAllNonNull(dayOneBasedIndex, diaryText);
        this.day = Index.fromOneBased(dayOneBasedIndex);
        this.diaryText = diaryText;

        if (photos != null) {
            this.photoList = new PhotoList(photos);
        } else {
            this.photoList = new PhotoList();
        }
    }

    /**
     * Converts a given {@code Diary} into this class for Jackson use.
     */
    public JsonAdaptedDiaryEntry(DiaryEntry source) {
        requireNonNull(source);
        this.day = source.getDayIndex();
        this.diaryText = source.getDiaryText();
        this.photoList = source.getPhotoList();
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Diary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted diary.
     */
    public DiaryEntry toModelType() throws IllegalValueException {
        return new DiaryEntry(day, photoList, diaryText);
    }
}
