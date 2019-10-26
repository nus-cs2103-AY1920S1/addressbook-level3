package seedu.address.diaryfeature.model.diaryEntry;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.diaryfeature.model.Title;
import seedu.address.diaryfeature.model.exceptions.TitleException;


public class TitleTest {
    @Test
    public void TitleConstructorTest() {
        assertThrows(TitleException.class, () -> new Title("        "));
    }


}
