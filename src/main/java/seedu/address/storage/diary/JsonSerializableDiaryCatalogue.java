package seedu.address.storage.diary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.DiaryRecords;
import seedu.address.model.diary.ReadOnlyDiary;
import seedu.address.model.diary.components.Diary;

/**
 * An Immutable Exercise Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "exercisecatalogue")
class JsonSerializableDiaryCatalogue {

    public static final String MESSAGE_DUPLICATE_DIARY = "Diaries list contains duplicate diary(s).";

    private final List<JsonAdaptedDiary> diaries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDiaryCatalogue} with the given diaries.
     */
    @JsonCreator
    public JsonSerializableDiaryCatalogue(@JsonProperty("diaries") List<JsonAdaptedDiary> diaries) {
        this.diaries.addAll(diaries);
    }

    /**
    * Converts a given {@code ReadOnlyDiary} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableDiaryCatalogue}.
    */
    public JsonSerializableDiaryCatalogue(ReadOnlyDiary source) {
        diaries.addAll(source.getDiaryList().stream().map(JsonAdaptedDiary::new).collect(Collectors.toList()));
    }

    /**
    *  Converts this Exercise Catalogue into the model's {@code DiaryRecords} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public DiaryRecords toModelType() throws IllegalValueException {
        DiaryRecords diaryRecords = new DiaryRecords();
        for (JsonAdaptedDiary jsonAdaptedDiary : diaries) {
            Diary diary = jsonAdaptedDiary.toModelType();
            if (diaryRecords.hasDiary(diary)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DIARY);
            }
            diaryRecords.addDiary(diary);
        }
        return diaryRecords;
    }
}
