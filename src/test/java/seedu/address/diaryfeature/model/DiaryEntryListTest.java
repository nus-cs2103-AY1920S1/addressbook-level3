package seedu.address.diaryfeature.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class DiaryEntryListTest {

    DiaryEntryList tester = new DiaryEntryList();


    @Test
    public void test_DiaryEntryList_Fill_Method() {
        tester.loadSampleData();
        assertEquals(2,tester.getSize());
    }

    @Test
    public void test_DiaryEntryList_Delete_Method() {
        tester.loadSampleData();
        tester.deleteDiaryEntry(1);
        assertEquals(1,tester.getSize());
    }
}
