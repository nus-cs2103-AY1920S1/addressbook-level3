package seedu.address.storage.diary;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.DiaryEntryList;

/**
 * Jackson-friendly version of {@link Diary}.
 */
public class JsonAdaptedDiary {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary's %s field is missing!";

    private final JsonAdaptedDiaryEntryList diaryEntryList;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedDiary(@JsonProperty("diaryEntryList") JsonAdaptedDiaryEntryList diaryEntryList) {
        requireNonNull(diaryEntryList);
        this.diaryEntryList = diaryEntryList;
    }

    /**
     * Converts a given {@code Diary} into this class for Jackson use.
     */
    public JsonAdaptedDiary(Diary source) {
        requireNonNull(source);
        this.diaryEntryList = new JsonAdaptedDiaryEntryList(source.getDiaryEntryList());
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Diary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted diary.
     */
    public Diary toModelType() throws IllegalValueException {
        return new Diary(diaryEntryList.toModelType());
    }
}
