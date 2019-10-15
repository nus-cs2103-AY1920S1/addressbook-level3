package mams.model.appeal;

import mams.testutil.TypicalAppeals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AppealTest {

    @Test
    public void isSameAppeal() {
        // same object -> returns true
        Assertions.assertTrue(TypicalAppeals.appeal1.isSameAppeal(TypicalAppeals.appeal1));

        // null -> returns false
        Assertions.assertFalse(TypicalAppeals.appeal1.isSameAppeal(null));


    }

//    @Test
//    public void isEqual() {
//
//    }
}
