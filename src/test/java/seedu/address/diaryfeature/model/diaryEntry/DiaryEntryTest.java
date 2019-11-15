package seedu.address.diaryfeature.model.diaryEntry;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DateParseException;


public class DiaryEntryTest {
    //Base for comparison
    private final String TITLE_SAMPLE = "Title";
    private final String DATE_SAMPLE_AS_STRING = "12/12/1212 2000";
    private final String PLACE_SAMPLE = "Place";
    private final String MEMORY_SAMPLE = "Memory";
    //Change title and date
    private final String TITLE_SAMPLE_1 = "Title1";
    private final String DATE_SAMPLE_AS_STRING_1 = "11/12/1212 2000";
    private final String PLACE_SAMPLE_1 = "Place";
    private final String MEMORY_SAMPLE_1 = "Memory";
    //Change date
    private final String TITLE_SAMPLE_2 = "Title";
    private final String DATE_SAMPLE_AS_STRING_2 = "11/12/1212 2000";
    private final String PLACE_SAMPLE_2 = "Place";
    private final String MEMORY_SAMPLE_2 = "Memory";
    //Change title
    private final String TITLE_SAMPLE_3 = "Title1";
    private final String DATE_SAMPLE_AS_STRING_3 = "12/12/1212 2000";
    private final String PLACE_SAMPLE_3 = "Place";
    private final String MEMORY_SAMPLE_3 = "Memory";
    //Change place
    private final String TITLE_SAMPLE_4 = "Title";
    private final String DATE_SAMPLE_AS_STRING_4 = "12/12/1212 2000";
    private final String PLACE_SAMPLE_4 = "Place1";
    private final String MEMORY_SAMPLE_4 = "Memory";
    //Change memory
    private final String TITLE_SAMPLE_5 = "Title";
    private final String DATE_SAMPLE_AS_STRING_5 = "12/12/1212 2000";
    private final String PLACE_SAMPLE_5 = "Place1";
    private final String MEMORY_SAMPLE_5 = "Memory";



    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DiaryEntry(null,null,null,null));    }

        private DiaryEntry getSampleDiaryEntry(int input) {
            try {
                switch(input) {
                    case 0:
                        return new DiaryEntry(new Title(TITLE_SAMPLE), DateFormatter.convertToDate(DATE_SAMPLE_AS_STRING),
                                new Place(PLACE_SAMPLE), new Memory(MEMORY_SAMPLE));
                    case 1:
                        return new DiaryEntry(new Title(TITLE_SAMPLE_1), DateFormatter.convertToDate(DATE_SAMPLE_AS_STRING_1),
                                new Place(PLACE_SAMPLE_1), new Memory(MEMORY_SAMPLE_1));
                    case 2:
                        return new DiaryEntry(new Title(TITLE_SAMPLE_2), DateFormatter.convertToDate(DATE_SAMPLE_AS_STRING_2),
                                new Place(PLACE_SAMPLE_2), new Memory(MEMORY_SAMPLE_2));
                    case 3:
                        return new DiaryEntry(new Title(TITLE_SAMPLE_3), DateFormatter.convertToDate(DATE_SAMPLE_AS_STRING_3),
                                new Place(PLACE_SAMPLE_3), new Memory(MEMORY_SAMPLE_3));
                    case 4:
                        return new DiaryEntry(new Title(TITLE_SAMPLE_4), DateFormatter.convertToDate(DATE_SAMPLE_AS_STRING_4),
                                new Place(PLACE_SAMPLE_4), new Memory(MEMORY_SAMPLE_4));
                    case 5:
                        return new DiaryEntry(new Title(TITLE_SAMPLE_5), DateFormatter.convertToDate(DATE_SAMPLE_AS_STRING_5),
                                new Place(PLACE_SAMPLE_5), new Memory(MEMORY_SAMPLE_5));
                    default:
                        assert input > -1 && input< 6;
                        System.out.println("Error in Sample input");
                        return null;

                }

            } catch (DateParseException error) {
                assert DATE_SAMPLE_AS_STRING != null;
                System.out.println("Error with sample data");
                return null;
            }
        }

    @Test
    public void copy_is_different_from_original() {
        DiaryEntry test = getSampleDiaryEntry(0);
        assertNotSame(test, test.copy());
    }

    @Test
    public void memory_privacy() {
        DiaryEntry test = getSampleDiaryEntry(0);
        assertFalse(test.getPrivacy());
        test.setPrivate();
        assertTrue(test.getPrivacy());
        test.unPrivate();
        assertFalse(test.getPrivacy());
    }

    @Test
    public void equality() {
        DiaryEntry test_0 = getSampleDiaryEntry(0);
        DiaryEntry test_1 = getSampleDiaryEntry(1);
        DiaryEntry test_2 = getSampleDiaryEntry(2);
        DiaryEntry test_3 = getSampleDiaryEntry(3);
        DiaryEntry test_4 = getSampleDiaryEntry(4);
        DiaryEntry test_5 = getSampleDiaryEntry(5);
        DiaryEntry test_6 = getSampleDiaryEntry(0);

        assertEquals(test_0,test_0); //check if the same Diary entry is the same
        assertEquals(test_0,test_6); //check if different diary entries with the same params are the same
        assertNotSame(test_0,test_1); //check if different title/date leads to inequality
        assertNotSame(test_0,test_2); //check if different title leads to inequality
        assertNotSame(test_0,test_3); //check if different date leads to inequality
        assertEquals(test_0,test_4); //check if the diary entry is the same with a different place
        assertEquals(test_0,test_5); //check if the diary entry is the same with a different memory


    }


    }

