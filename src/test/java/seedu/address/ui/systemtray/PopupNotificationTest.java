package seedu.address.ui.systemtray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


public class PopupNotificationTest {
    @Test
    void constructor_inputNull_shouldThrowError() {
        assertThrows(NullPointerException.class, (
            ) -> new PopupNotification("testName", null));

        assertThrows(NullPointerException.class, (
            ) -> new PopupNotification(null, "testDescription"));
    }

    @Test
    void equality_inputSameParameters_equalityCheckShouldPass() {
        PopupNotification pn1 = new PopupNotification("testName", "testDescrp");
        PopupNotification pn2 = new PopupNotification("testName", "testDescrp");
        assertEquals(pn1, pn2);
    }

    @Test
    void equality_compareToObjectOfDifferentClass_shouldReturnFalse() {
        PopupNotification pn = new PopupNotification("testName", "testDescrp");
        String string = "Test String";
        assertFalse(pn.equals(string));
    }

    @Test
    void equality_compareSameClassDifferentParameters_shouldReturnFalse() {
        PopupNotification pn1 = new PopupNotification("testName1", "testDescrp1");
        PopupNotification pn2 = new PopupNotification("testName2", "testDescrp2");
        assertFalse(pn1.equals(pn2));

        PopupNotification pn3 = new PopupNotification("testName1", "testDescrp1");
        PopupNotification pn4 = new PopupNotification("testName1", "testDescrp2");
        assertFalse(pn3.equals(pn4));

        PopupNotification pn5 = new PopupNotification("testName1", "testDescrp1");
        PopupNotification pn6 = new PopupNotification("testName2", "testDescrp1");
        assertFalse(pn5.equals(pn6));
    }
}
