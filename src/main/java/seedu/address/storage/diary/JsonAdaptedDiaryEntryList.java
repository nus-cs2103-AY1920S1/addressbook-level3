package seedu.address.storage.diary;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.collections.ObservableList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.DiaryEntryList;

/**
 * Jackson-friendly version of {@link DiaryEntryList}.
 */
public class JsonAdaptedDiaryEntryList {
    private final List<JsonAdaptedDiaryEntry> diaryEntries;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedDiaryEntryList(@JsonProperty("entries") List<JsonAdaptedDiaryEntry> diaryEntries) {
        this.diaryEntries = new ArrayList<JsonAdaptedDiaryEntry>();
        if (diaryEntries != null) {
            this.diaryEntries.addAll(diaryEntries);
        }
    }

    /**
     * Converts a given {@code Diary} into this class for Jackson use.
     */
    public JsonAdaptedDiaryEntryList(DiaryEntryList source) {
        requireNonNull(source);
        this.diaryEntries = new ArrayList<JsonAdaptedDiaryEntry>();
        ObservableList<DiaryEntry> diaryEntryList = source.getReadOnlyDiaryEntries();

        for (DiaryEntry diaryEntry : diaryEntryList) {
            this.diaryEntries.add(new JsonAdaptedDiaryEntry(diaryEntry));
        }
    }

    /**
     * Converts this Jackson-friendly adapted diary object into the model's {@code Diary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted diary.
     */
    public DiaryEntryList toModelType() throws IllegalValueException {
        List<DiaryEntry> diaryEntries = new ArrayList<DiaryEntry>();
        for (JsonAdaptedDiaryEntry jsonAdaptedDiaryEntry : this.diaryEntries) {
            diaryEntries.add(jsonAdaptedDiaryEntry.toModelType());
        }

        return new DiaryEntryList(diaryEntries);
    }
}
