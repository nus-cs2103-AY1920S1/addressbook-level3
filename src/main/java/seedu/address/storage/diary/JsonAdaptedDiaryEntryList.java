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
class JsonAdaptedDiaryEntryList {
    private static final String MISSING_DIARY_ENTRIES_MESSAGE =
            "A diary's diary entry list is missing its diary entries in the storage file!";

    private final List<JsonAdaptedDiaryEntry> diaryEntries;

    /**
     * Constructs a {@code JsonAdaptedDiary} with the given diary details.
     */
    @JsonCreator
    public JsonAdaptedDiaryEntryList(@JsonProperty("diaryEntries") List<JsonAdaptedDiaryEntry> diaryEntries) {
        this.diaryEntries = new ArrayList<JsonAdaptedDiaryEntry>();
        if (diaryEntries != null) {
            this.diaryEntries.addAll(diaryEntries);
        }
    }

    /**
     * Converts a given {@code Diary} into this class for Jackson use.
     */
    JsonAdaptedDiaryEntryList(DiaryEntryList source) {
        requireNonNull(source);
        this.diaryEntries = new ArrayList<JsonAdaptedDiaryEntry>();
        ObservableList<DiaryEntry> diaryEntryList = source.getDiaryEntrySortedList();

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
        if (diaryEntries == null) {
            throw new IllegalArgumentException(MISSING_DIARY_ENTRIES_MESSAGE);
        }

        List<DiaryEntry> diaryEntries = new ArrayList<DiaryEntry>();
        for (JsonAdaptedDiaryEntry jsonAdaptedDiaryEntry : this.diaryEntries) {
            diaryEntries.add(jsonAdaptedDiaryEntry.toModelType());
        }

        return new DiaryEntryList(diaryEntries);
    }
}
