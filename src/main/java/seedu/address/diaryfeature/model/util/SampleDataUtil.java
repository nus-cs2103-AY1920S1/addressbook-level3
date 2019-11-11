package seedu.address.diaryfeature.model.util;


import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DiaryEntryParseException;
import seedu.address.diaryfeature.model.DiaryBook;
import seedu.address.diaryfeature.model.diaryEntry.DateFormatter;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static DiaryEntry[] getSampleDiaryEntry() {
        try {
            return new DiaryEntry[]{
                    new DiaryEntry(new Title("Temp"), DateFormatter.convertToDate("25/10/2019 1200"),
                            new Place("Copacabana"), new Memory("Her name was Lola, she was a showgirl")),
                    new DiaryEntry(new Title("Temp 2"), DateFormatter.convertToDate("31/12/2019 1400"),
                            new Place("Kokomo"), new Memory("Aruba, Bahama, come on pretty mama"))
            };
        } catch (DiaryEntryParseException error) {
            return null;
        }

    }

    /**
     * Get this sample diarybook
     * @return DiaryBook
     */
    public static DiaryBook getSampleDiaryBook() {
        DiaryBook sample = new DiaryBook();
        sample.loadData(getSampleDiaryEntry());
        return sample;
    }
}
