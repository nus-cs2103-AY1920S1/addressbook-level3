package seedu.address.diaryfeature.storage;

import java.text.ParseException;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.diaryfeature.model.diaryEntry.DateFormatter;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;
import seedu.address.diaryfeature.model.exceptions.TitleException;

/**
 * Jackson-friendly version of {@link seedu.address.diaryfeature.model.diaryEntry.DiaryEntry}.
 */

public class JsonAdaptedDiaryEntry {

        public static final String MISSING_FIELD_MESSAGE_FORMAT = "Diary's %s field is missing!";

        private final String title;
        private final String date;
        private final String place;
        private final String memory;

        /**
         * Constructs a {@code JsonAdaptedDiaryEntry} with the given person details.
         */
        @JsonCreator
        public JsonAdaptedDiaryEntry(@JsonProperty("title") String title, @JsonProperty("date") String date,
                                 @JsonProperty("place") String place, @JsonProperty("memory") String memory) {
            this.title = title;
            this.date = date;
            this.place = place;
            this.memory = memory;
        }

        /**
         * Converts a given {@code Person} into this class for Jackson use.
         */
        public JsonAdaptedDiaryEntry(DiaryEntry source) {
            title = source.getTitle().toString();
            date = source.getDateAsStringtoStore();
            place = source.getPlace().toString();
            memory = source.getMemory().toString();
        }

        /**
         * Converts this Jackson-friendly adapted person object into the addressBookModel's {@code Person} object.
         *
         * @throws IllegalValueException if there were any data constraints violated in the adapted person.
         */
        public DiaryEntry toModelType() throws IllegalValueException, ParseException, TitleException {

            if (title == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
            }

            final Title modelTitle = new Title(title);

            if (date == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date is missing"));
            }
            final Date modelDate = DateFormatter.convertToDate(date);

            if (place == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Place.class.getSimpleName()));
            }

            final Place modelPlace = new Place(place);

            if (memory == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Memory.class.getSimpleName()));
            }
            final Memory modelMemory = new Memory(memory);


            return new DiaryEntry(modelTitle,modelDate,modelPlace,modelMemory);
        }

    }
