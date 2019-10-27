package seedu.address.diaryfeature.model.util;


import seedu.address.diaryfeature.model.diaryEntry.Date;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.diaryEntry.Title;



/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static DiaryEntry[] getSampleDiaryEntry() {
        return new DiaryEntry[] {
            new DiaryEntry(new Title("Temp"), new Date("hee hee")),
                new DiaryEntry(new Title("Temp 2"), new Date("wawa wawa"))
        };
    }

    /*

    public static ReadOnlyDiaryBook getSampleDiaryBook() {
        DiaryBook sampleAb = new AddressBook();
        for (DiaryEntry samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

     */


}
