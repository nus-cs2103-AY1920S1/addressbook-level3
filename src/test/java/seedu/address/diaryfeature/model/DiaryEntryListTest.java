package seedu.address.diaryfeature.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



public class DiaryEntryListTest {

    private DiaryEntryList tester;


    @Test
    public void test_diaryEntryList_Fill_Method() {
        tester = new DiaryEntryList();
        tester.loadSampleData();
        assertEquals(2, tester.getSize());
    }

    @Test
    public void test_diaryEntryList_Delete_Method() {
        tester = new DiaryEntryList();
        tester.loadSampleData();
        tester.deleteDiaryEntry(1);
        assertEquals(1, tester.getSize());
    }
}
