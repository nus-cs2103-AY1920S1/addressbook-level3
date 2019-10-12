package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.diary.Diary;

/**
 * An Immutable Exercise Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "exercisecatalogue")
class JsonSerializableExerciseCatalogue {

    public static final String MESSAGE_DUPLICATE_DIARY = "Diaries list contains duplicate diary(s).";

    private final List<JsonAdaptedDiary> diaries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableExerciseCatalogue} with the given diaries.
     */
    @JsonCreator
    public JsonSerializableExerciseCatalogue(@JsonProperty("diaries") List<JsonAdaptedDiary> diaries) {
        this.diaries.addAll(diaries);
    }

    /**
    * Converts a given {@code ReadOnlyDukeCooks} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableExerciseCatalogue}.
    */
    public JsonSerializableExerciseCatalogue(ReadOnlyDukeCooks source) {
        diaries.addAll(source.getDiaryList().stream().map(JsonAdaptedDiary::new).collect(Collectors.toList()));
    }

    /**
    *  Converts this Exercise Catalogue into the model's {@code DukeCooks} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public DukeCooks toModelType() throws IllegalValueException {
        DukeCooks dukeCooks = new DukeCooks();
        for (JsonAdaptedDiary jsonAdaptedDiary : diaries) {
            Diary diary = jsonAdaptedDiary.toModelType();
            if (dukeCooks.hasDiary(diary)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DIARY);
            }
            dukeCooks.addDiary(diary);
        }
        return dukeCooks;
    }
}
