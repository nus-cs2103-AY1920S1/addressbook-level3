package seedu.address.diaryfeature.storage;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.diaryfeature.logic.parser.Encryptor;
import seedu.address.diaryfeature.logic.parser.ParserUtil;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DiaryEntryParseException;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;

/**
 * Jackson-friendly version of {@link seedu.address.diaryfeature.model.diaryEntry.DiaryEntry}.
 */

public class JsonAdaptedDiaryEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary's %s field is missing!";

    private final String title;
    private final String date;
    private final String place;
    private final String memory;
    private final String isPrivate;

    /**
     * Constructs a {@code JsonAdaptedDiaryEntry} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDiaryEntry(@JsonProperty("title") String title, @JsonProperty("date") String date,
                                 @JsonProperty("place") String place, @JsonProperty("memory") String memory,
                                 @JsonProperty("isPrivate") String isPrivate) {
        this.title = title;
        this.date = date;
        this.place = place;
        this.memory = memory;
        this.isPrivate = isPrivate;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedDiaryEntry(DiaryEntry source) {
        title = source.getTitle().toString();
        date = source.getDateAsStringtoStore();
        place = source.getPlace().toString();
        memory = Encryptor.encrypt(source.getMemory().toSave());
        isPrivate = String.valueOf(source.getPrivacy());

    }

    /**
     * Converts this Jackson-friendly adapted person object into the addressBookModel's {@code Person} object.
     */
    public DiaryEntry toModelType() throws DiaryEntryParseException {
        final Title modelTitle = ParserUtil.parseTitle(title);
        final Date modelDate = ParserUtil.parseDate(date);
        final Place modelPlace = ParserUtil.parsePlace(place);
        Memory modelMemory = ParserUtil.parseMemory(Encryptor.decrypt(memory));
        boolean privacy = Boolean.valueOf(isPrivate);
        if (privacy) {
            modelMemory.setPrivate();
        } else {
            modelMemory.unPrivate();
        }
        DiaryEntry curr = new DiaryEntry(modelTitle, modelDate, modelPlace, modelMemory);
        return curr;
    }

}
