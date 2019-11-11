package seedu.address.diaryfeature.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DateParseException;
import seedu.address.diaryfeature.model.diaryEntry.DateFormatter;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;
import seedu.address.diaryfeature.model.util.SampleDataUtil;


public class DiaryEntryListTest {
    private final String TITLE_SAMPLE = "Title";
    private final String DATE_SAMPLE_AS_STRING = "12/12/1212 2000";
    private final String PLACE_SAMPLE = "Place";
    private final String MEMORY_SAMPLE = "Memory";


    private DiaryEntry getSampleDiaryEntry() {
        try {
            return new DiaryEntry(new Title(TITLE_SAMPLE), DateFormatter.convertToDate(DATE_SAMPLE_AS_STRING), new Place(PLACE_SAMPLE), new Memory(MEMORY_SAMPLE));
        } catch (DateParseException error) {
            assert DATE_SAMPLE_AS_STRING != null;
            System.out.println("Error with sample data");
            return null;
        }
    }


    @Test
    public void test_diaryEntryList_Add_Method() {
        DiaryEntryList tester = new DiaryEntryList();
        tester.addDiaryEntry(getSampleDiaryEntry());
        assertEquals(1, tester.getSize());
    }

    @Test
    public void test_diaryEntryList_Delete_Method() {
        DiaryEntryList tester = new DiaryEntryList();
        tester.addDiaryEntry(getSampleDiaryEntry());
        tester.deleteDiaryEntry(getSampleDiaryEntry());
        assertEquals(0, tester.getSize());
    }

    @Test
    public void test_diaryEntryList_load_sample_data_method() {
        DiaryEntryList tester = new DiaryEntryList();
        tester.loadData(SampleDataUtil.getSampleDiaryEntry());
        assertEquals(2, tester.getSize());
    }

    @Test
    public void test_diaryEntryList_isEmpty_method() {
        DiaryEntryList tester = new DiaryEntryList();
        assertTrue(tester.isEmpty());
        tester.addDiaryEntry(getSampleDiaryEntry());
        assertFalse(tester.isEmpty());
    }

    @Test
    public void test_diaryEntryList_contains() {
        DiaryEntryList tester = new DiaryEntryList();
        tester.addDiaryEntry(getSampleDiaryEntry());
        assertTrue(tester.contains(getSampleDiaryEntry()));
    }

    @Test
    public void test_diaryEntryList_private() {
        DiaryEntryList tester = new DiaryEntryList();
        DiaryEntry test = getSampleDiaryEntry();
        tester.addDiaryEntry(test);
        assertFalse(test.getPrivacy());
        tester.setDiaryEntryPrivate(test);
        assertTrue(test.getPrivacy());
        tester.setDiaryEntryUnPrivate(test);
        assertFalse(test.getPrivacy());

    }
}



